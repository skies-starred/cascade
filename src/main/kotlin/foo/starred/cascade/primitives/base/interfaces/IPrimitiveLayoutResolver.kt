@file:Suppress("Unchecked_Cast")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveLayoutResolver<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var dirty: Boolean

    fun layout() {
        dirty = true
    }

    fun layout0() {
        if (!dirty) return
        val self = self

        for (child in self.children) {
            if (!child.visible) continue
            child.constrain(self)
            child.layout0()
        }
    }
}