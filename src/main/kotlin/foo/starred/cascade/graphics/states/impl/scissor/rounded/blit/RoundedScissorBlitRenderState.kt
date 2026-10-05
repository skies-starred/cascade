@file:Suppress("Unused")

package foo.starred.cascade.graphics.states.impl.scissor.rounded.blit

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.Cascade.client
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.base.IRoundedGuiElementRenderState
import foo.starred.cascade.internal.scissor.rounded.data.CascadeRoundedScissorPipeline
import foo.starred.cascade.internal.scissor.rounded.impl.CascadeRoundedScissorSetup
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import org.joml.Matrix3x2fc

class RoundedScissorBlitRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val color: CascadeGeometricColor,
    val radius: CascadeGeometricRadius,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : CascadeGuiElementRenderState(CascadeRoundedScissorPipeline.BLIT, scissor, bounds), IRoundedGuiElementRenderState {
    override fun textureSetup(): TextureSetup {
        CascadeRoundedScissorSetup.validate(client.window.width, client.window.height)
        //~ if >= 26.2 'client.mainRenderTarget' -> 'client.gameRenderer.mainRenderTarget()'
        return TextureSetup.singleTexture(CascadeRoundedScissorSetup.target?.colorTextureView ?: client.mainRenderTarget.colorTextureView!!, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        vertexConsumer.quad(pose, x0, y0, x1, y1, color, radius)
    }
}
