package foo.starred.cascade.wrappers.text.data

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence

class CascadeTextWrapperData {
    var text: FormattedCharSequence = EMPTY
    var texts: List<FormattedCharSequence>? = null

    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 0f
    var size: Float = 12f

    var color: CascadeGeometricColor = CascadeGeometricColor.WHITE

    var cached: Boolean = false
    var center: Boolean = false
    var shadow: Boolean = false

    companion object {
        private val EMPTY = Component.empty().visualOrderText

        fun singular(text: FormattedCharSequence, x: Float, y: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = false, center: Boolean = false, width: Float = 0f, size: Float = 12f, cache: Boolean = true): CascadeTextWrapperData {
            return CascadeTextWrapperData().apply {
                this.text = text
                this.x = x
                this.y = y
                this.color = color
                this.shadow = shadow
                this.center = center
                this.width = width
                this.size = size
                this.cached = cache
            }
        }

        fun singular(text: FormattedCharSequence, x: Float, y: Float, color: Int, shadow: Boolean = false, center: Boolean = false, width: Float = 0f, size: Float = 12f, cache: Boolean = true): CascadeTextWrapperData {
            return singular(text, x, y, CascadeGeometricColor(color), shadow, center, width, size, cache)
        }

        fun multiple(texts: List<FormattedCharSequence>, x: Float, y: Float, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, shadow: Boolean = false, center: Boolean = false, width: Float = 0f, size: Float = 12f, cache: Boolean = true): CascadeTextWrapperData {
            return CascadeTextWrapperData().apply {
                this.texts = texts
                this.x = x
                this.y = y
                this.color = color
                this.shadow = shadow
                this.center = center
                this.width = width
                this.size = size
                this.cached = cache
            }
        }

        fun multiple(texts: List<FormattedCharSequence>, x: Float, y: Float, color: Int, shadow: Boolean = false, center: Boolean = false, width: Float = 0f, size: Float = 12f, cache: Boolean = true): CascadeTextWrapperData {
            return multiple(texts, x, y, CascadeGeometricColor(color), shadow, center, width, size, cache)
        }
    }
}
