package foo.starred.cascade.utils.blur.data

@JvmInline
value class CascadeBlurBlend(val packed: Long) {
    val tier0: Int
        get() = (packed and 0xFF).toInt()

    val tier1: Int
        get() = ((packed ushr 8) and 0xFF).toInt()

    val blend: Float
        get() = Float.fromBits((packed ushr 16).toInt())

    constructor(tier0: Int, tier1: Int, blend: Float) : this((tier0.toLong() and 0xFF) or ((tier1.toLong() and 0xFF) shl 8) or ((blend.toRawBits().toLong() and 0xFFFFFFFFL) shl 16))

    companion object {
        val ZERO = CascadeBlurBlend(0, 0, 0f)

        fun get(radius: Float): CascadeBlurBlend {
            return when {
                radius <= 0f -> ZERO
                radius < 6f -> CascadeBlurBlend(0, 1, (radius / 6f).coerceIn(0f, 1f))
                radius < 14f -> CascadeBlurBlend(1, 2, ((radius - 6f) / 8f).coerceIn(0f, 1f))
                radius < 26f -> CascadeBlurBlend(2, 3, ((radius - 14f) / 12f).coerceIn(0f, 1f))
                radius < 42f -> CascadeBlurBlend(3, 4, ((radius - 26f) / 16f).coerceIn(0f, 1f))
                radius < 64f -> CascadeBlurBlend(4, 5, ((radius - 42f) / 22f).coerceIn(0f, 1f))
                else -> CascadeBlurBlend(5, 5, 1f)
            }
        }
    }
}
