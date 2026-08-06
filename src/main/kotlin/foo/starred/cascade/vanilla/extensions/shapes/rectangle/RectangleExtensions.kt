package foo.starred.cascade.vanilla.extensions.shapes.rectangle

import net.minecraft.client.gui.GuiGraphicsExtractor
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