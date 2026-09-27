@file:Suppress("Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import java.util.concurrent.CopyOnWriteArrayList

interface IPrimitiveChildren<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    val children: CopyOnWriteArrayList<IPrimitiveElement<*>>
    val root: IPrimitiveElement<*>
    var parent: IPrimitiveElement<*>?

    operator fun contains(child: IPrimitiveElement<*>): Boolean {
        return child in children
    }

    fun children(graphics: GuiGraphicsExtractor) {
        for (c in children) c.render(graphics)
    }

    fun adopt(a: IPrimitiveElement<*>): T {
        a.parent?.children?.remove(a)

        a.parent = self
        children.add(a)

        root.dirty()
        return self
    }

    fun attach(a: IPrimitiveElement<*>): T {
        a.adopt(self)
        return self
    }

    fun disown(a: IPrimitiveElement<*>): T {
        if (a.parent !== self) return self
        if (root.focused != null) a.iterateChildren { if (root.focused === it) root.focused = null }

        children.remove(a)
        a.parent = null
        root.dirty()
        return self
    }

    fun detach(): T {
        parent?.disown(self)
        return self
    }

    @Deprecated("Use iterateChildren", ReplaceWith("iterateChildren(reversed, block)"))
    fun forEach(reversed: Boolean = false, block: (IPrimitiveElement<*>) -> Unit) {
        iterateChildren(reversed, block)
    }

    fun iterateChildren(reversed: Boolean = false, block: (IPrimitiveElement<*>) -> Unit) {
        block(self)

        val a = if (reversed) children.asReversed() else children
        for (b in a) b.iterateChildren(reversed, block)
    }
}
