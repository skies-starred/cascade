package foo.starred.cascade.primitives.impl

import foo.starred.cascade.Cascade.client
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Pose
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.atan

open class EntityPrimitive : IPrimitiveElement<EntityPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 50f
    override var height: Float = 80f
    override var color: Int = -1

    var entity: LivingEntity? = null
    var scale: Float = 30f
    var factor: Float = 0.529f
    var cursor: Boolean = true

    override fun constrain(parent: IPrimitiveElement<*>) {
        super.constrain(parent)
        if (size == null) return

        scale = height * factor
    }

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return
        val entity = entity ?: return

        val mouseX = client.mouseHandler.getScaledXPos(client.window).toFloat()
        val mouseY = client.mouseHandler.getScaledYPos(client.window).toFloat()

        val x0 = x.toInt()
        val y0 = y.toInt()
        val x1 = (x + width).toInt()
        val y1 = (y + height).toInt()

        val x2 = (x0 + x1) / 2.0f
        val y2 = (y0 + y1) / 2.0f

        val angle0 = if (cursor) atan((x2 - mouseX) / 40.0f) else 0f
        val angle1 = if (cursor) atan((y2 - mouseY) / 40.0f) else 0f

        val rotationZ = Quaternionf().rotateZ(Math.PI.toFloat())
        val rotationX = Quaternionf().rotateX(angle1 * 20.0f * (Math.PI.toFloat() / 180.0f))

        rotationZ.mul(rotationX)
        val state = entity.extract()

        if (state is LivingEntityRenderState) {
            state.bodyRot = 180.0f + angle0 * 20.0f
            state.yRot = angle0 * 20.0f
            state.xRot = if (state.pose == Pose.FALL_FLYING) 0f else -angle1 * 20f

            state.boundingBoxWidth /= state.scale
            state.boundingBoxHeight /= state.scale
            state.scale = 1.0f
        }

        val translation = Vector3f(0.0f, state.boundingBoxHeight / 2.0f + 0.0625f, 0.0f)

        //~ if >= 26.1 'submitEntityRenderState' -> 'entity'
        graphics.entity(state, scale, translation, rotationZ, rotationX, x0, y0, x1, y1)
        super.render(graphics)
    }

    private fun LivingEntity.extract(): EntityRenderState {
        val state = client.entityRenderDispatcher.getRenderer(this).createRenderState(this, 1.0f)
        state.shadowPieces.clear()
        state.outlineColor = 0
        state.lightCoords = 0xF000F0
        return state
    }

    companion object {
        val NONE = EntityPrimitive()

        inline fun entity(block: EntityPrimitive.() -> Unit): EntityPrimitive {
            return EntityPrimitive().apply(block)
        }
    }
}