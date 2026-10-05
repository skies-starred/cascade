@file:Suppress("PropertyName", "LocalVariableName")

package foo.starred.cascade.graphics.states.base

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.states.batch.impl.CascadeBatcher
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

abstract class CascadeGuiElementRenderState(
    val _pipeline: RenderPipeline,
    val _scissor: ScreenRectangle? = null,
    val _bounds: ScreenRectangle? = null
) : GuiElementRenderState {
    override fun pipeline(): RenderPipeline {
        return _pipeline
    }

    override fun textureSetup(): TextureSetup {
        return TextureSetup.noTexture()
    }

    override fun scissorArea(): ScreenRectangle? {
        return _scissor
    }

    override fun bounds(): ScreenRectangle? {
        return _bounds
    }

    fun submit(graphics: GuiGraphicsExtractor) {
        CascadeBatcher.submit(graphics, this)
    }

    companion object {
        fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle? {
            val m00 = pose.m00()
            val m01 = pose.m01()
            val m10 = pose.m10()
            val m11 = pose.m11()
            val m20 = pose.m20()
            val m21 = pose.m21()

            val _x00: Float
            val _x01: Float
            val _y00: Float
            val _y01: Float

            if (m01 == 0f && m10 == 0f) {
                val _x0 = x0 * m00 + m20
                val _x1 = x1 * m00 + m20
                val _y0 = y0 * m11 + m21
                val _y1 = y1 * m11 + m21

                _x00 = min(_x0, _x1)
                _x01 = max(_x0, _x1)
                _y00 = min(_y0, _y1)
                _y01 = max(_y0, _y1)
            } else {
                val _x0 = x0 * m00 + y0 * m10 + m20
                val _y0 = x0 * m01 + y0 * m11 + m21
                val _x1 = x1 * m00 + y0 * m10 + m20
                val _y1 = x1 * m01 + y0 * m11 + m21
                val _x2 = x0 * m00 + y1 * m10 + m20
                val _y2 = x0 * m01 + y1 * m11 + m21
                val _x3 = x1 * m00 + y1 * m10 + m20
                val _y3 = x1 * m01 + y1 * m11 + m21

                _x00 = min(min(_x0, _x1), min(_x2, _x3))
                _x01 = max(max(_x0, _x1), max(_x2, _x3))
                _y00 = min(min(_y0, _y1), min(_y2, _y3))
                _y01 = max(max(_y0, _y1), max(_y2, _y3))
            }

            var x2 = floor(_x00).toInt()
            var y2 = floor(_y00).toInt()
            var x3 = ceil(_x01).toInt()
            var y3 = ceil(_y01).toInt()

            if (scissor != null) {
                x2 = max(x2, scissor.left())
                y2 = max(y2, scissor.top())
                x3 = min(x3, scissor.right())
                y3 = min(y3, scissor.bottom())

                if (x2 >= x3 || y2 >= y3) return null
            }

            return ScreenRectangle(x2, y2, x3 - x2, y3 - y2)
        }
    }
}
