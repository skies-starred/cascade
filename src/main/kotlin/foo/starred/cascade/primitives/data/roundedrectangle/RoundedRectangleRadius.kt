package foo.starred.cascade.primitives.data.roundedrectangle

data class RoundedRectangleRadius(
    val tl: Float,
    val tr: Float,
    val bl: Float,
    val br: Float
) {
    operator fun plus(a: Float): RoundedRectangleRadius {
        if (a == 0f) return this
        return RoundedRectangleRadius(tl + a, tr + a, bl + a, br + a)
    }

    operator fun minus(a: Float): RoundedRectangleRadius {
        if (a == 0f) return this
        return RoundedRectangleRadius(tl - a, tr - a, bl - a, br - a)
    }

    companion object {
        val ZERO = RoundedRectangleRadius(0f, 0f, 0f, 0f)

        fun of(radius: Float): RoundedRectangleRadius {
            return RoundedRectangleRadius(radius, radius, radius, radius)
        }
    }
}