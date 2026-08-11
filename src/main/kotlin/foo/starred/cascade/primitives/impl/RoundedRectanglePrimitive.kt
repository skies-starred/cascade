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
    var blur: Float = 0f

    var border: Boolean = false
    var borderInset: Boolean = true
    var borderWidth: Float = 1f
    var borderColor: Int = -1

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        if (color ushr 24 != 0 || blur > 0f) {
            RoundedRectangleRenderState.extract(graphics, x, y, width, height, color, radius, 0f, blur, pose, scissor)
        }

        if (border && borderColor ushr 24 != 0 && borderWidth > 0f) {
            val i0 = if (borderInset) 0f else -borderWidth
            val i1 = if (borderInset) 0f else borderWidth

            RoundedRectangleRenderState.extract(graphics, x + i0, y + i0, width - i0 * 2, height - i0 * 2, borderColor, radius + i1, borderWidth, 0f, pose, scissor)
        }

        super.render(graphics)
    }

    companion object {
        val NONE = RoundedRectanglePrimitive()

        inline fun roundedRectangle(block: RoundedRectanglePrimitive.() -> Unit): RoundedRectanglePrimitive {
            return RoundedRectanglePrimitive().apply(block)
        }
    }
}