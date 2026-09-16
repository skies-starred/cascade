package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.state.gui.GuiElementRenderState

open class RenderStatePrimitive : IPrimitiveElement<RenderStatePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: CascadeGeometricColor = CascadeGeometricColor.WHITE

    var state: GuiElementRenderState? = null
    var provider: ((GuiGraphicsExtractor) -> GuiElementRenderState?)? = null
    var ascend: Boolean = false

    override fun draw(graphics: GuiGraphicsExtractor) {
        val state = state ?: provider?.invoke(graphics) ?: return

        graphics.guiRenderState.addGuiElement(state)
        if (ascend) graphics.guiRenderState.nextStratum()
    }

    companion object {
        val NONE = RenderStatePrimitive()

        inline fun renderState(block: RenderStatePrimitive.() -> Unit): RenderStatePrimitive {
            return RenderStatePrimitive().apply(block)
        }
    }
}
