package foo.starred.cascade.graphics.states.batch.impl

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.states.batch.data.CascadeBatchedLayer
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.client.renderer.state.gui.GuiRenderState
import net.minecraft.client.renderer.state.gui.ScreenArea

object CascadeBatcher {
    private val layers = ArrayList<CascadeBatchedLayer>()
    private val nodes = ArrayList<GuiRenderState.Node>()
    private var last: GuiRenderState? = null

    fun submit(graphics: GuiGraphicsExtractor, state: GuiElementRenderState) {
        val bounds = state.bounds() ?: return
        val x0 = bounds.left()
        val y0 = bounds.top()
        val x1 = bounds.right()
        val y1 = bounds.bottom()

        if (x0 >= x1) return
        if (y0 >= y1) return

        val state1 = graphics.guiRenderState
        if (state1 !== last) {
            last = state1
            reset(state1)
        }

        if (state1 === last && (nodes.isEmpty() || (state1.current !== nodes.last() && root(state1.current) !== nodes[0]))) {
            reset(state1)
        }

        val pipeline = state.pipeline()
        val texture = state.textureSetup()
        val scissor = state.scissorArea()

        val i1 = nodes.lastIndex
        if (i1 >= 0 && state1.current === nodes[i1] && clean(nodes[i1])) {
            val layer = layers[i1]

            if (layer.matches(pipeline, texture, scissor)) {
                nodes[i1].addGuiElement(state)
                layer.synced++

                if (x0 < layer.x0) layer.x0 = x0
                if (y0 < layer.y0) layer.y0 = y0
                if (x1 > layer.x1) layer.x1 = x1
                if (y1 > layer.y1) layer.y1 = y1

                return
            }
        }

        sync()

        var target = 0

        for (index in nodes.indices) {
            if (index < target) continue

            val node = nodes[index]

            if (overlap(node.textStates, x0, y0, x1, y1)) {
                target = index + 1
                continue
            }

            if (overlap(node.itemStates, x0, y0, x1, y1)) {
                target = index + 1
                continue
            }

            if (overlap(node.glyphStates, x0, y0, x1, y1)) {
                target = index + 1
                continue
            }

            if (overlap(node.picturesInPictureStates, x0, y0, x1, y1)) {
                target = index + 1
                continue
            }

            val layer = layers[index]
            if (layer.matches(pipeline, texture, scissor)) {
                continue
            }

            if (target == index) {
                target = index + 1
            }

            if (layer.synced > 0) {
                if (x1 <= layer.x0) continue
                if (x0 >= layer.x1) continue
                if (y1 <= layer.y0) continue
                if (y0 >= layer.y1) continue
            }

            if (conflict(node, x0, y0, x1, y1)) {
                target = index + 1
                continue
            }
        }

        ensure(target, state1)

        nodes[target].addGuiElement(state)
        record(layers[target], pipeline, texture, scissor, x0, y0, x1, y1)
        state1.current = nodes.last()
    }

    private fun init(l: Int) {
        while (layers.size <= l) {
            layers.add(CascadeBatchedLayer())
        }

        layers[l].reset()
    }

    private fun reset(state: GuiRenderState) {
        nodes.clear()
        nodes.add(root(state.current))
        init(0)
    }

    private fun sync() {
        while (true) {
            val up = nodes.lastOrNull()?.up ?: break

            nodes.add(up)
            init(nodes.lastIndex)
        }
    }

    private fun clean(node: GuiRenderState.Node): Boolean {
        return node.up == null && node.itemStates.isNullOrEmpty() && node.textStates.isNullOrEmpty() && node.picturesInPictureStates.isNullOrEmpty() && node.glyphStates.isNullOrEmpty()
    }

    private fun root(node: GuiRenderState.Node): GuiRenderState.Node {
        var current = node

        while (true) {
            current = current.parent ?: break
        }

        return current
    }

    private fun overlap(list: List<ScreenArea>?, x0: Int, y0: Int, x1: Int, y1: Int): Boolean {
        if (list == null) return false

        for (index in list.indices) {
            val bounds = list[index].bounds() ?: continue

            if (x0 >= bounds.right()) continue
            if (bounds.left() >= x1) continue
            if (y0 >= bounds.bottom()) continue
            if (bounds.top() >= y1) continue

            return true
        }

        return false
    }

    private fun conflict(node: GuiRenderState.Node, x0: Int, y0: Int, x1: Int, y1: Int): Boolean {
        val elements = node.elementStates ?: return false

        for (index in elements.indices) {
            val bounds = elements[index].bounds() ?: continue

            if (x0 >= bounds.right()) continue
            if (bounds.left() >= x1) continue
            if (y0 >= bounds.bottom()) continue
            if (bounds.top() >= y1) continue

            return true
        }

        return false
    }

    private fun record(layer: CascadeBatchedLayer, pipeline: RenderPipeline, texture: TextureSetup, scissor: ScreenRectangle?, x0: Int, y0: Int, x1: Int, y1: Int) {
        if (layer.synced == 0) {
            layer.x0 = x0
            layer.y0 = y0
            layer.x1 = x1
            layer.y1 = y1
            layer.pipeline = pipeline
            layer.texture = texture
            layer.scissor = scissor
        }

        if (layer.synced > 0) {
            if (x0 < layer.x0) layer.x0 = x0
            if (y0 < layer.y0) layer.y0 = y0
            if (x1 > layer.x1) layer.x1 = x1
            if (y1 > layer.y1) layer.y1 = y1
        }

        layer.synced++
    }

    private fun ensure(target: Int, state: GuiRenderState) {
        while (nodes.size <= target) {
            val top = nodes.last()
            val up = top.up

            if (up != null) {
                nodes.add(up)
                init(nodes.lastIndex)
                continue
            }

            state.current = top
            state.up()
            nodes.add(state.current)
            init(nodes.lastIndex)
        }
    }
}
