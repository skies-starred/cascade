package foo.starred.cascade.primitives.data.text.base

import foo.starred.cascade.primitives.impl.TextPrimitive
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.util.FormattedCharSequence

interface ITextPrimitiveRenderer {
    fun width(text: FormattedCharSequence, size: Float): Float

    fun height(text: FormattedCharSequence, size: Float): Float

    fun render(graphics: GuiGraphicsExtractor, primitive: TextPrimitive) {
        for (c in primitive.children) c.render(graphics)
    }
}