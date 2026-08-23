package foo.starred.cascade.graphics.geometry

data class CascadeGeometricOffset(
    private val _x: Number,
    private val _y: Number,
) {
    val x = _x.toFloat()
    val y = _y.toFloat()

    constructor(offset: Number) : this(offset, offset)

    operator fun plus(other: CascadeGeometricOffset): CascadeGeometricOffset {
        if (other.x == 0f && other.y == 0f) return this
        return CascadeGeometricOffset(x + other.x, y + other.y)
    }

    operator fun plus(a: Float): CascadeGeometricOffset {
        if (a == 0f) return this
        return CascadeGeometricOffset(x + a, y + a)
    }

    operator fun minus(other: CascadeGeometricOffset): CascadeGeometricOffset {
        if (other.x == 0f && other.y == 0f) return this
        return CascadeGeometricOffset(x - other.x, y - other.y)
    }

    operator fun minus(a: Float): CascadeGeometricOffset {
        if (a == 0f) return this
        return CascadeGeometricOffset(x - a, y - a)
    }

    companion object {
        val ZERO = CascadeGeometricOffset(0)
    }
}
