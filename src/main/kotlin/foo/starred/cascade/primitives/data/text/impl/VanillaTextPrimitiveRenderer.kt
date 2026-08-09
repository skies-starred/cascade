package foo.starred.cascade.primitives.data.text.impl

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.primitives.data.text.base.ITextPrimitiveRenderer
import foo.starred.cascade.primitives.impl.TextPrimitive
import foo.starred.cascade.extensions.text.extractText
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

object VanillaTextPrimitiveRenderer : ITextPrimitiveRenderer {
    override fun width(text: FormattedCharSequence, size: Float): Float {
        return (client.font?.width(text)?.toFloat() ?: 0f) * (size / 8f)
    }

    override fun height(text: FormattedCharSequence, size: Float): Float {
        return size
    }

    override fun render(graphics: GuiGraphicsExtractor, primitive: TextPrimitive) {
        val pose = graphics.pose()

        val x = primitive.x.toInt()
        val y = primitive.y.toInt()
        val width = primitive.width.toInt()

        val text = primitive.text0
        val texts = primitive.texts0

        val center = primitive.center
        val scale = primitive.textSize / 8f

        if (scale == 1f) {
            if (texts != null) graphics.extractText(texts, x, y, primitive.shadow, primitive.color, center = if (center) texts.indices else IntRange.EMPTY)
            else graphics.extractText(text, if (center) x + (width / 2) else x, y, primitive.shadow, primitive.color, center)

            super.render(graphics, primitive)
            return
        }

        pose.pushMatrix()
        pose.translate(x.toFloat(), y.toFloat())
        pose.scale(scale, scale)

        if (texts != null) graphics.extractText(texts, 0, 0, primitive.shadow, primitive.color, center = if (center) texts.indices else IntRange.EMPTY)
        else graphics.extractText(text, if (center) (width / 2f / scale).toInt() else 0, 0, primitive.shadow, primitive.color)

        pose.popMatrix()
        super.render(graphics, primitive)
    }
}