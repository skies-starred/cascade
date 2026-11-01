package foo.starred.cascade.graphics.geometry

data class CascadeGeometricRadius(
    val tl: Float,
    val tr: Float,
    val bl: Float,
    val br: Float
) {
    constructor(radius: Float) : this(radius, radius, radius, radius)

    init {
        require(tl >= 0f && tr >= 0f && bl >= 0f && br >= 0f) {
            "Radii must be non-negative (top left=$tl, top right=$tr, bottom left=$bl, bottom right=$br)"
        }
    }

    operator fun plus(other: Float): CascadeGeometricRadius {
        if (other == 0f) return this
        return calculate(this, other)
    }

    operator fun minus(other: Float): CascadeGeometricRadius {
        if (other == 0f) return this
        return calculate(this, -other)
    }

    companion object {
        val ZERO = CascadeGeometricRadius(0f)

        @Deprecated("Use constructor")
        fun of(radius: Float): CascadeGeometricRadius {
            return CascadeGeometricRadius(radius)
        }

        private inline fun calculate(primary: CascadeGeometricRadius, other: Float): CascadeGeometricRadius {
            return CascadeGeometricRadius((primary.tl + other).coerceAtLeast(0f), (primary.tr + other).coerceAtLeast(0f), (primary.bl + other).coerceAtLeast(0f), (primary.br + other).coerceAtLeast(0f))
        }
    }
}
