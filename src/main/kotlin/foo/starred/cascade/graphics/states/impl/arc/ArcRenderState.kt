package foo.starred.cascade.graphics.states.impl.arc

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import foo.starred.cascade.utils.bounds
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2fc

class ArcRenderState(
    val pose: Matrix3x2fc,
    val x: Float,
    val y: Float,
    val radius0: Float,
    val radius1: Float,
    val angle0: Float,
    val angle1: Float,
    val rounded: Boolean,
    val color: CascadeGeometricColor,
    val scissor: ScreenRectangle? = null
) : CascadeGuiElementRenderState(PIPELINE, scissor, if (radius1 > 0f) bounds(x - radius1, y - radius1, x + radius1, y + radius1, pose, scissor) else null) {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (_bounds == null) return

        val r = radius1
        val x0 = x - r
        val y0 = y - r
        val x1 = x + r
        val y1 = y + r

        val r0 = radius0.toInt().toShort().toInt()
        val r1 = (if (rounded) -radius1 else radius1).toInt().toShort().toInt()
        val a0 = (angle0 * 10f).toInt().toShort().toInt()
        val a1 = (angle1 * 10f).toInt().toShort().toInt()

        val u1x = r0 and 0xFFFF
        val u1y = r1 and 0xFFFF
        val u2x = a0 and 0xFFFF
        val u2y = a1 and 0xFFFF

        vertexConsumer.addVertexWith2DPose(pose, x0, y0).setColor(color.tl).setUv(-r, -r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x0, y1).setColor(color.bl).setUv(-r, r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(color.br).setUv(r, r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x1, y0).setColor(color.tr).setUv(r, -r).setUv1(u1x, u1y).setUv2(u2x, u2y)
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("arc", CascadeVertexFormats.UV3)
    }
}
