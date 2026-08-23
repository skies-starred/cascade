package foo.starred.cascade.graphics.extensions.shadow

import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.shadow.DropShadowRenderState
import foo.starred.cascade.utils.bounds
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.dropShadow(x: Float, y: Float, width: Float, height: Float, offset: CascadeGeometricOffset, blur: Float, spread: Float, color: Int, radius: CascadeGeometricRadius, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val expand = spread + blur

    val x0 = x + offset.x - expand
    val y0 = y + offset.y - expand
    val x1 = x + width + offset.x + expand
    val y1 = y + height + offset.y + expand
    val radius = if (spread != 0f) radius + spread else radius

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()
    val bounds = bounds ?: bounds(x0, y0, x1, y1, pose, scissor)

    DropShadowRenderState(pose, x0, y0, x1, y1, width + spread * 2f, height + spread * 2f, color, radius, blur, scissor, bounds).submit(this)
}
