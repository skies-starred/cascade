package foo.starred.cascade.extensions.circle

import foo.starred.cascade.primitives.states.CircleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.circle(x: Float, y: Float, radius: Float, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (radius <= 0f) return
    if (color ushr 24 == 0) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(CircleRenderState(pose, x, y, radius, color, scissor))
}