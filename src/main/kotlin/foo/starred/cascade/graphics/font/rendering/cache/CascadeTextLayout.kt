package foo.starred.cascade.graphics.font.rendering.cache

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.font.rendering.state.FontRenderState
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.impl.rectangle.solid.SolidRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.render.TextureSetup
import org.joml.Matrix3x2fc
import kotlin.math.max
import kotlin.math.min

class CascadeTextLayout(
    val batches: List<Batch>,
    val effects: List<Effect>
) {
    fun submit(graphics: GuiGraphicsExtractor, pose: Matrix3x2fc) {
        val scissor = graphics.scissorStack.peek()

        for (batch in batches) {
            if (batch.glyphs.isEmpty()) continue
            val x1 = if (batch.shadow) batch.x1 + 0.5f else batch.x1
            val y1 = if (batch.shadow) batch.y1 + 0.5f else batch.y1

            graphics.guiRenderState.addGlyphToCurrentLayer(FontRenderState(batch.pipeline, batch.texture, pose, batch.glyphs, batch.shadow, batch.x0, batch.y0, x1, y1, scissor))
        }

        for (effect in effects) {
            graphics.guiRenderState.addGlyphToCurrentLayer(SolidRectangleRenderState(pose, effect.x0, effect.y0, effect.x1, effect.y1, effect.color, scissor))
        }
    }

    companion object {
        class Effect(
            val x0: Float,
            val y0: Float,
            val x1: Float,
            val y1: Float,
            val color: CascadeGeometricColor
        )

        class Batch(
            val pipeline: RenderPipeline,
            val texture: TextureSetup,
            val shadow: Boolean,
            val glyphs: MutableList<FontRenderState.Companion.Glyph> = mutableListOf(),
            var x0: Float = Float.POSITIVE_INFINITY,
            var y0: Float = Float.POSITIVE_INFINITY,
            var x1: Float = Float.NEGATIVE_INFINITY,
            var y1: Float = Float.NEGATIVE_INFINITY
        ) {
            fun add(glyph: FontRenderState.Companion.Glyph) {
                glyphs += glyph

                val i0 = if (glyph.italic) -0.25f * glyph.y0 else 0f
                val i1 = if (glyph.italic) -0.25f * glyph.y1 else 0f
                val x00 = min(glyph.x0 + i0, glyph.x0 + i1)
                val x01 = max(glyph.x1 + i0, glyph.x1 + i1)

                if (x00 < x0) {
                    x0 = x00
                }

                if (glyph.y0 < y0) {
                    y0 = glyph.y0
                }

                if (x01 > x1) {
                    x1 = x01
                }

                if (glyph.y1 > y1) {
                    y1 = glyph.y1
                }
            }
        }

        class Span(val y0: Float, val y1: Float, val effects: MutableList<Effect>) {
            var color1: CascadeGeometricColor? = null
            var x0 = -1f
            var x1 = -1f

            fun push(active: Boolean, x: Float, advance: Float, color: CascadeGeometricColor) {
                if (!active) {
                    flush()
                    return
                }

                if (x0 >= 0f && x1 == x && color1 == color) {
                    x1 = x + advance
                    return
                }

                flush()
                x0 = x
                x1 = x + advance
                color1 = color
            }

            fun flush() {
                val color = color1 ?: return
                if (x0 < 0f) return

                effects += Effect(x0, y0, x1, y1, color)
                x0 = -1f
            }
        }
    }
}
