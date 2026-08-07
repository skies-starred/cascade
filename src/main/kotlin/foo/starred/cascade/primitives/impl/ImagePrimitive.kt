package foo.starred.cascade.primitives.impl

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.vanilla.extensions.shapes.rectangle.blit
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

        if (sprite) {
            graphics.blitSprite(pipeline, location, x.toInt(), y.toInt(), width.toInt(), height.toInt(), color)
            super.render(graphics)

            return
        }

        val u00 = u0 / textureWidth.toFloat()
        val v00 = v0 / textureHeight.toFloat()
        val u01 = (u0 + (u1 ?: width.toInt()).toFloat()) / textureWidth.toFloat()
        val v01 = (v0 + (v1 ?: height.toInt()).toFloat()) / textureHeight.toFloat()

        graphics.blit(pipeline, location, x, y, width, height, u00, v00, u01, v01, color)
        super.render(graphics)
    }

    companion object {
        val NONE = ImagePrimitive()

        inline fun image(block: ImagePrimitive.() -> Unit): ImagePrimitive {
            return ImagePrimitive().apply(block)
        }
    }
}