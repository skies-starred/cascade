package foo.starred.cascade.screen

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.animation.Animation
import foo.starred.cascade.graphics.geometry.CascadeGeometricResolution
import foo.starred.cascade.primitives.impl.ContainerPrimitive
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

open class CascadeScreen(title: String = "Cascade Screen [Athen]", var resolution: CascadeGeometricResolution = CascadeGeometricResolution.MINECRAFT) : Screen(Component.literal(title)) {
    val scene = ContainerPrimitive().apply {
        width = this@CascadeScreen.width.toFloat()
        height = this@CascadeScreen.height.toFloat()
        animations = Animation(this)
    }

    override fun init() {
        CascadeGeometricResolution.compute(resolution, width, height) { scale1, width1, height1 ->
            scene.scale = scale1
            scene.width = width1
            scene.height = height1
        }

        scene.layout()
    }

    //~ if >= 26.1 'render(' -> 'extractRenderState('
    final override fun extractRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, delta: Float) {
        scene.animations?.animate()
        if (scene.dirty) scene.layout()

        val bool = scene.scale != 1f

        if (bool) {
            graphics.pose().pushMatrix()
            graphics.pose().scale(scene.scale, scene.scale)
        }

        scene.render(graphics)

        if (bool) {
            graphics.pose().popMatrix()
        }

        //~ if >= 26.1 'render(' -> 'extractRenderState('
        super.extractRenderState(graphics, mouseX, mouseY, delta)
    }

    final override fun mouseClicked(event: MouseButtonEvent, isDoubleClick: Boolean): Boolean {
        val (mouseX, mouseY) = mouse(event.x(), event.y())
        return scene.mousePress(mouseX, mouseY, event.button()) || super.mouseClicked(event, isDoubleClick)
    }

    final override fun mouseReleased(event: MouseButtonEvent): Boolean {
        val (mouseX, mouseY) = mouse(event.x(), event.y())
        return scene.mouseRelease(mouseX, mouseY, event.button())
    }

    final override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        val (mouseX, mouseY) = mouse(mouseX, mouseY)
        return scene.mouseScroll(mouseX, mouseY, scrollY) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    final override fun mouseMoved(mouseX: Double, mouseY: Double) {
        val (mouseX, mouseY) = mouse(mouseX, mouseY)
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

    private fun mouse(x: Double, y: Double): Pair<Double, Double> {
        val x = x / scene.scale
        val y = y / scene.scale
        scene.mouseX = x.toFloat()
        scene.mouseY = y.toFloat()
        return x to y
    }
}
