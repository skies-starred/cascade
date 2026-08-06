@file:Suppress("Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveConstrainable<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var position: IPositionConstraint?
    var size: ISizeConstraint?

    fun constrain(parent: IPrimitiveElement<*>) {
        size?.let {
            self.width = it.width(self, parent)
            self.height = it.height(self, parent)
        }

        position?.let {
            self.x = it.x(self, parent)
            self.y = it.y(self, parent)
        }
    }
}