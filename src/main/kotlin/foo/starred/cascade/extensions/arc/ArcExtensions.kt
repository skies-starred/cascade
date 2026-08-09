package foo.starred.cascade.extensions.arc

import foo.starred.cascade.primitives.states.ArcRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.arc(x: Float, y: Float, radius0: Float, radius1: Float, angle0: Float, angle1: Float, rounded: Boolean, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (radius1 <= 0f) return
    if (radius0 >= radius1) return
    if (color ushr 24 == 0) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(ArcRenderState(pose, x, y, radius0, radius1, angle0, angle1, rounded, color, scissor))
}

@JvmOverloads
fun GuiGraphicsExtractor.ring(x: Float, y: Float, radius0: Float, radius1: Float, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    arc(x, y, radius0, radius1, 0f, 360f, false, color, pose, scissor)
}