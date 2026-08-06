package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveSelf<T : IPrimitiveElement<T>> {
    val self: T
}