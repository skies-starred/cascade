@file:Suppress("Unused")

package foo.starred.cascade.events.base

abstract class UIEvent {
    @Volatile
    var cancelled = false
        private set

    fun cancel() {
        cancelled = true
    }
}