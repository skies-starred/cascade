package foo.starred.cascade.extensions.rectangle

import foo.starred.cascade.primitives.states.RectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f
import java.awt.Color

@JvmOverloads
@JvmName("drawRectangle_color")
fun GuiGraphicsExtractor.rectangle(x: Int, y: Int, width: Int, height: Int, color: Color = Color.WHITE) {
    fill(x, y, x + width, y + height, color.rgb)
}

@JvmOverloads
@JvmName("drawRectangle_int")
fun GuiGraphicsExtractor.rectangle(x: Int, y: Int, width: Int, height: Int, color: Int = -1) {
    fill(x, y, x + width, y + height, color)
}

@JvmOverloads
@JvmName("drawRectangle_float_int")
fun GuiGraphicsExtractor.rectangle(x: Float, y: Float, width: Float, height: Float, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(RectangleRenderState(pose, x, y, x + width, y + height, color, scissor))
}