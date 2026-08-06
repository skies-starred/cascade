@file:Suppress("Unused")

package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.data.text.base.ITextPrimitiveRenderer
import foo.starred.cascade.primitives.data.text.impl.VanillaTextPrimitiveRenderer
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence

open class TextPrimitive : IPrimitiveElement<TextPrimitive>() {
    var text0: FormattedCharSequence = EMPTY_COMPONENT.visualOrderText
        private set

    var texts0: List<FormattedCharSequence>? = null
        private set

    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    override var interact: Boolean = false
    var shadow: Boolean = true
    var center: Boolean = false

    var type: ITextPrimitiveRenderer = VanillaTextPrimitiveRenderer
        set(value) {
            if (field == value) return
            field = value
            width = 0f
            height = 0f
            root.layout()
        }

    var text: Component = EMPTY_COMPONENT
        set(value) {
            if (field == value) return
            field = value
            text0 = value.visualOrderText
            width = 0f
            height = 0f
            root.layout()
        }

    var texts: List<Component>? = null
        set(value) {
            if (field == value) return
            field = value
            texts0 = value?.map { it.visualOrderText }
            width = 0f
            height = 0f
            root.layout()
        }

    var textSize: Float = 8f
        set(value) {
            if (field == value) return
            field = value
            width = 0f
            height = 0f
            root.layout()
        }

    override fun constrain(parent: IPrimitiveElement<*>) {
        size?.let {
            width = it.width(this, parent)
            height = it.height(this, parent)
        }

        if (width == 0f) {
            width = texts0?.maxOfOrNull { type.width(it, textSize) } ?: type.width(text0, textSize)
        }

        if (height == 0f) {
            height = texts0?.let { it.size * type.height(text0, textSize) + (it.size - 1) * 2f } ?: type.height(text0, textSize)
        }

        position?.let {
            x = it.x(this, parent)
            y = it.y(this, parent)
        }
    }

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return
        type.render(graphics, this)
    }

    companion object {
        private val EMPTY_COMPONENT = Component.empty()
        val NONE = TextPrimitive()

        inline fun text(block: TextPrimitive.() -> Unit): TextPrimitive {
            return TextPrimitive().apply(block)
        }
    }
}