@file:Suppress("CanBeParameter")

package foo.starred.cascade.animation.type.impl

import foo.starred.cascade.animation.type.base.IAnimationType
import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator
import foo.starred.cascade.animation.interpolator.easing.impl.LinearEasingInterpolator
import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import kotlin.reflect.KMutableProperty0
import kotlin.time.Duration
import kotlin.time.DurationUnit

class PropertyAnimationType<V>(
    val property: KMutableProperty0<V>,
    val target: V,
    val duration: Duration,
    var interpolate: IPropertyInterpolator<V>,
    val easing: IEasingInterpolator = LinearEasingInterpolator,
    override var function: (() -> Unit)? = null
) : IAnimationType {
    private val _duration: Float = duration.toDouble(DurationUnit.SECONDS).toFloat()
    private val from: V = property.get()
    private var elapsed: Float = 0f

    override fun advance(delta: Float): Boolean {
        elapsed += delta

        if (elapsed >= _duration || _duration <= 0f) {
            property.set(target)
            return false
        }

        property.set(interpolate.get(from, target, easing.get(elapsed / _duration)))
        return true
    }
}
