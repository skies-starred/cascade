package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class CenterPositionConstraint(x: Number = 0f, y: Number = 0f) : IPositionConstraint {
    val x = x.toFloat()
    val y = y.toFloat()

    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.x + (parent.width - element.width) / 2f) + x
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.y + (parent.height - element.height) / 2f) + y
    }
}