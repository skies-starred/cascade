package foo.starred.cascade.graphics.states.impl.circle

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2fc

class CircleRenderState(
    val pose: Matrix3x2fc,
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: CascadeGeometricColor,
    val scissor: ScreenRectangle? = null
) : CascadeGuiElementRenderState(PIPELINE, scissor, if (radius > 0f) bounds(x - radius, y - radius, x + radius, y + radius, pose, scissor) else null) {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (_bounds == null) return

        val x0 = x - radius
        val y0 = y - radius
        val x1 = x + radius
        val y1 = y + radius

        vertexConsumer.addVertexWith2DPose(pose, x0, y0).setColor(color.tl).setUv(-1f, -1f)
        vertexConsumer.addVertexWith2DPose(pose, x0, y1).setColor(color.bl).setUv(-1f, 1f)
        vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(color.br).setUv(1f, 1f)
        vertexConsumer.addVertexWith2DPose(pose, x1, y0).setColor(color.tr).setUv(1f, -1f)
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("circle", CascadeVertexFormats.UV)
    }
}
