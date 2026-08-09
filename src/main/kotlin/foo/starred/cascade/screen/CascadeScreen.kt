package foo.starred.cascade.screen

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.animation.Animation
import foo.starred.cascade.primitives.impl.ContainerPrimitive
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

open class CascadeScreen(title: String = "Cascade Screen [Athen]") : Screen(Component.literal(title)) {
    private var bool = false

    val scene = object : ContainerPrimitive() {
        override fun layout() {
            if (!bool) return
            super.layout()
        }
    }.apply {
        width = this@CascadeScreen.width.toFloat()
        height = this@CascadeScreen.height.toFloat()
        animations = Animation(this)
    }

    override fun init() {
        bool = true
        scene.width = width.toFloat()
        scene.height = height.toFloat()
        scene.layout()
    }

    //~ if >= 26.1 'render(' -> 'extractRenderState('
    final override fun extractRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        scene.animations?.animate()
        scene.render(graphics)
        //~ if >= 26.1 'render(' -> 'extractRenderState('
        super.extractRenderState(graphics, mouseX, mouseY, delta)
    }

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