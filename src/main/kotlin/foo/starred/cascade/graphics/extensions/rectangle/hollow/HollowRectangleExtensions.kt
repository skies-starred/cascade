package foo.starred.cascade.graphics.extensions.rectangle.hollow

import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.rectangle.hollow.HollowRectangleRenderState
import foo.starred.cascade.utils.bounds
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.hollowRectangle(x: Float, y: Float, width: Float, height: Float, thickness: Float, color: Int, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose = pose ?: Matrix3x2f(pose())
    val bounds = bounds ?: bounds(x, y, x1, y1, pose, scissor)

    HollowRectangleRenderState(pose, x, y, x1, y1, thickness, color, radius, scissor, bounds).submit(this)
}
