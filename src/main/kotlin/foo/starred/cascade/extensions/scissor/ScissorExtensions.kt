package foo.starred.cascade.extensions.scissor

import net.minecraft.client.gui.GuiGraphicsExtractor

//? if >= 26.2
//import foo.starred.cascade.geometry.CascadeScreenRectangle

inline fun GuiGraphicsExtractor.scissor(x: Number, y: Number, width: Number, height: Number, block: () -> Unit) {
    //? if >= 26.2 {
    /*scissorStack.push(CascadeScreenRectangle(x.toInt(), y.toInt(), width.toInt(), height.toInt()).transformAxisAligned(pose()))
    block()
    scissorStack.pop()
    *///? } else {
    enableScissor(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    block()
    disableScissor()
    //? }
}