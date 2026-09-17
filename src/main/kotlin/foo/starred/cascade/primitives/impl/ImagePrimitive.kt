package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.image.image
import foo.starred.cascade.graphics.extensions.image.sprite
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.impl.image.data.CascadeImageFilter
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveRounded
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.resources.Identifier

open class ImagePrimitive : IPrimitiveElement<ImagePrimitive>(), IPrimitiveRounded {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: CascadeGeometricColor = CascadeGeometricColor.WHITE
    override var radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO

    var sprite: Boolean = false
    var rotation: Float = 0f

    var location: Identifier? = null
    var filter: CascadeImageFilter = CascadeImageFilter.LINEAR

    var u0: Float = 0f
    var v0: Float = 0f

    var u1: Int? = null
    var v1: Int? = null

    var textureWidth: Int = 256
    var textureHeight: Int = 256

    override fun draw(graphics: GuiGraphicsExtractor) {
        val location = location ?: return

        if (rotation != 0f) {
            val x = x + width / 2f
            val y = y + height / 2f
            graphics.pose().pushMatrix()
            graphics.pose().translate(x, y)
            graphics.pose().rotate(rotation * (Math.PI.toFloat() / 180f))
            graphics.pose().translate(-x, -y)
        }

        if (sprite) {
            graphics.sprite(location, x, y, width, height, color, radius, filter)
            if (rotation != 0f) graphics.pose().popMatrix()
            return
        }

        val u00 = u0 / textureWidth.toFloat()
        val v00 = v0 / textureHeight.toFloat()
        val u01 = (u0 + (u1 ?: textureWidth).toFloat()) / textureWidth.toFloat()
        val v01 = (v0 + (v1 ?: textureHeight).toFloat()) / textureHeight.toFloat()

        graphics.image(location, x, y, width, height, u00, v00, u01, v01, color, radius, filter)

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
