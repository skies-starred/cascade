package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
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

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val s = state ?: provider?.invoke(graphics)
        if (s != null) {
            //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
            graphics.guiRenderState.addGuiElement(s)
            if (ascend) graphics.guiRenderState.nextStratum()
        }

        super.render(graphics)
    }

    companion object {
        val NONE = RenderStatePrimitive()

        inline fun renderState(block: RenderStatePrimitive.() -> Unit): RenderStatePrimitive {
            return RenderStatePrimitive().apply(block)
        }
    }
}