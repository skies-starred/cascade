package foo.starred.cascade.graphics.extensions.rectangle.solid

import foo.starred.cascade.graphics.states.rectangle.solid.SolidRectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.rectangle(x: Float, y: Float, width: Float, height: Float, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    SolidRectangleRenderState(pose, x, y, x + width, y + height, color, scissor).submit(this)
}
