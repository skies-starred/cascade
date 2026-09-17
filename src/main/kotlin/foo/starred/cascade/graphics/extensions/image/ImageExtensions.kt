package foo.starred.cascade.graphics.extensions.image

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState.Companion.bounds
import foo.starred.cascade.graphics.states.impl.image.data.CascadeImageFilter
import foo.starred.cascade.graphics.states.impl.image.impl.ImageRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.data.AtlasIds
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.image(textureSetup: TextureSetup, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val x1 = x + width
    val y1 = y + height
    val pose = pose ?: Matrix3x2f(pose())
    val scissor = scissor ?: scissorStack.peek()
    val bounds = bounds ?: bounds(x, y, x1, y1, pose, scissor)

    ImageRenderState(textureSetup, pose, x, y, x1, y1, u0, v0, u1, v1, color, radius, scissor, bounds).submit(this)
}

fun GuiGraphicsExtractor.image(location: Identifier, x: Float, y: Float, width: Float, height: Float, u0: Float = 0f, v0: Float = 0f, u1: Float = 1f, v1: Float = 1f, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, filter: CascadeImageFilter = CascadeImageFilter.LINEAR, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val texture = client.textureManager.getTexture(location)
    val textureSetup = TextureSetup.singleTexture(texture.textureView, filter.sampler())
    image(textureSetup, x, y, width, height, u0, v0, u1, v1, color, radius, pose, scissor, bounds)
}

fun GuiGraphicsExtractor.image(sprite: TextureAtlasSprite, x: Float, y: Float, width: Float, height: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, filter: CascadeImageFilter = CascadeImageFilter.LINEAR, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val texture = client.textureManager.getTexture(sprite.atlasLocation())
    val textureSetup = TextureSetup.singleTexture(texture.textureView, filter.sampler())

    image(textureSetup, x, y, width, height, sprite.u0, sprite.v0, sprite.u1, sprite.v1, color, radius, pose, scissor, bounds)
}

fun GuiGraphicsExtractor.sprite(location: Identifier, x: Float, y: Float, width: Float, height: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO, filter: CascadeImageFilter = CascadeImageFilter.LINEAR, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
    val sprite = client.atlasManager.getAtlasOrThrow(AtlasIds.GUI).getSprite(location)

    image(sprite, x, y, width, height, color, radius, filter, pose, scissor, bounds)
}
