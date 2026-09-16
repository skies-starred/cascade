@file:Suppress("Unused")

package foo.starred.cascade.graphics.states.impl.shadow

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2fc
import kotlin.math.min

class DropShadowRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val width: Float,
    val height: Float,
    val color: CascadeGeometricColor,
    val radius: CascadeGeometricRadius,
    val blur: Float = 0f,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds) {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val width1 = (x1 - x0).toInt()
        val height1 = (y1 - y0).toInt()

        val tl = (min(radius.tl, 25.5f) * 10f).toInt()
        val tr = (min(radius.tr, 25.5f) * 10f).toInt()
        val bl = (min(radius.bl, 25.5f) * 10f).toInt()
        val br = (min(radius.br, 25.5f) * 10f).toInt()

        val u2x = (tr shl 8) or tl
        val u2y = (bl shl 8) or br

        val blur = min(blur, 127f) / 127f

        fun vertex(x: Float, y: Float, u: Float, v: Float, color: Int) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(u, v).setUv1(width.toInt(), height.toInt()).setUv2(u2x, u2y).setNormal(0f, blur, 0f)
        }

        vertex(x0, y0, 0f, 0f, color.tl)
        vertex(x0, y1, 0f, height1.toFloat(), color.bl)
        vertex(x1, y1, width1.toFloat(), height1.toFloat(), color.br)
        vertex(x1, y0, width1.toFloat(), 0f, color.tr)
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("drop_shadow", CascadeVertexFormats.UV3_NORMAL, "core/effects/shadow/drop/drop_shadow")
    }
}
