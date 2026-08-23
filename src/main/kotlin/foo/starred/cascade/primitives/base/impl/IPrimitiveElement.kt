@file:Suppress("Unused", "Unchecked_cast", "PropertyName")

package foo.starred.cascade.primitives.base.impl

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.data.AnimatableColor
import foo.starred.cascade.animation.data.AnimatableFloat
import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.events.base.UIEvent
import foo.starred.cascade.events.impl.FocusEvent
import foo.starred.cascade.primitives.base.interfaces.*
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f
import java.util.concurrent.CopyOnWriteArrayList

abstract class IPrimitiveElement<T : IPrimitiveElement<T>> : IPrimitiveAnimatable<T>, IPrimitiveChildren<T>, IPrimitiveConstrainable<T>, IPrimitiveEffects<T>, IPrimitiveEvents<T>, IPrimitiveFindable<T>, IPrimitiveInteractable<T>, IPrimitiveLayoutResolver<T>, IPrimitiveVisible<T> {
    internal var _root: IPrimitiveElement<*>? = null

    abstract var x: Float
    abstract var y: Float
    abstract var width: Float
    abstract var height: Float
    abstract var color: Int

    override val effects: CopyOnWriteArrayList<IEffect> = CopyOnWriteArrayList()
    override val children: CopyOnWriteArrayList<IPrimitiveElement<*>> = CopyOnWriteArrayList()
    override val listeners: MutableMap<Class<out UIEvent>, MutableList<UIEvent.() -> Unit>> = mutableMapOf()

    override val root: IPrimitiveElement<*>
        get() {
            val r = _root
            if (r != null && r.parent == null) return r
            return generateSequence(this as IPrimitiveElement<*>) { it.parent }.last().also { _root = it }
        }

    override val self: T
        get() = this as T

    override var parent: IPrimitiveElement<*>? = null
        set(value) {
            field = value
            _root = null
            root.dirty()
        }

    override var size: ISizeConstraint? = null
        set(value) {
            field = value
            root.dirty()
        }

    override var position: IPositionConstraint? = null
        set(value) {
            field = value
            root.dirty()
        }

    override var visible: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            root.dirty()
        }

    override var focused: IPrimitiveElement<*>? = null
        set(value) {
            if (field == value) return
            val field0 = field
            field = value

            field0?.post(FocusEvent.Lose(field0))
            value?.post(FocusEvent.Gain(value))
        }

    override var dirty: Boolean = false
    override var interact: Boolean = true
    override var hovered: Boolean = false
    override var unfocus: Boolean = true

    override var animations: Animation? = null
    override var `animation$float`: AnimatableFloat? = null
    override var `animation$color`: AnimatableColor? = null

    open fun draw(graphics: GuiGraphicsExtractor) {}

    open fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) {
            return
        }

        if (effects.isEmpty()) {
            draw(graphics)

            for (c in children) {
                c.render(graphics)
            }

            return
        }

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        for (e in effects) {
            e.before(self, graphics, pose, scissor)
        }

        draw(graphics)

        for (e in effects) {
            e.after(self, graphics, pose, scissor)
        }

        for (c in children) {
            c.render(graphics)
        }
    }

    inline fun <reified E : UIEvent> on(noinline listener: E.() -> Unit): T {
        return on(E::class.java, listener)
    }
}
