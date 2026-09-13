package foo.starred.cascade.graphics.extensions.circle

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.circle.CircleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.circle(x: Float, y: Float, radius: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (radius <= 0f) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    CircleRenderState(pose, x, y, radius, color, scissor).submit(this)
}

@JvmOverloads
fun GuiGraphicsExtractor.circle(x: Float, y: Float, radius: Float, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    circle(x, y, radius, CascadeGeometricColor(color), pose, scissor)
}
