package foo.starred.cascade.graphics.extensions.scissor

import net.minecraft.client.gui.GuiGraphicsExtractor

//? if >= 26.2
//import foo.starred.cascade.geometry.CascadeScreenRectangle

inline fun GuiGraphicsExtractor.scissor(x: Number, y: Number, width: Number, height: Number, block: () -> Unit) {
    val x = x.toInt()
    val y = y.toInt()
    val width = width.toInt()
    val height = height.toInt()

    //? if >= 26.2 {
    /*scissorStack.push(CascadeScreenRectangle(x, y, width, height).transformAxisAligned(pose()))
    block()
    scissorStack.pop()
    *///? } else {
    enableScissor(x, y, x + width, y + height)
    block()
    disableScissor()
    //? }
}
