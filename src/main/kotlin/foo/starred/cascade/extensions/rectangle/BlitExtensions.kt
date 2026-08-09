package foo.starred.cascade.extensions.rectangle

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.Cascade.client
import foo.starred.cascade.primitives.states.TextureRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2f

@JvmOverloads
@JvmName("blit_float")
fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, textureSetup: TextureSetup, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    //~ if >= 26.1 'submitGuiElement' -> 'addGuiElement'
    guiRenderState.addGuiElement(TextureRenderState(pipeline, textureSetup, pose, x, y, x + width, y + height, u0, u1, v0, v1, color, scissor))
}

@JvmOverloads
@JvmName("blit_float_id")
fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, location: Identifier, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val texture = client.textureManager.getTexture(location)

    blit(pipeline, TextureSetup.singleTexture(texture.textureView, texture.sampler), x, y, width, height, u0, v0, u1, v1, color, pose, scissor)
}