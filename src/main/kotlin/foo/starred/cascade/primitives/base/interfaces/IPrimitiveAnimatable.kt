@file:Suppress("PropertyName", "Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.data.AnimatableColor
import foo.starred.cascade.animation.data.AnimatableFloat
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveAnimatable<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var animations: Animation?
    var `animation$float`: AnimatableFloat?
    var `animation$color`: AnimatableColor?
}