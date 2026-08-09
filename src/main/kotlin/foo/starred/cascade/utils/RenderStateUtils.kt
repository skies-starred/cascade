package foo.starred.cascade.utils

import net.minecraft.client.gui.GuiGraphicsExtractor
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState

fun GuiElementRenderState.submit(graphics: GuiGraphicsExtractor) {
    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    graphics.guiRenderState.addGuiElement(this)
}