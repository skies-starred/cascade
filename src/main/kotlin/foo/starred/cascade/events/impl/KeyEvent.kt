@file:Suppress("Unused")

package foo.starred.cascade.events.impl

import foo.starred.cascade.events.base.UIEvent

sealed class KeyEvent {
    data class Press(
        val key: Int
    ) : UIEvent()

    data class Type(
        val char: Char
    ) : UIEvent()

    data class Release(
        val key: Int
    ) : UIEvent()
}