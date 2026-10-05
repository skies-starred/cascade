package foo.starred.cascade.graphics.states.impl.scissor.rounded.start

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.internal.scissor.rounded.data.CascadeRoundedScissorPipeline
import net.minecraft.client.gui.navigation.ScreenRectangle

class RoundedScissorStartRenderState(
    val bounds: ScreenRectangle = ScreenRectangle(0, 0, 1, 1)
) : CascadeGuiElementRenderState(CascadeRoundedScissorPipeline.START, null, bounds) {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        repeat(4) {
            vertexConsumer.addVertex(0f, 0f, 0f).setColor(0).setUv(0f, 0f).setUv1(0, 0).setUv2(0, 0)
        }
    }
}
