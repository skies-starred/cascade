package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState

open class RenderStatePrimitive : IPrimitiveElement<RenderStatePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    var state: GuiElementRenderState? = null
    var provider: ((GuiGraphicsExtractor) -> GuiElementRenderState?)? = null
    var ascend: Boolean = false

    override fun draw(graphics: GuiGraphicsExtractor) {
        val s = state ?: provider?.invoke(graphics) ?: return

        s.submit(graphics)
        if (ascend) graphics.guiRenderState.nextStratum()
    }

    companion object {
        val NONE = RenderStatePrimitive()

        inline fun renderState(block: RenderStatePrimitive.() -> Unit): RenderStatePrimitive {
            return RenderStatePrimitive().apply(block)
        }
    }
}
