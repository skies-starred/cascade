package foo.starred.cascade.graphics.states.pipeline.sampler.builder

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.graphics.states.pipeline.sampler.impl.CascadeSamplers

//? if >= 26.3
//import com.mojang.blaze3d.pipeline.UniformType

//? if >= 26.2
//import com.mojang.blaze3d.pipeline.BindGroupLayout

class CascadeSamplerBuilder {
    private val samplers = mutableListOf<String>()

    operator fun invoke(vararg names: String): CascadeSamplerBuilder {
        samplers += names
        return this
    }

    operator fun invoke(vararg samplers: CascadeSamplers): CascadeSamplerBuilder {
        for (sampler in samplers) this.samplers += sampler.id
        return this
    }

    fun build(builder: RenderPipeline.Builder) {
        if (samplers.isEmpty()) return

        //? if >= 26.3 {
        /*val layout = BindGroupLayout.builder()
        for (sampler in samplers) layout.withUniform(sampler, UniformType.COMBINED_IMAGE_SAMPLER)
        builder.withBindGroupLayout(layout.build())
        *///?} elif 26.2 {
        /*val layout = BindGroupLayout.builder()
        for (sampler in samplers) layout.withSampler(sampler)
        builder.withBindGroupLayout(layout.build())
        *///? } else {
        for (sampler in samplers) {
            builder.withSampler(sampler)
        }
        //?}
    }
}
