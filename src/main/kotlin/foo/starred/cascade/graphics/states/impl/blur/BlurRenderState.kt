@file:Suppress("Unused")

package foo.starred.cascade.graphics.states.impl.blur

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.blur.data.CascadeBlurBlend
import foo.starred.cascade.graphics.blur.impl.CascadeBlurSetup
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.sampler.impl.CascadeSamplers
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix3x2fc
import kotlin.math.min

class BlurRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val color: CascadeGeometricColor,
    val radius: CascadeGeometricRadius,
    val blur: Float,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds) {
    private val blend: CascadeBlurBlend = CascadeBlurBlend.get(blur)

    override fun textureSetup(): TextureSetup {
        return CascadeBlurSetup.setup(blend)
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val width = (x1 - x0).toInt()
        val height = (y1 - y0).toInt()

        val tl = (min(radius.tl, 25.5f) * 10f).toInt()
        val tr = (min(radius.tr, 25.5f) * 10f).toInt()
        val bl = (min(radius.bl, 25.5f) * 10f).toInt()
        val br = (min(radius.br, 25.5f) * 10f).toInt()

        val u2x = (tr shl 8) or tl
        val u2y = (bl shl 8) or br
        val blend = blend.blend

        fun vertex(x: Float, y: Float, u: Float, v: Float, color: Int) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(u, v).setUv1(width, height).setUv2(u2x, u2y).setNormal(blend, 0f, 0f)
        }

        vertex(x0, y0, 0f, 0f, color.tl)
        vertex(x0, y1, 0f, height.toFloat(), color.bl)
        vertex(x1, y1, width.toFloat(), height.toFloat(), color.br)
        vertex(x1, y0, width.toFloat(), 0f, color.tr)
    }

    companion object {
        val PIPELINE: RenderPipeline = cascadeRenderPipeline("blur", CascadeVertexFormats.UV3_NORMAL, "core/effects/blur/impl/blur", RenderPipelines.GUI_TEXTURED_SNIPPET) {
            data.sampler(CascadeSamplers.SAMPLER1)
        }
    }
}
