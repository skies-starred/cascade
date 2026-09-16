package foo.starred.cascade.graphics.extensions.rectangle.rounded

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState.Companion.bounds
import foo.starred.cascade.graphics.states.impl.rectangle.rounded.RoundedRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.roundedRectangle(x: Float, y: Float, width: Float, height: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()
    val bounds = bounds ?: bounds(x, y, x1, y1, pose, scissor)

    RoundedRectangleRenderState(pose, x, y, x1, y1, color, radius, scissor, bounds).submit(this)
}

fun GuiGraphicsExtractor.roundedRectangle(x: Float, y: Float, width: Float, height: Float, color: Int = -1, radius: CascadeGeometricRadius, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    roundedRectangle(x, y, width, height, CascadeGeometricColor(color), radius, pose, scissor, bounds)
}
