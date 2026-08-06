package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

open class ContainerPrimitive : IPrimitiveElement<ContainerPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    companion object {
        val NONE = ContainerPrimitive()

        inline fun container(block: ContainerPrimitive.() -> Unit): ContainerPrimitive {
            return ContainerPrimitive().apply(block)
        }
    }
}