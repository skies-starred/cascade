package foo.starred.cascade.extensions.triangle

import foo.starred.cascade.primitives.states.TriangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

@JvmOverloads
fun GuiGraphicsExtractor.triangle(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, color: Int, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    if (color ushr 24 == 0) return

    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(TriangleRenderState(pose, x0, y0, x1, y1, x2, y2, color, scissor))
}