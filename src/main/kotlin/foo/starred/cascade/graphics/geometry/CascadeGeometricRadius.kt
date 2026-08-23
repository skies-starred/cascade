package foo.starred.cascade.graphics.geometry

data class CascadeGeometricRadius(
    val tl: Float,
    val tr: Float,
    val bl: Float,
    val br: Float
) {
    constructor(radius: Float) : this(radius, radius, radius, radius)

    operator fun plus(a: Float): CascadeGeometricRadius {
        if (a == 0f) return this
        return CascadeGeometricRadius(tl + a, tr + a, bl + a, br + a)
    }

    operator fun minus(a: Float): CascadeGeometricRadius {
        if (a == 0f) return this
        return CascadeGeometricRadius(tl - a, tr - a, bl - a, br - a)
    }

    companion object {
        val ZERO = CascadeGeometricRadius(0f)

        fun of(radius: Float): CascadeGeometricRadius {
            return CascadeGeometricRadius(radius)
        }
    }
}
