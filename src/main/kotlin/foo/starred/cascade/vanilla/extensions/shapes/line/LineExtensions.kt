package foo.starred.cascade.vanilla.extensions.shapes.line

import foo.starred.cascade.primitives.states.FloatLineRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f
import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

@JvmOverloads
fun GuiGraphicsExtractor.line(x1: Int, y1: Int, x2: Int, y2: Int, color: Int, thickness: Int = 1) {
    val dx = x2 - x1
    val dy = y2 - y1
    val length = sqrt((dx * dx + dy * dy).toDouble())
    if (length <= 0f) return

    val pose = pose()
    pose.pushMatrix()

    pose.translate(x1.toFloat(), y1.toFloat())
    pose.rotate(atan2(dy.toFloat(), dx.toFloat()))

    if (thickness == 1) {
        fill(0, 0, length.toInt(), 1, color)
        pose.popMatrix()
        return
    }

    val half = thickness / 2f
    fill(0, floor(-half).toInt(), length.toInt(), ceil(half).toInt(), color)

    pose.popMatrix()
}

@JvmOverloads
@JvmName("line_float")
fun GuiGraphicsExtractor.line(x1: Float, y1: Float, x2: Float, y2: Float, color: Int, thickness: Float = 1f, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val x3 = x2 - x1
    val y3 = y2 - y1
    if (x3 * x3 + y3 * y3 <= 0f) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(FloatLineRenderState(pose, x1, y1, x2, y2, thickness, color, scissor))
}