package foo.starred.cascade.animation.interpolator.property.impl

import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import net.minecraft.util.ARGB

object ArgbPropertyInterpolator : IPropertyInterpolator<Int> {
    override fun get(from: Int, to: Int, progress: Float): Int {
        return ARGB.srgbLerp(progress, from, to)
    }
}
