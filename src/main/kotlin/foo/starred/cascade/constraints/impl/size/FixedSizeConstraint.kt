package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FixedSizeConstraint(width: Number, height: Number) : ISizeConstraint {
    var width: Float = width.toFloat()
    var height: Float = height.toFloat()

    override fun _width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return width
    }

    override fun _height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return height
    }
}
