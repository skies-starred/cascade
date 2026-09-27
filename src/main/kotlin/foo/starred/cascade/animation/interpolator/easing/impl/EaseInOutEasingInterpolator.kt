package foo.starred.cascade.animation.interpolator.easing.impl

import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator
import kotlin.math.pow

object EaseInOutEasingInterpolator : IEasingInterpolator {
    override fun get(float: Float): Float {
        return if (float < 0.5f) 2f * float * float else 1f - (-2f * float + 2f).pow(2) / 2f
    }
}
