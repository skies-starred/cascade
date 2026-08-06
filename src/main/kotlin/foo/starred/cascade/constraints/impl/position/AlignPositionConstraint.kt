package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.impl.data.PositionAlignment
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AlignPositionConstraint(val horizontal: PositionAlignment = PositionAlignment.START, val vertical: PositionAlignment = PositionAlignment.START, x: Number = 0, y: Number = 0) : IPositionConstraint {
    val x: Float = x.toFloat()
    val y: Float = y.toFloat()

    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val base = when (horizontal) {
            PositionAlignment.START -> parent.x
            PositionAlignment.CENTER -> parent.x + (parent.width - element.width) / 2f
            PositionAlignment.END -> parent.x + parent.width - element.width
        }

        return base + x
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        val base = when (vertical) {
            PositionAlignment.START -> parent.y
            PositionAlignment.CENTER -> parent.y + (parent.height - element.height) / 2f
            PositionAlignment.END -> parent.y + parent.height - element.height
        }

        return base + y
    }
}