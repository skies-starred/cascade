package foo.starred.cascade.graphics.states.base

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import org.joml.Matrix3x2fc
import kotlin.math.min

interface IRoundedGuiElementRenderState {
    fun CascadeGeometricRadius.radii(block: (tl: Int, tr: Int, br: Int, bl: Int) -> Unit) {
        val tl = (min(tl, 3276.7f) * 10f).toInt()
        val tr = (min(tr, 3276.7f) * 10f).toInt()
        val br = (min(br, 3276.7f) * 10f).toInt()
        val bl = (min(bl, 3276.7f) * 10f).toInt()

        block(tl, tr, br, bl)
    }

    fun VertexConsumer.quad( pose: Matrix3x2fc, x0: Float, y0: Float, x1: Float, y1: Float, color: CascadeGeometricColor, radius: CascadeGeometricRadius, block: VertexConsumer.(u: Float, v: Float) -> Unit = { _, _ -> }) {
        val w0 = (x1 - x0) * 0.5f
        val h0 = (y1 - y0) * 0.5f

        radius.radii { tl, tr, br, bl ->
            fun vertex(x: Float, y: Float, u: Float, v: Float, color: Int) {
                addVertexWith2DPose(pose, x, y).setColor(color).setUv(u, v).setUv1(tl, tr).setUv2(br, bl).block(u, v)
            }

            vertex(x0, y0, -w0, -h0, color.tl)
            vertex(x0, y1, -w0, h0, color.bl)
            vertex(x1, y1, w0, h0, color.br)
            vertex(x1, y0, w0, -h0, color.tr)
        }
    }
}
