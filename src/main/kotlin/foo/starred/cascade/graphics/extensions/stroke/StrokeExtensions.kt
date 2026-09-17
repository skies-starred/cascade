package foo.starred.cascade.graphics.extensions.stroke

import foo.starred.cascade.graphics.extensions.rectangle.rounded.roundedRectangle
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f
import kotlin.math.atan2
import kotlin.math.sqrt

fun GuiGraphicsExtractor.stroke(x1: Float, y1: Float, x2: Float, y2: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, thickness: Float = 1f, rounded: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val x3 = x2 - x1
    val y3 = y2 - y1
    val i0 = x3 * x3 + y3 * y3
    if (i0 <= 0f) return

    val length = sqrt(i0)
    val matrix = Matrix3x2f(pose ?: pose())

    matrix.translate(x1, y1)
    matrix.rotate(atan2(y3, x3))

    val i1 = thickness / 2f
    val x = if (rounded) -i1 else 0f
    val width = if (rounded) length + thickness else length
    val radius = if (rounded) CascadeGeometricRadius(i1) else CascadeGeometricRadius.ZERO

    roundedRectangle(x, -i1, width, thickness, color, radius, matrix, scissor)
}

fun GuiGraphicsExtractor.stroke(x1: Float, y1: Float, x2: Float, y2: Float, color: Int = -1, thickness: Float = 1f, rounded: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    stroke(x1, y1, x2, y2, CascadeGeometricColor(color), thickness, rounded, pose, scissor)
}
