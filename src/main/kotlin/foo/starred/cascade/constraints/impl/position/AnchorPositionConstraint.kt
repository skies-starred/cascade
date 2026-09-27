package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.impl.data.PositionAnchor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AnchorPositionConstraint(val fn: () -> IPrimitiveElement<*>, var anchor: PositionAnchor) : IPositionConstraint {
    override fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val t = fn()
        return when (anchor) {
            PositionAnchor.LEFT -> t.x - element.width
            PositionAnchor.RIGHT -> t.x + t.width
            PositionAnchor.ABOVE, PositionAnchor.BELOW -> t.x
        }
    }

    override fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val t = fn()
        return when (anchor) {
            PositionAnchor.ABOVE -> t.y - element.height
            PositionAnchor.BELOW -> t.y + t.height
            PositionAnchor.LEFT, PositionAnchor.RIGHT -> t.y
        }
    }
}
