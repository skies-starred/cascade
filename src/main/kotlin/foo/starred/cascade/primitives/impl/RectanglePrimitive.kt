package foo.starred.cascade.primitives.impl

import foo.starred.cascade.extensions.rectangle.outline
import foo.starred.cascade.extensions.rectangle.rectangle
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f

open class RectanglePrimitive : IPrimitiveElement<RectanglePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    var border: Boolean = false
    var borderInset: Boolean = true
    var borderWidth: Float = 1f
    var borderColor: Int = -1

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        if (color ushr 24 != 0) {
            graphics.rectangle(x, y, width, height, color, pose, scissor)
        }

        if (border && borderColor ushr 24 != 0 && borderWidth > 0f) {
            graphics.outline(x, y, width, height, borderWidth, borderColor, borderInset, pose, scissor)
        }

        super.render(graphics)
    }

    companion object {
        val NONE = RectanglePrimitive()

        inline fun rectangle(block: RectanglePrimitive.() -> Unit): RectanglePrimitive {
            return RectanglePrimitive().apply(block)
        }
    }
}