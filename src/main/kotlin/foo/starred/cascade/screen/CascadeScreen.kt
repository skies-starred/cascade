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

open class CascadeScreen(title: String = "Cascade Screen", private val guiScale: Boolean = false) : Screen(Component.literal(title)) {
    val scene = ContainerPrimitive().apply {
        width = sceneWidth()
        height = sceneHeight()
        animations = Animation(this)
    }

    private fun sceneWidth() = if (guiScale) this@CascadeScreen.width.toFloat() else Resolution.width
    private fun sceneHeight() = if (guiScale) this@CascadeScreen.height.toFloat() else Resolution.height
    private fun mouseX(vanillaX: Double) = if (guiScale) vanillaX else vanillaX / Resolution.scale
    private fun mouseY(vanillaY: Double) = if (guiScale) vanillaY else vanillaY / Resolution.scale

    override fun init() {
        if (!guiScale) Resolution.refresh()
        scene.width = sceneWidth()
        scene.height = sceneHeight()
        scene.layout()
    }

    //~ if >= 26.1 'render(' -> 'extractRenderState('
    final override fun extractRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        scene.animations?.animate()
        if (scene.dirty) scene.layout()

        if (!guiScale) Resolution.push(graphics)
        scene.render(graphics)
        if (!guiScale) Resolution.pop(graphics)
        //~ if >= 26.1 'render(' -> 'extractRenderState('
        super.extractRenderState(graphics, mouseX, mouseY, delta)
    }

    final override fun mouseClicked(event: MouseButtonEvent, isDoubleClick: Boolean): Boolean {
        return scene.mousePress(mouseX(event.x()), mouseY(event.y()), event.button()) || super.mouseClicked(event, isDoubleClick)
    }

    final override fun mouseReleased(event: MouseButtonEvent): Boolean {
        return scene.mouseRelease(mouseX(event.x()), mouseY(event.y()), event.button())
    }

    final override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        return scene.mouseScroll(mouseX(mouseX), mouseY(mouseY), scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    final override fun mouseMoved(mouseX: Double, mouseY: Double) {
        scene.mouseMove(mouseX(mouseX), mouseY(mouseY))
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