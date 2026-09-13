package foo.starred.cascade.graphics.extensions.rectangle.textured

//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.Cascade.client
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.states.rectangle.textured.TexturedRectangleRenderState
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, textureSetup: TextureSetup, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()

    TexturedRectangleRenderState(pipeline, textureSetup, pose, x, y, x + width, y + height, u0, u1, v0, v1, color, scissor).submit(this)
}

fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, textureSetup: TextureSetup, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    blit(pipeline, textureSetup, x, y, width, height, u0, v0, u1, v1, CascadeGeometricColor(color), pose, scissor)
}

fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, location: Identifier, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    val texture = client.textureManager.getTexture(location)
    blit(pipeline, TextureSetup.singleTexture(texture.textureView, texture.sampler), x, y, width, height, u0, v0, u1, v1, color, pose, scissor)
}

fun GuiGraphicsExtractor.blit(pipeline: RenderPipeline, location: Identifier, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: Int = -1, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null) {
    blit(pipeline, location, x, y, width, height, u0, v0, u1, v1, CascadeGeometricColor(color), pose, scissor)
}
