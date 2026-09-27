package foo.starred.cascade.animation.interpolator.property.impl

import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import net.minecraft.util.ARGB

object ColorPropertyInterpolator : IPropertyInterpolator<CascadeGeometricColor> {
    override fun get(from: CascadeGeometricColor, to: CascadeGeometricColor, progress: Float): CascadeGeometricColor {
        return CascadeGeometricColor(ARGB.srgbLerp(progress, from.tl, to.tl), ARGB.srgbLerp(progress, from.tr, to.tr), ARGB.srgbLerp(progress, from.bl, to.bl), ARGB.srgbLerp(progress, from.br, to.br))
    }
}
