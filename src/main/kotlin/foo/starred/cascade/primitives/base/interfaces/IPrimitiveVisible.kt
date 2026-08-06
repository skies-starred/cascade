@file:Suppress("Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveVisible<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var visible: Boolean
}