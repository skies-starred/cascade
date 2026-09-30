package foo.starred.cascade.graphics.font.rendering.state

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import org.joml.Matrix3x2fc

class FontRenderState(
    val pipeline: RenderPipeline,
    val textureSetup: TextureSetup,
    val pose: Matrix3x2fc,
    val glyphs: List<Glyph>,
    val shadow: Boolean,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : CascadeGuiElementRenderState(pipeline, scissor, bounds) {
    override fun textureSetup(): TextureSetup {
        return textureSetup
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (shadow) {
            for (glyph in glyphs) {
                val i0 = if (glyph.italic) -0.25f * glyph.y0 else 0f
                val i1 = if (glyph.italic) -0.25f * glyph.y1 else 0f

                vertexConsumer.addVertexWith2DPose(pose, glyph.x0 + i0 + 0.5f, glyph.y0 + 0.5f).setUv(glyph.u0, glyph.v0).setColor(glyph.shade.tl)
                vertexConsumer.addVertexWith2DPose(pose, glyph.x0 + i1 + 0.5f, glyph.y1 + 0.5f).setUv(glyph.u0, glyph.v1).setColor(glyph.shade.bl)
                vertexConsumer.addVertexWith2DPose(pose, glyph.x1 + i1 + 0.5f, glyph.y1 + 0.5f).setUv(glyph.u1, glyph.v1).setColor(glyph.shade.br)
                vertexConsumer.addVertexWith2DPose(pose, glyph.x1 + i0 + 0.5f, glyph.y0 + 0.5f).setUv(glyph.u1, glyph.v0).setColor(glyph.shade.tr)
            }
        }

        for (glyph in glyphs) {
            val i0 = if (glyph.italic) -0.25f * glyph.y0 else 0f
            val i1 = if (glyph.italic) -0.25f * glyph.y1 else 0f

            vertexConsumer.addVertexWith2DPose(pose, glyph.x0 + i0, glyph.y0).setUv(glyph.u0, glyph.v0).setColor(glyph.color.tl)
            vertexConsumer.addVertexWith2DPose(pose, glyph.x0 + i1, glyph.y1).setUv(glyph.u0, glyph.v1).setColor(glyph.color.bl)
            vertexConsumer.addVertexWith2DPose(pose, glyph.x1 + i1, glyph.y1).setUv(glyph.u1, glyph.v1).setColor(glyph.color.br)
            vertexConsumer.addVertexWith2DPose(pose, glyph.x1 + i0, glyph.y0).setUv(glyph.u1, glyph.v0).setColor(glyph.color.tr)
        }
    }

    companion object {
        class Glyph(
            val x0: Float,
            val y0: Float,
            val x1: Float,
            val y1: Float,
            val u0: Float,
            val u1: Float,
            val v0: Float,
            val v1: Float,
            val color: CascadeGeometricColor,
            val shade: CascadeGeometricColor,
            val italic: Boolean = false
        )
    }
}
