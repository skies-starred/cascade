@file:Suppress("Unused")

package foo.starred.cascade.graphics.geometry

import foo.starred.cascade.Cascade.client

open class CascadeGeometricResolution(
    val width: Float,
    val height: Float,
    val guiScale: Float = 1f
) {
    fun of(scale: Number): CascadeGeometricResolution {
        return CascadeGeometricResolution(width, height, scale.toFloat())
    }

    companion object {
        val NONE = CascadeGeometricResolution(0f, 0f, 1f)
        val MINECRAFT = CascadeGeometricResolution(-1f, -1f, 1f)
        val HD = CascadeGeometricResolution(1280f, 720f, 1f)
        val FHD = CascadeGeometricResolution(1920f, 1080f, 1f)
        val QHD = CascadeGeometricResolution(2560f, 1440f, 1f)
        val UHD = CascadeGeometricResolution(3840f, 2160f, 1f)

        fun compute(resolution: CascadeGeometricResolution, width: Number, height: Number, block: (scale: Float, width: Float, height: Float) -> Unit) {
            val width = width.toFloat()
            val height = height.toFloat()
            val scale1 = client.window.guiScale.toFloat().coerceAtLeast(1f)

            if (resolution === NONE) {
                return block(1f / scale1, client.window.width.toFloat(), client.window.height.toFloat())
            }

            if (resolution === MINECRAFT || resolution.width <= 0f || resolution.height <= 0f) {
                return block(1f, width, height)
            }

            val height1 = resolution.height / resolution.guiScale
            val scale = (height / height1).coerceAtLeast(0.001f)

            block(scale, width / scale, height / scale)
        }
    }
}
