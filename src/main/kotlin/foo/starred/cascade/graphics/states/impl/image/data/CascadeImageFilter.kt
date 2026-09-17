package foo.starred.cascade.graphics.states.impl.image.data

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuSampler

enum class CascadeImageFilter(val mode: FilterMode) {
    LINEAR(FilterMode.LINEAR),
    NEAREST(FilterMode.NEAREST);

    fun sampler(): GpuSampler {
        return RenderSystem.getSamplerCache().getClampToEdge(mode)
    }
}
