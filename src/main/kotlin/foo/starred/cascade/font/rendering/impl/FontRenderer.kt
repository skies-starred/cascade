@file:Suppress("Unused")

package foo.starred.cascade.font.rendering.impl

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.font.data.FontData
import foo.starred.cascade.font.rendering.cache.GlyphElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.ARGB
import net.minecraft.util.FormattedCharSequence
import org.joml.Matrix3x2f
import java.util.concurrent.TimeUnit

class FontRenderer(identifier: Identifier) {
    private val width: Cache<String, Float> = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.MINUTES).build()
    private val layout: Cache<String, List<GlyphElement>> = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.MINUTES).build()

    private val pipeline: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cascade", "msdf"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/msdf"))
            .build()
    )

    val regular: FontData = FontData(identifier.withSuffix("/regular"))
    val bold: FontData = FontData(identifier.withSuffix("/bold"))

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12, cached: Boolean = true) {
        extract(graphics, Component.literal(text), x, y, color, shadow, size, cached)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12, cached: Boolean = true) {
        extract(graphics, component.visualOrderText, x, y, color, shadow, size, cached)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12, cached: Boolean = true) {
        val size = size.toFloat()

        if (!cached) {
            val matrix = Matrix3x2f(graphics.pose()).translate(x.toFloat(), y.toFloat())
            val layout = extract0(sequence, size, color, shadow)
            for (element in layout) element.submit(graphics, matrix)
            return
        }

        val string = StringBuilder()
        var hash = 0

        sequence.accept { _, style, codepoint ->
            string.appendCodePoint(codepoint)
            hash = hash * 31 + style.hashCode()
            true
        }

        val matrix = Matrix3x2f(graphics.pose()).translate(x.toFloat(), y.toFloat())
        val layout = layout.get("$string|$hash|$size|$color|$shadow") { extract0(sequence, size, color, shadow) }

        for (element in layout) element.submit(graphics, matrix)
    }

    fun width(text: String, size: Number = 12, cached: Boolean = true): Float {
        return width(Component.literal(text), size, cached)
    }

    fun width(component: Component, size: Number = 12, cached: Boolean = true): Float {
        return width(component.visualOrderText, size, cached)
    }

    fun width(sequence: FormattedCharSequence, size: Number = 12, cached: Boolean = true): Float {
        val size = size.toFloat()

        if (!cached) {
            return width0(sequence, size)
        }

        val string = StringBuilder()
        var hash = 0

        sequence.accept { _, style, codepoint ->
            string.appendCodePoint(codepoint)
            hash = hash * 31 + style.hashCode()
            true
        }

        return width.get("$string|$hash|$size") {
            width0(sequence, size)
        }
    }

    private fun extract0(sequence: FormattedCharSequence, size: Float, color: Int, shadow: Boolean): List<GlyphElement> {
        val elements = mutableListOf<GlyphElement>()
        var x = 0f

        sequence.accept { _, style, codepoint ->
            val font = if (style.isBold) bold else regular
            val base = font.glyph(codepoint.toChar()) ?: return@accept true
            val glyph = (if (style.isObfuscated) font.glyph() else base) ?: return@accept true
            val advance = base.advance * size

            val bounds = glyph.atlasBounds
            val plane = glyph.planeBounds
            if (bounds == null || plane == null) {
                x += advance
                return@accept true
            }

            val color0 = style.color?.value?.let { (color and 0xFF000000.toInt()) or (it and 0x00FFFFFF) } ?: color
            val shade = if (shadow) style.shadowColor ?: ARGB.multiplyAlpha(ARGB.scaleRGB(color0, 0.25f), 0.55f) else color0

            val ascent = size + font.metrics.descender * size
            val offset = if (style.isObfuscated) ((base.planeBounds?.width() ?: base.advance) - plane.width()) * size / 2f else 0f

            val x0 = plane.left * size + offset
            val y0 = ascent - plane.top * size
            val x1 = x0 + plane.width() * size
            val y1 = y0 + plane.height() * size

            val u0 = (bounds.left - 0.5f) / font.atlas.width
            val u1 = (bounds.right + 0.5f) / font.atlas.width
            val v0 = 1f - (bounds.top / font.atlas.height)
            val v1 = 1f - (bounds.bottom / font.atlas.height)

            elements += GlyphElement(x, style.isItalic, pipeline, TextureSetup.singleTexture(font.texture.textureView, font.texture.sampler), x0, y0, x1, y1, u0, u1, v0, v1, color0, shade, shadow, style.isStrikethrough, style.isUnderlined, advance, size)
            x += advance
            true
        }

        return elements
    }

    private fun width0(sequence: FormattedCharSequence, size: Float): Float {
        var width = 0f

        sequence.accept { _, style, codepoint ->
            val font = if (style.isBold) bold else regular
            val glyph = font.glyph(codepoint.toChar()) ?: return@accept true

            width += glyph.advance * size
            true
        }

        return width
    }
}