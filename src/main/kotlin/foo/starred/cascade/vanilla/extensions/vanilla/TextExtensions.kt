@file:Suppress("Unused")

package foo.starred.cascade.vanilla.extensions.vanilla

import foo.starred.cascade.Cascade.client
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.util.FormattedCharSequence

//~ if >= 26.1 'drawString(' -> 'text(' {
@JvmOverloads
@JvmName("text_string")
fun GuiGraphicsExtractor.extractText(text: String, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, center: Boolean = false) {
    val xx = if (center) x - client.font.width(text) / 2 else x
    text(client.font, text, xx, y, color, shadow)
}

@JvmOverloads
@JvmName("text_component")
fun GuiGraphicsExtractor.extractText(text: Component, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, center: Boolean = false) {
    val xx = if (center) x - client.font.width(text) / 2 else x
    text(client.font, text, xx, y, color, shadow)
}

@JvmOverloads
@JvmName("text_fcs")
fun GuiGraphicsExtractor.extractText(text: FormattedCharSequence, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, center: Boolean = false) {
    val xx = if (center) x - client.font.width(text) / 2 else x
    text(client.font, text, xx, y, color, shadow)
}

@JvmOverloads
@JvmName("text_string_multi")
fun GuiGraphicsExtractor.extractText(texts: List<String>, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, spacing: Int = 2, center: IntRange = IntRange.EMPTY) {
    extractText(texts.map { Language.getInstance().getVisualOrder(FormattedText.of(it)) }, x, y, shadow, color, spacing, center)
}

@JvmOverloads
@JvmName("text_component_multi")
fun GuiGraphicsExtractor.extractText(texts: List<Component>, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, spacing: Int = 2, center: IntRange = IntRange.EMPTY) {
    extractText(texts.map { it.visualOrderText }, x, y, shadow, color, spacing, center)
}

@JvmOverloads
@JvmName("text_fcs_multi")
fun GuiGraphicsExtractor.extractText(texts: List<FormattedCharSequence>, x: Int, y: Int, shadow: Boolean = true, color: Int = -1, spacing: Int = 2, center: IntRange = IntRange.EMPTY) {
    drawTexts(texts, x, y, color, shadow, spacing, center)
}

private fun GuiGraphicsExtractor.drawTexts(lines: List<FormattedCharSequence>, x: Int, y: Int, color: Int, shadow: Boolean, spacing: Int, center: IntRange) {
    val widths = IntArray(lines.size) { client.font.width(lines[it]) }
    val max = widths.maxOrNull() ?: 0

    for (i in lines.indices) {
        val x0 = if (i in center) x + (max - widths[i]) / 2 else x
        val y0 = y + i * (client.font.lineHeight + spacing)
        text(client.font, lines[i], x0, y0, color, shadow)
    }
}
//~ }