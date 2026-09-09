package foo.starred.cascade.graphics.geometry

import java.awt.Color

data class CascadeGeometricColor(
    val tl: Int,
    val tr: Int,
    val bl: Int,
    val br: Int
) {
    constructor(color: Int) : this(color, color, color, color)
    constructor(top: Int, bottom: Int) : this(top, top, bottom, bottom)

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
