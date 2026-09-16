package foo.starred.cascade.graphics.states.impl.rectangle.solid

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.utils.bounds
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix3x2fc
import kotlin.math.max
import kotlin.math.min

class SolidRectangleRenderState(
    val pose: Matrix3x2fc,
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    val color: CascadeGeometricColor,
    val scissor: ScreenRectangle? = null
) : CascadeGuiElementRenderState(RenderPipelines.GUI, scissor) {
    val x00 = min(x0, x1)
    val y00 = min(y0, y1)
    val x01 = max(x0, x1)
    val y01 = max(y0, y1)

    override fun bounds(): ScreenRectangle? {
        return bounds(x00, y00, x01, y01, pose, _scissor)
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose, x00, y00).setColor(color.tl)
        vertexConsumer.addVertexWith2DPose(pose, x00, y01).setColor(color.bl)
        vertexConsumer.addVertexWith2DPose(pose, x01, y01).setColor(color.br)
        vertexConsumer.addVertexWith2DPose(pose, x01, y00).setColor(color.tr)
    }
}
