@file:Suppress("Unused")

package foo.starred.cascade.animation.enums

import foo.starred.cascade.animation.base.IAnimation
import kotlin.math.pow

enum class CascadeAnimations(private val fn: (Float) -> Float) : IAnimation {
    LINEAR({ it }),
    EASE_IN({ it * it }),
    EASE_OUT({ 1f - (1f - it).pow(2) }),
    EASE_IN_OUT({ if (it < 0.5f) 2f * it * it else 1f - (-2f * it + 2f).pow(2) / 2f });

    override fun apply(t: Float): Float {
        return fn(t)
    }
}