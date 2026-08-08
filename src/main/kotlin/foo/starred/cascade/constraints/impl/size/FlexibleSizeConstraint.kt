package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FlexibleSizeConstraint(padding: Number = 0) : ISizeConstraint {
    val padding: Float = padding.toFloat()

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return fn(element, { it.x - element.x }, { it.width })
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return fn(element, { it.y - element.y }, { it.height })
    }

    private inline fun fn(element: IPrimitiveElement<*>, offset: (IPrimitiveElement<*>) -> Number, size: (IPrimitiveElement<*>) -> Number): Float {
        var a = 0f

        for (child in element.children) {
            if (!child.visible) continue
            child.constrain(element)

            val b = offset(child).toFloat()
            val c = size(child).toFloat()
            val d = b + c
            if (d > a) a = d
        }

        return a + padding
    }
}