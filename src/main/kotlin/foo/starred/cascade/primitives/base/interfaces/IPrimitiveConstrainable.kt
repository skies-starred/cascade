@file:Suppress("Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveConstrainable<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var position: IPositionConstraint?
    var size: ISizeConstraint?
    var offset: CascadeGeometricOffset

    fun constrain(parent: IPrimitiveElement<*>) {
        size?.let {
            self.width = it._width(self, parent)
            self.height = it._height(self, parent)
        }

        position?.let {
            self.x = it._x(self, parent) + offset.x
            self.y = it._y(self, parent) + offset.y
        }
    }
}
