package foo.starred.cascade.graphics.states.rectangle.textured

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.utils.bounds
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import org.joml.Matrix3x2fc
import kotlin.math.max
import kotlin.math.min

class TexturedRectangleRenderState(
    val pipeline: RenderPipeline,
    val textureSetup: TextureSetup,
    val pose: Matrix3x2fc,
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    val u0: Float,
    val u1: Float,
    val v0: Float,
    val v1: Float,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    val x00 = min(x0, x1)
    val y00 = min(y0, y1)
    val x01 = max(x0, x1)
    val y01 = max(y0, y1)
    val bounds: ScreenRectangle? = bounds(x00, y00, x01, y01, pose, scissor)

    override fun pipeline(): RenderPipeline = pipeline
    override fun textureSetup(): TextureSetup = textureSetup
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose, x00, y00).setUv(u0, v0).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x00, y01).setUv(u0, v1).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x01, y01).setUv(u1, v1).setColor(color)
        vertexConsumer.addVertexWith2DPose(pose, x01, y00).setUv(u1, v0).setColor(color)
    }
}
