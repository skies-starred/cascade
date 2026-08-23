package foo.starred.cascade.utils

import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor

fun GuiElementRenderState.submit(graphics: GuiGraphicsExtractor) {
    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    graphics.guiRenderState.addGuiElement(this)
}

internal fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle? {
    val bounds = ScreenRectangle(floor(x0.toDouble()).toInt(), floor(y0.toDouble()).toInt(), ceil((x1 - x0).toDouble()).toInt(), ceil((y1 - y0).toDouble()).toInt()).transformMaxBounds(pose)
    return if (scissor != null) scissor.intersection(bounds) else bounds
}
