package foo.starred.cascade.primitives.impl

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.Identifier

open class ImagePrimitive : IPrimitiveElement<ImagePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    var sprite: Boolean = false

    var location: Identifier? = null
    var pipeline: RenderPipeline = RenderPipelines.GUI_TEXTURED

    var u0: Float = 0f
    var v0: Float = 0f

    var u1: Int? = null
    var v1: Int? = null

    var textureWidth: Int = 256
    var textureHeight: Int = 256

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return
        val location = location ?: return

        val x = x.toInt()
        val y = y.toInt()
        val width = width.toInt()
        val height = height.toInt()

        if (sprite) {
            graphics.blitSprite(pipeline, location, x, y, width, height, color)
            super.render(graphics)

            return
        }

        graphics.blit(pipeline, location, x, y, u0, v0, width, height, u1 ?: width, v1 ?: height, textureWidth, textureHeight, color)
        super.render(graphics)
    }

    companion object {
        val NONE = ImagePrimitive()

        inline fun image(block: ImagePrimitive.() -> Unit): ImagePrimitive {
            return ImagePrimitive().apply(block)
        }
    }
}