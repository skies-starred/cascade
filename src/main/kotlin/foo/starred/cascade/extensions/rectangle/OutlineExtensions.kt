package foo.starred.cascade.extensions.rectangle

import foo.starred.cascade.primitives.states.RectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
@JvmName("outline_int")
fun GuiGraphicsExtractor.outline(x: Int, y: Int, width: Int, height: Int, border: Int, color: Int = -1, inset: Boolean = false) {
    val border = if (inset) -border else border
    fill(x - border, y - border, x + width + border, y, color)
    fill(x - border, y + height, x + width + border, y + height + border, color)
    fill(x - border, y, x, y + height, color)
    fill(x + width, y, x + width + border, y + height, color)
}

@JvmOverloads
@JvmName("outline_float")
fun GuiGraphicsExtractor.outline(x: Float, y: Float, width: Float, height: Float, border: Float, color: Int = -1, inset: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val border = if (inset) -border else border
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    RectangleRenderState(pose, x - border, y - border, x + width + border, y, color, scissor).submit(this)
    RectangleRenderState(pose, x - border, y + height, x + width + border, y + height + border, color, scissor).submit(this)
    RectangleRenderState(pose, x - border, y, x, y + height, color, scissor).submit(this)
    RectangleRenderState(pose, x + width, y, x + width + border, y + height, color, scissor).submit(this)
}