package foo.starred.cascade.primitives.impl

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.extensions.rectangle.textured.blit
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
    var rotation: Float = 0f

    var location: Identifier? = null
    var pipeline: RenderPipeline = RenderPipelines.GUI_TEXTURED

    var u0: Float = 0f
    var v0: Float = 0f

    var u1: Int? = null
    var v1: Int? = null

    var textureWidth: Int = 256
    var textureHeight: Int = 256

    override fun draw(graphics: GuiGraphicsExtractor) {
        val location = location ?: return

        if (sprite) {
            graphics.blitSprite(pipeline, location, x.toInt(), y.toInt(), width.toInt(), height.toInt(), color)
            return
        }

        val u00 = u0 / textureWidth.toFloat()
        val v00 = v0 / textureHeight.toFloat()
        val u01 = (u0 + (u1 ?: textureWidth).toFloat()) / textureWidth.toFloat()
        val v01 = (v0 + (v1 ?: textureHeight).toFloat()) / textureHeight.toFloat()

        if (rotation != 0f) {
            val x = x + width / 2f
            val y = y + height / 2f
            graphics.pose().pushMatrix()
            graphics.pose().translate(x, y)
            graphics.pose().rotate(rotation * (Math.PI.toFloat() / 180f))
            graphics.pose().translate(-x, -y)
        }

        graphics.blit(pipeline, location, x, y, width, height, u00, v00, u01, v01, color)

        if (rotation != 0f) {
            graphics.pose().popMatrix()
        }
    }

    companion object {
        val NONE = ImagePrimitive()

        inline fun image(block: ImagePrimitive.() -> Unit): ImagePrimitive {
            return ImagePrimitive().apply(block)
        }
    }
}
