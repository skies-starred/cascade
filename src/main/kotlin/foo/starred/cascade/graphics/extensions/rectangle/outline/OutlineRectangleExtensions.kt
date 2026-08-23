package foo.starred.cascade.graphics.extensions.rectangle.outline

import foo.starred.cascade.graphics.states.rectangle.solid.SolidRectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.outline(x: Float, y: Float, width: Float, height: Float, border: Float, color: Int = -1, inset: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val border = if (inset) -border else border
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    SolidRectangleRenderState(pose, x - border, y - border, x + width + border, y, color, scissor).submit(this)
    SolidRectangleRenderState(pose, x - border, y + height, x + width + border, y + height + border, color, scissor).submit(this)
    SolidRectangleRenderState(pose, x - border, y, x, y + height, color, scissor).submit(this)
    SolidRectangleRenderState(pose, x + width, y, x + width + border, y + height, color, scissor).submit(this)
}
