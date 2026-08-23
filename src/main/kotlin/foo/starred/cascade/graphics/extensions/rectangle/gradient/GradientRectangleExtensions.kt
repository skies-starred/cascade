package foo.starred.cascade.graphics.extensions.rectangle.gradient

import foo.starred.cascade.graphics.states.rectangle.gradient.GradientRectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.gradientRectangle(x: Float, y: Float, width: Float, height: Float, tl: Int, tr: Int, bl: Int, br: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    GradientRectangleRenderState(pose, x, y, x + width, y + height, tl, tr, bl, br, scissor).submit(this)
}

@JvmOverloads
fun GuiGraphicsExtractor.gradientRectangle(x: Float, y: Float, width: Float, height: Float, from: Int, to: Int, vertical: Boolean = true, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (vertical) gradientRectangle(x, y, width, height, from, from, to, to, pose, scissor)
    else gradientRectangle(x, y, width, height, from, to, from, to, pose, scissor)
}
