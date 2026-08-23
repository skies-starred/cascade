package foo.starred.cascade.states.extensions.shadow

import foo.starred.cascade.geometry.CascadeGeometricOffset
import foo.starred.cascade.geometry.CascadeGeometricRadius
import foo.starred.cascade.states.impl.shadow.InnerShadowRenderState
import foo.starred.cascade.utils.bounds
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.innerShadow(x: Float, y: Float, width: Float, height: Float, offset: CascadeGeometricOffset, blur: Float, color: Int, radius: CascadeGeometricRadius, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose0 = pose ?: Matrix3x2f(pose())
    val bounds0 = bounds ?: bounds(x, y, x1, y1, pose0, scissor)

    InnerShadowRenderState(pose0, x, y, x1, y1, offset, blur, color, radius, scissor, bounds0).submit(this)
}
