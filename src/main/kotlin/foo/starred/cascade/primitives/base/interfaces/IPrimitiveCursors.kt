package foo.starred.cascade.primitives.base.interfaces

import com.mojang.blaze3d.platform.cursor.CursorType
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveCursors<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var cursor: CursorType?

    fun cursor(): CursorType? {
        val root = self.root
        var current: IPrimitiveElement<*>? = self

        while (current != null) {
            if (current === root) return null
            if (current.cursor != null) return current.cursor

            current = current.parent
        }

        return null
    }
}
