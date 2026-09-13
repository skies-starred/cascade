package foo.starred.cascade.graphics.font.rendering.cache

//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.rectangle.solid.SolidRectangleRenderState
import foo.starred.cascade.graphics.states.rectangle.textured.TexturedRectangleRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.render.TextureSetup
import org.joml.Matrix3x2f

class GlyphElement(
    private val x: Float,
    private val italic: Boolean,
    private val pipeline: RenderPipeline,
    private val textureSetup: TextureSetup,
    private val x0: Float,
    private val y0: Float,
    private val x1: Float,
    private val y1: Float,
    private val u0: Float,
    private val u1: Float,
    private val v0: Float,
    private val v1: Float,
    private val color: CascadeGeometricColor,
    private val shade: CascadeGeometricColor,
    private val shadow: Boolean,
    private val strike: Boolean,
    private val under: Boolean,
    private val advance: Float,
    private val size: Float
) {
    fun submit(graphics: GuiGraphicsExtractor, pose: Matrix3x2f) {
        val matrix = Matrix3x2f(pose).translate(x, 0f)

        if (italic) {
            matrix.m10 = matrix.m10() + matrix.m11() * -0.25f
            matrix.m00 = matrix.m00() + matrix.m01() * -0.25f
        }

        val scissor = graphics.scissorStack.peek()
        val state = TexturedRectangleRenderState(pipeline, textureSetup, matrix, x0, y0, x1, y1, u0, u1, v0, v1, color, scissor)

        if (shadow) {
            graphics.guiRenderState.addGlyphToCurrentLayer(TexturedRectangleRenderState(pipeline, textureSetup, matrix, x0 + 0.5f, y0 + 0.5f, x1 + 0.5f, y1 + 0.5f, u0, u1, v0, v1, shade, scissor))
        }

        graphics.guiRenderState.addGlyphToCurrentLayer(state)
        val size2 = size / 10f

        if (strike) {
            graphics.guiRenderState.addGlyphToCurrentLayer(SolidRectangleRenderState(matrix, 0f, size / 2f - size2 / 2f, advance, size / 2f + size2 / 2f, color, scissor))
        }

        if (under) {
            graphics.guiRenderState.addGlyphToCurrentLayer(SolidRectangleRenderState(matrix, 0f, size - size2, advance, size, color, scissor))
        }
    }
}
