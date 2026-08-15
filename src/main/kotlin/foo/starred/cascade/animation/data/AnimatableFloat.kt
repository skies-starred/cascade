@file:Suppress("Unused")

package foo.starred.cascade.animation.data

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.base.IAnimatable
import foo.starred.cascade.animation.base.IAnimation
import foo.starred.cascade.animation.enums.CascadeAnimations

data class AnimatableFloat(private val initial: Number) : IAnimatable {
    override var function: (() -> Unit)? = null

    var value: Float = initial.toFloat()
        private set

    private var from: Float = value
    private var to: Float = value
    private var duration: Float = 0f
    private var elapsed: Float = 0f
    private var easing: IAnimation = CascadeAnimations.LINEAR

    fun animate(manager: Animation, target: Number, duration0: Number, easing0: IAnimation = CascadeAnimations.LINEAR, function0: (() -> Unit)? = null) {
        val t = target.toFloat()
        if (t == to && this.easing === easing0) return

        from = value
        to = t
        duration = duration0.toFloat()
        easing = easing0
        elapsed = 0f
        function = function0

        if (duration <= 0f) {
            value = to
            elapsed = duration
            function?.invoke()
            function = null
            return
        }

        manager.track(this)
    }

    fun snap(target: Number) {
        value = target.toFloat()
        from = value
        to = value
        elapsed = duration
    }

    override fun advance(delta: Float): Boolean {
        elapsed += delta

        if (elapsed >= duration || duration <= 0f) {
            value = to
            return false
        }

        val t = elapsed / duration
        value = from + (to - from) * easing.apply(t)
        return true
    }
}