@file:Suppress("Unused")

package foo.starred.cascade.animation.data

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.base.IAnimatable
import foo.starred.cascade.animation.base.IAnimation
import foo.starred.cascade.animation.enums.CascadeAnimations
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AnimatableColor(val element: IPrimitiveElement<*>) : IAnimatable {
    override var function: (() -> Unit)? = null

    var value: CascadeGeometricColor = element.color
        private set

    private var from: CascadeGeometricColor = value
    private var to: CascadeGeometricColor = value
    private var duration: Float = 0f
    private var elapsed: Float = 0f
    private var easing: IAnimation = CascadeAnimations.LINEAR

    fun animate(manager: Animation, target: CascadeGeometricColor, duration0: Number, easing0: IAnimation = CascadeAnimations.LINEAR, function0: (() -> Unit)? = null) {
        if (target == to && this.easing === easing0) return

        from = value
        to = target
        duration = duration0.toFloat()
        easing = easing0
        elapsed = 0f
        function = function0

        if (duration <= 0f) {
            value = to
            element.color = value
            elapsed = duration
            function?.invoke()
            function = null
            return
        }

        manager.track(this)
    }

    fun snap(target: CascadeGeometricColor) {
        value = target
        from = value
        to = value
        element.color = value
        elapsed = duration
    }

    override fun advance(delta: Float): Boolean {
        elapsed += delta

        if (elapsed >= duration || duration <= 0f) {
            value = to
            element.color = value
            return false
        }

        val ease = easing.apply(elapsed / duration)

        value = CascadeGeometricColor(interpolate(from.tl, to.tl, ease), interpolate(from.tr, to.tr, ease), interpolate(from.bl, to.bl, ease), interpolate(from.br, to.br, ease))
        element.color = value
        return true
    }

    private fun interpolate(from: Int, to: Int, ease: Float): Int {
        val a1 = (from ushr 24) and 0xFF
        val r1 = (from ushr 16) and 0xFF
        val g1 = (from ushr 8) and 0xFF
        val b1 = from and 0xFF

        val a2 = (to ushr 24) and 0xFF
        val r2 = (to ushr 16) and 0xFF
        val g2 = (to ushr 8) and 0xFF
        val b2 = to and 0xFF

        val a = (a1 + (a2 - a1) * ease).toInt()
        val r = (r1 + (r2 - r1) * ease).toInt()
        val g = (g1 + (g2 - g1) * ease).toInt()
        val b = (b1 + (b2 - b1) * ease).toInt()

        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }

    companion object {
        fun <T : IPrimitiveElement<T>> T.animateColor(color1: CascadeGeometricColor, duration: Number, easing: IAnimation = CascadeAnimations.LINEAR, function: (() -> Unit)? = null): T {
            val manager = root.animations
            if (manager == null) {
                color = color1
                function?.invoke()
                return self
            }

            val current = `animation$color` ?: AnimatableColor(this).also { `animation$color` = it }
            current.animate(manager, color1, duration, easing, function)
            return self
        }
    }
}
