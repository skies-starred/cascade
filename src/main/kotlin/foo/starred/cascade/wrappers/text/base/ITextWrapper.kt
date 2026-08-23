package foo.starred.cascade.wrappers.text.base

import foo.starred.cascade.wrappers.text.data.CascadeTextWrapperData
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

interface ITextWrapper {
    fun width(text: FormattedCharSequence, size: Float): Float

    fun height(size: Float): Float

    fun render(graphics: GuiGraphicsExtractor, data: CascadeTextWrapperData)
}
