package foo.starred.cascade.animation.interpolator.easing.impl

import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator
import kotlin.math.pow

object EaseOutEasingInterpolator : IEasingInterpolator {
    override fun get(float: Float): Float {
        return 1f - (1f - float).pow(2)
    }
}
