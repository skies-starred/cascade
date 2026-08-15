package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.events.impl.KeyEvent
import foo.starred.cascade.events.impl.MouseEvent
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveInteractable<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var interact: Boolean
    var hovered: Boolean

    var focused: IPrimitiveElement<*>?
    var unfocus: Boolean

    fun mousePress(x: Double, y: Double, button: Int): Boolean {
        val a = self.root.focused
        if (a?.unfocus == false) return a.post(MouseEvent.Press(x, y, button, a))

        val b = self.find(x, y).also { self.root.focused = it } ?: return false
        return b.post(MouseEvent.Press(x, y, button, b))
    }

    fun mouseRelease(x: Double, y: Double, button: Int): Boolean {
        val a = self.find(x, y)

        val b = self.root.focused
        if (b?.unfocus == false) return b.post(MouseEvent.Release(x, y, button, b))
        if (b != a) b?.post(MouseEvent.Release(x, y, button, b))

        return a?.post(MouseEvent.Release(x, y, button, a)) ?: false
    }

    fun mouseScroll(x: Double, y: Double, amount: Double): Boolean {
        val a = self.find(x, y) ?: return false
        val b = a.post(MouseEvent.Scroll(x, y, amount, a))
        if (b) return true

        var c = a.parent
        while (c != null) {
            if (c is IPrimitiveScrollable) return c.post(MouseEvent.Scroll(x, y, amount, c))
            c = c.parent
        }

        return false
    }

    fun mouseMove(x: Double, y: Double) {
        val a = self.find(x, y)

        self.forEach {
            if (!it.hovered) return@forEach
            if (it == a) return@forEach

            it.hovered = false
            it.post(MouseEvent.Move.Exit(x, y, it))
        }

        val b = a ?: return
        if (!b.hovered) {
            b.hovered = true
            b.post(MouseEvent.Move.Enter(x, y, b))
        }

        b.post(MouseEvent.Move.Any(x, y, b))
    }

    fun keyPress(key: Int): Boolean {
        return focused?.post(KeyEvent.Press(key)) ?: false
    }

    fun keyRelease(key: Int): Boolean {
        return focused?.post(KeyEvent.Release(key)) ?: false
    }

    fun keyType(char: Char): Boolean {
        return focused?.post(KeyEvent.Type(char)) ?: false
    }
}