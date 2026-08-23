package foo.starred.cascade.graphics.extensions.triangle

import foo.starred.cascade.graphics.states.triangle.TriangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.triangle(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (color ushr 24 == 0) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    TriangleRenderState(pose, x0, y0, x1, y1, x2, y2, color, scissor).submit(this)
}
