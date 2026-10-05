@file:Suppress("unused", "unchecked_cast")

package foo.starred.cascade.animation.extension.impl

import foo.starred.cascade.animation.extension.base.AnimationScope
import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator
import foo.starred.cascade.animation.interpolator.easing.impl.LinearEasingInterpolator
import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import foo.starred.cascade.animation.type.impl.PropertyAnimationType
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import kotlin.reflect.KMutableProperty0
import kotlin.time.Duration

fun <V, T : IPrimitiveElement<T>> T.animate(property: KMutableProperty0<V>, to: V, duration: Duration, interpolator: IPropertyInterpolator<*>? = IPropertyInterpolator.of(to), easing: IEasingInterpolator = LinearEasingInterpolator, function: (() -> Unit)? = null): PropertyAnimationType<V>? {
    if (interpolator == null) return null

    val manager = root.animations
    if (manager == null || duration <= Duration.ZERO) {
        property.set(to)
        function?.invoke()
        return null
    }

    manager.removeIf { it is PropertyAnimationType<*> && it.property == property }
    return PropertyAnimationType(property, to, duration, interpolator as IPropertyInterpolator<V>, easing, function).also { manager.track(it) }
}

fun <T : IPrimitiveElement<T>> T.animate(duration: Duration, easing: IEasingInterpolator = LinearEasingInterpolator, block: AnimationScope<T>.() -> Unit): T {
    AnimationScope(this, duration, easing).apply(block)
    return self
}
