@file:Suppress("Unused")

package foo.starred.cascade.graphics.font

import foo.starred.cascade.graphics.font.data.font.impl.TtfFontData
import foo.starred.cascade.graphics.font.rendering.impl.FontRenderer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.utils.resource
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence

object CascadeFonts {
    var arial: FontRenderer
        private set

    var loaded: Boolean = false
        private set

    init {
        arial = FontRenderer(TtfFontData(resource("/assets/cascade/font/arial.ttf")), TtfFontData(resource("/assets/cascade/font/arial_bold.ttf")))
        loaded = true
    }

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, Component.literal(text), x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component.visualOrderText, x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        arial.extract(graphics, sequence, x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, text, x, y, CascadeGeometricColor(color), shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component, x, y, CascadeGeometricColor(color), shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, sequence, x, y, CascadeGeometricColor(color), shadow, size)
    }
}
