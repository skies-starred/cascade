package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.impl.data.PositionAlignment
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AlignPositionConstraint(var horizontal: PositionAlignment = PositionAlignment.START, var vertical: PositionAlignment = PositionAlignment.START) : IPositionConstraint {
    override fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return when (horizontal) {
            PositionAlignment.START -> parent.x
            PositionAlignment.CENTER -> parent.x + (parent.width - element.width) / 2f
            PositionAlignment.END -> parent.x + parent.width - element.width
        }
    }

    override fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return when (vertical) {
            PositionAlignment.START -> parent.y
            PositionAlignment.CENTER -> parent.y + (parent.height - element.height) / 2f
            PositionAlignment.END -> parent.y + parent.height - element.height
        }
    }
}
