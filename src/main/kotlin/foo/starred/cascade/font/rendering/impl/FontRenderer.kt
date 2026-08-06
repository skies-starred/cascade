@file:Suppress("Unused")

package foo.starred.cascade.font.rendering.impl

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.font.data.FontData
import foo.starred.cascade.font.rendering.state.RectangleRenderState
import foo.starred.cascade.font.rendering.state.TexturedRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.util.ARGB
import net.minecraft.util.FormattedCharSequence
import org.joml.Matrix3x2f

class FontRenderer(identifier: Identifier) {
    private val pipeline: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cascade", "msdf"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/msdf"))
            .build()
    )

    val regular: FontData = FontData(identifier.withSuffix("/regular"))
    val bold: FontData = FontData(identifier.withSuffix("/bold"))

    fun extract(graphics: GuiGraphicsExtractor, text: String, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, Component.literal(text), x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, component: Component, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        extract(graphics, component.visualOrderText, x, y, color, shadow, size)
    }

    fun extract(graphics: GuiGraphicsExtractor, sequence: FormattedCharSequence, x: Number, y: Number, color: Int = -1, shadow: Boolean = true, size: Number = 12) {
        val size = size.toFloat()
        val matrix = Matrix3x2f(graphics.pose()).translate(x.toFloat(), y.toFloat())

        sequence.accept { _, style, codepoint ->
            val font = if (style.isBold) bold else regular
            val base = font.glyph(codepoint.toChar()) ?: return@accept true
            val glyph = (if (style.isObfuscated) font.glyph() else base) ?: return@accept true
            val advance = base.advance * size

            val bounds = glyph.atlasBounds
            val plane = glyph.planeBounds
            if (bounds == null || plane == null) {
                matrix.translate(advance, 0f)
                return@accept true
            }

            val color0 = style.color?.value?.let { (color and 0xFF000000.toInt()) or (it and 0x00FFFFFF) } ?: color
            val shade = if (shadow) style.shadowColor ?: ARGB.multiplyAlpha(ARGB.scaleRGB(color0, 0.25f), 0.55f) else color0

            val ascent = size + font.metrics.descender * size
            val offset = if (style.isObfuscated) ((base.planeBounds?.width() ?: base.advance) - plane.width()) * size / 2f else 0f

            val x0 = plane.left * size + offset
            val y0 = ascent - plane.top * size

            val pose0 = Matrix3x2f(matrix)
            
            if (style.isItalic) {
                pose0.m10 = pose0.m10() + pose0.m11() * -0.25f
                pose0.m00 = pose0.m00() + pose0.m01() * -0.25f
            }

            val state = TexturedRectangleRenderState(pipeline, TextureSetup.singleTexture(font.texture.textureView, font.texture.sampler), pose0, x0, y0, x0 + plane.width() * size, y0 + plane.height() * size, (bounds.left - 0.5f) / font.atlas.width, (bounds.right + 0.5f) / font.atlas.width, 1f - (bounds.top / font.atlas.height), 1f - (bounds.bottom / font.atlas.height), color0, color0, graphics.scissorStack.peek())

            //~ if >= 26.1 'submitGlyphToCurrentLayer' -> 'addGlyphToCurrentLayer' {
            if (shadow) {
                graphics.guiRenderState.addGlyphToCurrentLayer(TexturedRectangleRenderState(state.pipeline, state.textureSetup, state.pose, state.x0 + .5f, state.y0 + .5f, state.x1 + .5f, state.y1 + .5f, state.u0, state.u1, state.v0, state.v1, shade, shade, state.scissorArea))
            }

            graphics.guiRenderState.addGlyphToCurrentLayer(state)

            val size2 = size / 10f

            if (style.isStrikethrough) {
                graphics.guiRenderState.addGlyphToCurrentLayer(RectangleRenderState(RenderPipelines.GUI, TextureSetup.noTexture(), state.pose, 0f, size / 2f - size2 / 2f, advance, size / 2f + size2 / 2f, color0, color0, state.scissorArea()))
            }

            if (style.isUnderlined) {
                graphics.guiRenderState.addGlyphToCurrentLayer(RectangleRenderState(RenderPipelines.GUI, TextureSetup.noTexture(), state.pose, 0f, size - size2, advance, size, color0, color0, state.scissorArea()))
            }
            //~ }

            matrix.translate(advance, 0f)
            true
        }
    }

    fun width(text: String, size: Number = 12): Float {
        return width(Component.literal(text), size)
    }

    fun width(component: Component, size: Number = 12): Float {
        return width(component.visualOrderText, size)
    }

    fun width(sequence: FormattedCharSequence, size: Number = 12): Float {
        val size = size.toFloat()
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