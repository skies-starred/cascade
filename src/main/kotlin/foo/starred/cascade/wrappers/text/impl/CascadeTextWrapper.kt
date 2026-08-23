package foo.starred.cascade.wrappers.text.impl

import foo.starred.cascade.graphics.font.CascadeFonts
import foo.starred.cascade.wrappers.text.base.ITextWrapper
import foo.starred.cascade.wrappers.text.data.CascadeTextWrapperData
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

object CascadeTextWrapper : ITextWrapper {
    private val height = CascadeFonts.arial.regular.height

    override fun width(text: FormattedCharSequence, size: Float): Float {
        return CascadeFonts.arial.width(text, size)
    }

    override fun height(size: Float): Float {
        return height * size
    }

    override fun render(graphics: GuiGraphicsExtractor, data: CascadeTextWrapperData) {
        val x = data.x
        val y = data.y
        val width = data.width

        val text = data.text
        val texts = data.texts

        val center = data.center
        val size = data.size

        val height = height * size
        val font = CascadeFonts.arial

        if (texts != null) {
            var y = y

            for (line in texts) {
                font.extract(graphics, line, if (center) x + (width / 2f) - (font.width(line, size, data.cached) / 2f) else x, y, data.color, data.shadow, size, data.cached)
                y += height + 2f
            }

            return
        }

        font.extract(graphics, text, if (center) x + (width / 2f) - (font.width(text, size, data.cached) / 2f) else x, y, data.color, data.shadow, size, data.cached)
    }
}
