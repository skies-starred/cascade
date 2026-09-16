package foo.starred.cascade.graphics.blur.data

import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.CommandEncoder
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.GpuSampler
import com.mojang.blaze3d.textures.GpuTextureView
import net.minecraft.client.renderer.RenderPipelines
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.sampler.impl.CascadeSamplers

//? if >= 26.2 {
/*import java.util.Optional
*///? } else {
import java.util.OptionalInt
//? }

object CascadeBlurPipeline {
    val DOWN: RenderPipeline = kawase("kawase_down")
    val UP: RenderPipeline = kawase("kawase_up")

    fun pass(encoder: CommandEncoder, label: String, target: TextureTarget, pipeline: RenderPipeline, source: GpuTextureView, sampler: GpuSampler) {
        //~ if >= 26.2 'OptionalInt.empty()' -> 'Optional.empty()'
        val pass = encoder.createRenderPass({ label }, target.colorTextureView!!, OptionalInt.empty())
        //~ if >= 26.3 'pass.setPipeline(pipeline)' -> 'pass.setPipeline(RenderSystem.getCompiledPipeline(pipeline))'
        pass.setPipeline(pipeline)
        //~ if >= 26.3 'bindTexture' -> 'setUniform'
        pass.bindTexture("InSampler", source, sampler)
        //~ if >= 26.2 'pass.draw(0, 3)' -> 'pass.draw(3, 1, 0, 0)'
        pass.draw(0, 3)
        pass.close()
    }

    private fun kawase(id: String): RenderPipeline {
        return cascadeRenderPipeline(id, snippet = RenderPipelines.POST_PROCESSING_SNIPPET) {
            data.vertexShader = "minecraft:core/screenquad"
            data.fragmentShader = "core/effects/blur/kawase/$id"
            data.colorTargetState = ColorTargetState.DEFAULT

            data.sampler(CascadeSamplers.IN_SAMPLER)
        }
    }
}
