@file:Suppress("Unused")

package foo.starred.cascade.graphics.states.impl.blur

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.blur.data.CascadeBlurBlend
import foo.starred.cascade.graphics.blur.impl.CascadeBlurSetup
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.base.IRoundedGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.sampler.impl.CascadeSamplers
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix3x2fc

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
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds), IRoundedGuiElementRenderState {
    private val blend: CascadeBlurBlend = CascadeBlurBlend.get(blur)

    override fun textureSetup(): TextureSetup {
        return CascadeBlurSetup.setup(blend)
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        vertexConsumer.quad(pose, x0, y0, x1, y1, color, radius) { _, _ ->
            setNormal(blend.blend, 0f, 0f)
        }
    }

    companion object {
        val PIPELINE: RenderPipeline = cascadeRenderPipeline("blur", CascadeVertexFormats.UV3_NORMAL, "core/effects/blur/impl/blur", RenderPipelines.GUI_TEXTURED_SNIPPET) {
            data.sampler(CascadeSamplers.SAMPLER1)
        }
    }
}
