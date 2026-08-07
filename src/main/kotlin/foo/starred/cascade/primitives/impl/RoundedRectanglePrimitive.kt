package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.data.roundedrectangle.RoundedRectangleRadius
import foo.starred.cascade.primitives.states.RoundedRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f

open class RoundedRectanglePrimitive : IPrimitiveElement<RoundedRectanglePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    var radius: RoundedRectangleRadius = RoundedRectangleRadius.ZERO

    var border: Boolean = false
    var borderInset: Boolean = true
    var borderWidth: Float = 1f
    var borderColor: Int = -1

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement' {
        if (color ushr 24 != 0) {
            val state = RoundedRectangleRenderState(pose, x, y, x + width, y + height, color, radius, 0f, scissor)
            graphics.guiRenderState.addGuiElement(state)
        }

        if (border && borderColor ushr 24 != 0 && borderWidth > 0f) {
            val i0 = if (borderInset) 0f else -borderWidth
            val i1 = if (borderInset) 0f else borderWidth
            val state = RoundedRectangleRenderState(pose, x + i0, y + i0, x + width - i0, y + height - i0, borderColor, radius + i1, borderWidth, scissor)
            graphics.guiRenderState.addGuiElement(state)
        }
        //~}

        super.render(graphics)
    }

    companion object {
        val NONE = RoundedRectanglePrimitive()

        inline fun roundedRectangle(block: RoundedRectanglePrimitive.() -> Unit): RoundedRectanglePrimitive {
            return RoundedRectanglePrimitive().apply(block)
        }
    }
}