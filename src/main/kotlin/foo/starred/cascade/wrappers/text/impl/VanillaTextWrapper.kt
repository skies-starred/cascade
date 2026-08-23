package foo.starred.cascade.wrappers.text.impl

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.wrappers.text.base.ITextWrapper
import foo.starred.cascade.wrappers.text.data.CascadeTextWrapperData
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

object VanillaTextWrapper : ITextWrapper {
    override fun width(text: FormattedCharSequence, size: Float): Float {
        return client.font.width(text).toFloat() * (size / 8f)
    }

    override fun height(size: Float): Float {
        return size
    }

    override fun render(graphics: GuiGraphicsExtractor, data: CascadeTextWrapperData) {
        val pose = graphics.pose()

        val x = data.x.toInt()
        val y = data.y.toInt()
        val width = data.width.toInt()

        val text = data.text
        val texts = data.texts

        val center = data.center
        val scale = data.size / 8f

        if (scale == 1f) {
            if (texts != null) {
                graphics.extract(texts, x, y, data.color, data.shadow, center)
                return
            }

            val x1 = if (center) x + (width / 2) - (client.font.width(text) / 2) else x
            //~ if >= 26.1 'drawString(' -> 'text('
            graphics.text(client.font, text, x1, y, data.color, data.shadow)
            return
        }

        pose.pushMatrix()
        pose.translate(x.toFloat(), y.toFloat())
        pose.scale(scale, scale)

        if (texts != null) {
            graphics.extract(texts, 0, 0, data.color, data.shadow, center)
            pose.popMatrix()
            return
        }

        val x1 = if (center) ((width / 2f / scale) - (client.font.width(text) / 2f)).toInt() else 0
        //~ if >= 26.1 'drawString(' -> 'text('
        graphics.text(client.font, text, x1, 0, data.color, data.shadow)

        pose.popMatrix()
    }

    private fun GuiGraphicsExtractor.extract(texts: List<FormattedCharSequence>, x: Int, y: Int, color: Int, shadow: Boolean, center: Boolean) {
        val widths = IntArray(texts.size) { client.font.width(texts[it]) }
        val max = widths.maxOrNull() ?: 0

        for (i in texts.indices) {
            val x0 = if (center) x + (max - widths[i]) / 2 else x
            val y0 = y + i * (client.font.lineHeight + 2)
            //~ if >= 26.1 'drawString(' -> 'text('
            text(client.font, texts[i], x0, y0, color, shadow)
        }
    }
}
