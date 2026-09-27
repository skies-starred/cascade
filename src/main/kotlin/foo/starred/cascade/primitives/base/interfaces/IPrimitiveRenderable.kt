@file:Suppress("Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f

interface IPrimitiveRenderable<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    var visible: Boolean

    fun draw(graphics: GuiGraphicsExtractor) {
    }

    fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) {
            return
        }

        if (self.effects.isEmpty()) {
            draw(graphics)

            for (c in self.children) {
                c.render(graphics)
            }

            return
        }

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        for (e in self.effects) {
            e.before(self, graphics, pose, scissor)
        }

        draw(graphics)

        for (e in self.effects) {
            e.after(self, graphics, pose, scissor)
        }

        for (c in self.children) {
            c.render(graphics)
        }
    }
}
