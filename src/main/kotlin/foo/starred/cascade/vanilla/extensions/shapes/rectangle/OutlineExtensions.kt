package foo.starred.cascade.vanilla.extensions.shapes.rectangle

import net.minecraft.client.gui.GuiGraphicsExtractor

@JvmOverloads
fun GuiGraphicsExtractor.outline(x: Int, y: Int, width: Int, height: Int, border: Int, color: Int = -1, inset: Boolean = false) {
    val border = if (inset) -border else border
    fill(x - border, y - border, x + width + border, y, color)
    fill(x - border, y + height, x + width + border, y + height + border, color)
    fill(x - border, y, x, y + height, color)
    fill(x + width, y, x + width + border, y + height, color)
}