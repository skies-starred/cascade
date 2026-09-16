package foo.starred.cascade.graphics.extensions.triangle

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.impl.triangle.TriangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.triangle(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    TriangleRenderState(pose, x0, y0, x1, y1, x2, y2, color, scissor).submit(this)
}

fun GuiGraphicsExtractor.triangle(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    triangle(x0, y0, x1, y1, x2, y2, CascadeGeometricColor(color), pose, scissor)
}
