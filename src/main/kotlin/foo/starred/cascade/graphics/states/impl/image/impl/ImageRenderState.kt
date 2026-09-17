package foo.starred.cascade.graphics.states.impl.image.impl

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.base.IRoundedGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.sampler.impl.CascadeSamplers
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import org.joml.Matrix3x2fc
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ImageRenderState(
    val textureSetup: TextureSetup,
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val u0: Float = 0f,
    val v0: Float = 0f,
    val u1: Float = 1f,
    val v1: Float = 1f,
    val color: CascadeGeometricColor = CascadeGeometricColor.WHITE,
    val radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(min(x0, x1), min(y0, y1), max(x0, x1), max(y0, y1), pose, scissor)
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds), IRoundedGuiElementRenderState {
    override fun textureSetup(): TextureSetup {
        return textureSetup
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val w0 = abs(x1 - x0) * 0.5f
        val h0 = abs(y1 - y0) * 0.5f
        val w1 = (w0 * 10f + 0.5f).toInt().coerceIn(0, 65535)
        val h1 = (h0 * 10f + 0.5f).toInt().coerceIn(0, 65535)
        val size = Float.fromBits((w1 shl 16) or (h1 and 0xFFFF))

        radius.radii { tl, tr, br, bl ->
            fun vertex(x: Float, y: Float, u: Float, v: Float, x1: Float, y1: Float, color: Int) {
                vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(pack(u, v), size).setUv1(tl, tr).setUv2(br, bl).setNormal(x1, y1, 0f)
            }

            vertex(x0, y0, u0, v0, -1f, -1f, color.tl)
            vertex(x0, y1, u0, v1, -1f, 1f, color.bl)
            vertex(x1, y1, u1, v1, 1f, 1f, color.br)
            vertex(x1, y0, u1, v0, 1f, -1f, color.tr)
        }
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("image", CascadeVertexFormats.UV3_NORMAL) {
            data.sampler(CascadeSamplers.SAMPLER0)
        }

        private fun pack(u: Float, v: Float): Float {
            val u = (u.coerceIn(0f, 1f) * 65535f + 0.5f).toInt()
            val v = (v.coerceIn(0f, 1f) * 65535f + 0.5f).toInt()
            return Float.fromBits((v shl 16) or (u and 0xFFFF))
        }
    }
}
