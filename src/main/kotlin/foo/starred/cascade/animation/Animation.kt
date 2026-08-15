@file:Suppress("Unused")

package foo.starred.cascade.animation

import foo.starred.cascade.animation.base.IAnimatable
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import java.util.concurrent.CopyOnWriteArrayList

class Animation(val scene: IPrimitiveElement<*>) {
    private val active = CopyOnWriteArrayList<IAnimatable>()
    private var last = System.nanoTime()

    val bool: Boolean
        get() = active.isNotEmpty()

    fun animate() {
        val b = bool
        tick()

        if (!b && !bool) return
        scene.layout()
    }

    fun track(anim: IAnimatable) {
        if (active.contains(anim)) return
        active.add(anim)
    }

    fun tick() {
        val now = System.nanoTime()
        val delta = ((now - last) / 1_000_000_000f).coerceIn(0f, 0.15f)
        last = now

        if (active.isEmpty()) {
            return
        }

        for (a in active) {
            if (a.advance(delta)) continue
            active.remove(a)
            a.function?.invoke()
        }
    }
}