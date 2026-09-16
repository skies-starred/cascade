package foo.starred.cascade.graphics.extensions.rectangle.outline

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.impl.rectangle.solid.SolidRectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.outline(x: Float, y: Float, width: Float, height: Float, border: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, inset: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val border = if (inset) -border else border
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    val top = CascadeGeometricColor(color.tl, color.tr, color.tl, color.tr)
    val bottom = CascadeGeometricColor(color.bl, color.br, color.bl, color.br)
    val left = CascadeGeometricColor(color.tl, color.bl)
    val right = CascadeGeometricColor(color.tr, color.br)

    SolidRectangleRenderState(pose, x - border, y - border, x + width + border, y, top, scissor).submit(this)
    SolidRectangleRenderState(pose, x - border, y + height, x + width + border, y + height + border, bottom, scissor).submit(this)
    SolidRectangleRenderState(pose, x - border, y, x, y + height, left, scissor).submit(this)
    SolidRectangleRenderState(pose, x + width, y, x + width + border, y + height, right, scissor).submit(this)
}

fun GuiGraphicsExtractor.outline(x: Float, y: Float, width: Float, height: Float, border: Float, color: Int = -1, inset: Boolean = false, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    outline(x, y, width, height, border, CascadeGeometricColor(color), inset, pose, scissor)
}
