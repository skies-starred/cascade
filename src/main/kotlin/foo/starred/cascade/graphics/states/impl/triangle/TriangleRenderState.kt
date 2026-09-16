package foo.starred.cascade.graphics.states.impl.triangle

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2fc
import kotlin.math.max
import kotlin.math.min

class TriangleRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    val color: CascadeGeometricColor,
    val scissor: ScreenRectangle? = null
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds(min(x0, min(x1, x2)), min(y0, min(y1, y2)), max(x0, max(x1, x2)), max(y0, max(y1, y2)), pose, scissor)) {
    private val x00 = min(x0, min(x1, x2))
    private val y00 = min(y0, min(y1, y2))
    private val x01 = max(x0, max(x1, x2))
    private val y01 = max(y0, max(y1, y2))

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val p1x = (x1 - x0).toInt().toShort().toInt() and 0xFFFF
        val p1y = (y1 - y0).toInt().toShort().toInt() and 0xFFFF
        val p2x = (x2 - x0).toInt().toShort().toInt() and 0xFFFF
        val p2y = (y2 - y0).toInt().toShort().toInt() and 0xFFFF

        fun vertex(x: Float, y: Float, color: Int) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(x - x0, y - y0).setUv1(p1x, p1y).setUv2(p2x, p2y)
        }

        vertex(x00, y00, color.tl)
        vertex(x00, y01, color.bl)
        vertex(x01, y01, color.br)
        vertex(x01, y00, color.tr)
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("triangle", CascadeVertexFormats.UV3)
    }
}
