package foo.starred.cascade.graphics.extensions.stroke

import foo.starred.cascade.graphics.states.stroke.StrokeRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.stroke(x1: Float, y1: Float, x2: Float, y2: Float, color: Int, thickness: Float = 1f, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val x3 = x2 - x1
    val y3 = y2 - y1
    if (x3 * x3 + y3 * y3 <= 0f) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    StrokeRenderState(pose, x1, y1, x2, y2, thickness, color, scissor).submit(this)
}
