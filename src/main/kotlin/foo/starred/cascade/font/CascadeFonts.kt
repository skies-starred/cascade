@file:Suppress("Unused")

package foo.starred.cascade.font

import foo.starred.cascade.font.rendering.impl.FontRenderer
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.FormattedCharSequence

object CascadeFonts {
    lateinit var arial: FontRenderer
        private set

    fun init() {
        arial = FontRenderer(Identifier.fromNamespaceAndPath("cascade", "msdf/arial"))
    }

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, Component.literal(text), x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component.visualOrderText, x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        arial.extract(graphics, sequence, x, y, color, shadow, size)
    }
}