package foo.starred.cascade.graphics.extensions.rectangle.hollow

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState.Companion.bounds
import foo.starred.cascade.graphics.states.impl.rectangle.hollow.HollowRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.hollowRectangle(x: Float, y: Float, width: Float, height: Float, thickness: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, inset: Boolean = true, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val i = if (inset) 0f else thickness
    val x0 = x - i
    val y0 = y - i
    val x1 = x + width + i
    val y1 = y + height + i
    val radius = radius.expand(i)

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()
    val bounds = bounds ?: bounds(x0, y0, x1, y1, pose, scissor)

    HollowRectangleRenderState(pose, x0, y0, x1, y1, thickness, color, radius, scissor, bounds).submit(this)
}

fun GuiGraphicsExtractor.hollowRectangle(x: Float, y: Float, width: Float, height: Float, thickness: Float, color: Int = -1, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, inset: Boolean = true, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    hollowRectangle(x, y, width, height, thickness, CascadeGeometricColor(color), radius, inset, pose, scissor, bounds)
}

private fun CascadeGeometricRadius.expand(i: Float): CascadeGeometricRadius {
    if (i == 0f) return this
    if (this == CascadeGeometricRadius.ZERO) return this

    return CascadeGeometricRadius(
        if (tl > 0f) tl + i else 0f,
        if (tr > 0f) tr + i else 0f,
        if (bl > 0f) bl + i else 0f,
        if (br > 0f) br + i else 0f
    )
}
