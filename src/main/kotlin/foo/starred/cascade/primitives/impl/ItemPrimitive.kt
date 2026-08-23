package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.world.item.ItemStack

open class ItemPrimitive : IPrimitiveElement<ItemPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 16f
    override var height: Float = 16f
    override var color: Int = -1

    override var interact: Boolean = false

    var item: ItemStack = ItemStack.EMPTY

    override fun draw(graphics: GuiGraphicsExtractor) {
        if (item.isEmpty) return

        //~ if >= 26.1 'renderItem(' -> 'item('
        graphics.item(item, x.toInt(), y.toInt())
    }

    companion object {
        val NONE = ItemPrimitive()

        inline fun item(block: ItemPrimitive.() -> Unit): ItemPrimitive {
            return ItemPrimitive().apply(block)
        }
    }
}