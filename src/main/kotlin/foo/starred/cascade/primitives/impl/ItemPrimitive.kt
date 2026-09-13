package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.world.item.ItemStack

open class ItemPrimitive : IPrimitiveElement<ItemPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 16f
    override var height: Float = 16f
    override var color: CascadeGeometricColor = CascadeGeometricColor.WHITE

    override var interact: Boolean = false

    var item: ItemStack = ItemStack.EMPTY

    override fun draw(graphics: GuiGraphicsExtractor) {
        if (item.isEmpty) return

        graphics.item(item, x.toInt(), y.toInt())
    }

    companion object {
        val NONE = ItemPrimitive()

        inline fun item(block: ItemPrimitive.() -> Unit): ItemPrimitive {
            return ItemPrimitive().apply(block)
        }
    }
}
