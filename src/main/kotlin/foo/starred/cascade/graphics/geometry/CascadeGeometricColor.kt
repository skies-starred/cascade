package foo.starred.cascade.graphics.geometry

import net.minecraft.util.ARGB
import java.awt.Color

data class CascadeGeometricColor(
    val tl: Int,
    val tr: Int,
    val bl: Int,
    val br: Int
) {
    constructor(color: Int) : this(color, color, color, color)
    constructor(top: Int, bottom: Int) : this(top, top, bottom, bottom)

    val visible: Boolean by lazy {
        (tl ushr 24 == 0) && (tr ushr 24 == 0) && (bl ushr 24 == 0) && (br ushr 24 == 0)
    }

    fun rgb(rgb: Int): CascadeGeometricColor {
        val rgb = rgb and 0x00FFFFFF
        return CascadeGeometricColor((tl and 0xFF000000.toInt()) or rgb, (tr and 0xFF000000.toInt()) or rgb, (bl and 0xFF000000.toInt()) or rgb, (br and 0xFF000000.toInt()) or rgb)
    }

    fun alpha(scale: Float): CascadeGeometricColor {
        return CascadeGeometricColor(ARGB.multiplyAlpha(tl, scale), ARGB.multiplyAlpha(tr, scale), ARGB.multiplyAlpha(bl, scale), ARGB.multiplyAlpha(br, scale))
    }

    fun scale(scale: Float): CascadeGeometricColor {
        return CascadeGeometricColor(ARGB.scaleRGB(tl, scale), ARGB.scaleRGB(tr, scale), ARGB.scaleRGB(bl, scale), ARGB.scaleRGB(br, scale))
    }

    companion object {
        val WHITE = CascadeGeometricColor(-1)
        val BLACK = CascadeGeometricColor(-16777216)
        val TRANSPARENT = CascadeGeometricColor(0)

        fun of(color: Int): CascadeGeometricColor {
            return CascadeGeometricColor(color)
        }

        fun of(color: Color): CascadeGeometricColor {
            return CascadeGeometricColor(color.rgb)
        }

        fun vertical(top: Int, bottom: Int): CascadeGeometricColor {
            return CascadeGeometricColor(top, top, bottom, bottom)
        }

        fun horizontal(left: Int, right: Int): CascadeGeometricColor {
            return CascadeGeometricColor(left, right, left, right)
        }
    }
}
