package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.max

open class OverlayPrimitive : IPrimitiveElement<OverlayPrimitive>() {
    private var x0: Float = -1f
    private var y0: Float = -1f

    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: CascadeGeometricColor = CascadeGeometricColor.TRANSPARENT
    override var interact: Boolean = false

    override var parent: IPrimitiveElement<*>? = null
        set(value) {
            if (field === value) return

            field?.root?.overlays?.remove(this)
            field = value
            _root = null
            value?.root?.overlays?.addIfAbsent(this)
            root.dirty()
        }

    var follow: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            root.dirty()
        }

    var clamp: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            root.dirty()
        }

    override fun layout() {
        if (follow) {
            x = mouseX + offset.x
            y = mouseY + offset.y
        }

        if (clamp && root.width > 0f && root.height > 0f) {
            x = x.coerceIn(0f, max(0f, root.width - width))
            y = y.coerceIn(0f, max(0f, root.height - height))
        }

        super.layout()

        if (!root.overlays.contains(this)) {
            root.overlays.add(this)
        }

        if (size != null) return
        val visible = children.filter { it.visible }

        if (width == 0f) {
            width = visible.maxOfOrNull { (it.x - x) + it.width } ?: 0f
        }

        if (height == 0f) {
            height = visible.maxOfOrNull { (it.y - y) + it.height } ?: 0f
        }
    }

    override fun render(graphics: GuiGraphicsExtractor) {
    }

    override fun overlay(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val x1 = mouseX
        val y1 = mouseY
        if (x1 != x0 || y1 != y0) {
            x0 = x1
            y0 = y1
            layout()
        }

        super.render(graphics)
    }

    companion object {
        val NONE = OverlayPrimitive()

        inline fun overlay(block: OverlayPrimitive.() -> Unit): OverlayPrimitive {
            return OverlayPrimitive().apply(block)
        }
    }
}
