@file:Suppress("Unused")

package foo.starred.cascade.graphics.font.rendering.extensions

import foo.starred.cascade.graphics.font.CascadeFonts
import foo.starred.cascade.graphics.font.rendering.impl.FontRenderer
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence

data class FontSubmission(
    val sequence: FormattedCharSequence,
    val x: Float,
    val y: Float,
    val color: Int = -1,
    val shadow: Boolean = true,
    val size: Number = 12,
    val font: FontRenderer = CascadeFonts.arial,
) {
    constructor(
        text: String,
        x: Float,
        y: Float,
        color: Int = -1,
        shadow: Boolean = true,
        size: Number = 12,
        font: FontRenderer = CascadeFonts.arial
    ) : this(Component.literal(text).visualOrderText, x, y, color, shadow, size, font)

    constructor(
        component: Component,
        x: Float,
        y: Float,
        color: Int = -1,
        shadow: Boolean = true,
        size: Number = 12,
        font: FontRenderer = CascadeFonts.arial
    ) : this(component.visualOrderText, x, y, color, shadow, size, font)

    fun extract(graphics: GuiGraphicsExtractor) {
        font.extract(graphics, sequence, x, y, color, shadow)
    }
}
