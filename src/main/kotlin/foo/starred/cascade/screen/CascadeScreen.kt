package foo.starred.cascade.screen

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.primitives.impl.ContainerPrimitive
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

open class CascadeScreen(title: String = "Cascade Screen [Athen]") : Screen(Component.literal(title)) {
    val scene = ContainerPrimitive().apply {
        width = this@CascadeScreen.width.toFloat()
        height = this@CascadeScreen.height.toFloat()
    }

    override fun init() {
        scene.width = width.toFloat()
        scene.height = height.toFloat()
        scene.layout()
    }

    //? if >= 26.1 {
    final override fun extractRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        scene.render(graphics)
        super.extractRenderState(graphics, mouseX, mouseY, delta)
    }
    //?} else {
    /*final override fun render(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        scene.render(graphics)
        super.render(graphics, mouseX, mouseY, partialTick)
    }
    *///?}

    final override fun mouseClicked(event: MouseButtonEvent, isDoubleClick: Boolean): Boolean {
        return scene.mousePress(event.x(), event.y(), event.button()) || super.mouseClicked(event, isDoubleClick)
    }

    final override fun mouseReleased(event: MouseButtonEvent): Boolean {
        return scene.mouseRelease(event.x(), event.y(), event.button())
    }

    final override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        return scene.mouseScroll(mouseX, mouseY, scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    final override fun mouseMoved(mouseX: Double, mouseY: Double) {
        scene.mouseMove(mouseX, mouseY)
        super.mouseMoved(mouseX, mouseY)
    }

    final override fun keyPressed(event: KeyEvent): Boolean {
        return scene.keyPress(event.key()) || super.keyPressed(event)
    }

    final override fun keyReleased(event: KeyEvent): Boolean {
        return scene.keyRelease(event.key()) || super.keyReleased(event)
    }

    final override fun charTyped(event: CharacterEvent): Boolean {
        return scene.keyType(event.codepoint().toChar()) || super.charTyped(event)
    }

    fun open() {
        client.schedule {
            //~ if >= 26.2 'client.setScreen' -> 'client.gui.setScreen'
            client.setScreen(this@CascadeScreen)
        }
    }
}