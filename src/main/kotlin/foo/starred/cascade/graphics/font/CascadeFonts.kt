@file:Suppress("Unused")

package foo.starred.cascade.graphics.font

import foo.starred.cascade.graphics.font.data.font.impl.TtfFontData
import foo.starred.cascade.graphics.font.rendering.impl.FontRenderer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence
import java.io.InputStream

object CascadeFonts {
    val sans: FontRenderer = FontRenderer(TtfFontData(resource("/assets/cascade/font/cascade.ttf")), TtfFontData(resource("/assets/cascade/font/cascade_bold.ttf")))

    @Deprecated("Use sans instead", ReplaceWith("sans"))
    val arial: FontRenderer
        get() = sans

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, Component.literal(text), x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component.visualOrderText, x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = true, size: Number = 12) {
        sans.extract(graphics, sequence, x, y, color, shadow, size)
    }

    @Deprecated("Use CascadeGeometricColor instead", ReplaceWith("extract(graphics, text, x, y, CascadeGeometricColor(color), shadow, size)", "foo.starred.cascade.graphics.geometry.CascadeGeometricColor"))
    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, text, x, y, CascadeGeometricColor(color), shadow, size)
    }

    @Deprecated("Use CascadeGeometricColor instead", ReplaceWith("extract(graphics, component, x, y, CascadeGeometricColor(color), shadow, size)", "foo.starred.cascade.graphics.geometry.CascadeGeometricColor"))
    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component, x, y, CascadeGeometricColor(color), shadow, size)
    }

    @Deprecated("Use CascadeGeometricColor instead", ReplaceWith("extract(graphics, sequence, x, y, CascadeGeometricColor(color), shadow, size)", "foo.starred.cascade.graphics.geometry.CascadeGeometricColor"))
    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, sequence, x, y, CascadeGeometricColor(color), shadow, size)
    }

    fun resource(path: String): InputStream {
        return CascadeFonts::class.java.getResourceAsStream(path) ?: error("Could not find resource: $path")
    }
}
