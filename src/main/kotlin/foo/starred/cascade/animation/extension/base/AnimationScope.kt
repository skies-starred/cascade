@file:Suppress("unused", "unchecked_cast")

package foo.starred.cascade.animation.extension.base

import foo.starred.cascade.animation.extension.impl.animate
import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator
import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import kotlin.reflect.KMutableProperty0
import kotlin.time.Duration

class AnimationScope<T : IPrimitiveElement<T>>(
    val element: T,
    val duration: Duration,
    val easing: IEasingInterpolator
) {
    infix fun <V> KMutableProperty0<V>.to(target: V): Binding<V> {
        return Binding(this, target)
    }

    inline fun <reified C : ISizeConstraint> size(block: C.() -> Unit) {
        (element.size as? C)?.apply(block)
    }

    inline fun <reified C : IPositionConstraint> position(block: C.() -> Unit) {
        (element.position as? C)?.apply(block)
    }

    inner class Binding<V>(val property: KMutableProperty0<V>, val target: V) {
        var animation = element.animate(property, target, duration, easing = easing)

        infix fun via(interpolator: IPropertyInterpolator<*>) {
            animation?.let {
                it.interpolate = interpolator as IPropertyInterpolator<V>
                return
            }

            animation = element.animate(property, target, duration, interpolator, easing)
        }
    }
}
