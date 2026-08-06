package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.impl.data.PositionAnchor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AnchorPositionConstraint(val fn: () -> IPrimitiveElement<*>, val anchor: PositionAnchor, x: Number = 0, y: Number = 0) : IPositionConstraint {
    val x: Float = x.toFloat()
    val y: Float = y.toFloat()

    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val t = fn()
        return when (anchor) {
            PositionAnchor.LEFT -> t.x - element.width + x
            PositionAnchor.RIGHT -> t.x + t.width + x
            PositionAnchor.ABOVE, PositionAnchor.BELOW -> t.x + x
        }
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val t = fn()
        return when (anchor) {
            PositionAnchor.ABOVE -> t.y - element.height + y
            PositionAnchor.BELOW -> t.y + t.height + y
            PositionAnchor.LEFT, PositionAnchor.RIGHT -> t.y + y
        }
    }
}