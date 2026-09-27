package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class CenterPositionConstraint : IPositionConstraint {
    override fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.x + (parent.width - element.width) / 2f
    }

    override fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.y + (parent.height - element.height) / 2f
    }
}
