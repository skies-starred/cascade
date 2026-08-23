package foo.starred.cascade.graphics.extensions.shadow

import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.shadow.InnerShadowRenderState
import foo.starred.cascade.utils.bounds
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.innerShadow(x: Float, y: Float, width: Float, height: Float, offset: CascadeGeometricOffset, blur: Float, color: Int, radius: CascadeGeometricRadius, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()
    val bounds = bounds ?: bounds(x, y, x1, y1, pose, scissor)

    InnerShadowRenderState(pose, x, y, x1, y1, offset, blur, color, radius, scissor, bounds).submit(this)
}
