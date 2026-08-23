package foo.starred.cascade.states.extensions.blur

import foo.starred.cascade.geometry.CascadeGeometricRadius
import foo.starred.cascade.states.impl.blur.BlurRenderState
import foo.starred.cascade.utils.bounds
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.blur(x: Float, y: Float, width: Float, height: Float, color: Int, radius: CascadeGeometricRadius, blur: Float, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose0 = pose ?: Matrix3x2f(pose())
    val bounds0 = bounds ?: bounds(x, y, x1, y1, pose0, scissor)

    BlurRenderState(pose0, x, y, x1, y1, color, radius, blur, scissor, bounds0).submit(this)
}
