package foo.starred.cascade.primitives.states

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class RectangleRenderState(
    val pose: Matrix3x2fc,
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    val x00 = min(x0, x1)
    val y00 = min(y0, y1)
    val x01 = max(x0, x1)
    val y01 = max(y0, y1)
    val bounds: ScreenRectangle? = bounds(x00, y00, x01, y01, pose, scissor)

    override fun pipeline(): RenderPipeline = RenderPipelines.GUI
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose, x00, y00).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x00, y01).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x01, y01).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x01, y00).setColor(color)
    }

    companion object {
        private fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle? {
            val bounds = ScreenRectangle(floor(x0.toDouble()).toInt(), floor(y0.toDouble()).toInt(), ceil((x1 - x0).toDouble()).toInt(), ceil((y1 - y0).toDouble()).toInt()).transformMaxBounds(pose)
            return if (scissor != null) scissor.intersection(bounds) else bounds
        }
    }
}