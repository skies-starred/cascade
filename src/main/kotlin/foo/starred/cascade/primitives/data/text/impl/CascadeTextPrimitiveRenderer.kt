@file:Suppress("Unused")

package foo.starred.cascade.primitives.data.text.impl

import foo.starred.cascade.font.CascadeFonts
import foo.starred.cascade.primitives.data.text.base.ITextPrimitiveRenderer
import foo.starred.cascade.primitives.impl.TextPrimitive
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

object CascadeTextPrimitiveRenderer : ITextPrimitiveRenderer {
    private val height: Float by lazy {
        CascadeFonts.arial.regular.height
    }

    override fun width(text: FormattedCharSequence, size: Float): Float {
        return CascadeFonts.arial.width(text, size)
    }

    override fun height(text: FormattedCharSequence, size: Float): Float {
        return height * size
    }

    override fun render(graphics: GuiGraphicsExtractor, primitive: TextPrimitive) {
        val x = primitive.x
        val y = primitive.y
        val width = primitive.width

        val text = primitive.text0
        val texts = primitive.texts0

        val center = primitive.center
        val size = primitive.textSize

        val height = height * size
        val font = CascadeFonts.arial

        if (texts != null) {
            var y = y

            for (line in texts) {
                font.extract(graphics, line, if (center) x + (width / 2f) - (font.width(line, size, primitive.cached) / 2f) else x, y, primitive.color, primitive.shadow, size, primitive.cached)
                y += height + 2f
            }

            super.render(graphics, primitive)
            return
        }

        font.extract(graphics, text, if (center) x + (width / 2f) - (font.width(text, size, primitive.cached) / 2f) else x, y, primitive.color, primitive.shadow, size, primitive.cached)
        super.render(graphics, primitive)
    }
}