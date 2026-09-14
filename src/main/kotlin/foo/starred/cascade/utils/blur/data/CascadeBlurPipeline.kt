package foo.starred.cascade.utils.blur.data

//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.pipeline.ColorTargetState
//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.TextureTarget
//~ if >= 26.3 'blaze3d.systems' -> 'renderpearl.api.commands'
import com.mojang.blaze3d.systems.CommandEncoder
import com.mojang.blaze3d.systems.RenderSystem
//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.textures.GpuSampler
//~ if >= 26.3 'blaze3d' -> 'renderpearl.api'
import com.mojang.blaze3d.textures.GpuTextureView
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.Identifier

//? if >= 26.2 {
/*import java.util.Optional
*///? } else {
import java.util.OptionalInt
//? }

//? if >= 26.3 {
/*import com.mojang.renderpearl.api.pipeline.BindGroupLayout
import com.mojang.renderpearl.api.pipeline.UniformType
*///?} elif 26.2 {
/*import com.mojang.blaze3d.pipeline.BindGroupLayout
*///?}

object CascadeBlurPipeline {
    val DOWN: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.POST_PROCESSING_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cascade", "kawase_down"))
            .withVertexShader(Identifier.fromNamespaceAndPath("minecraft", "core/screenquad"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/effects/blur/kawase/kawase_down"))
            .withColorTargetState(ColorTargetState.DEFAULT)
            //? if >= 26.3 {
            /*.withBindGroupLayout(BindGroupLayout.builder()
                .withUniform("InSampler", UniformType.COMBINED_IMAGE_SAMPLER)
                .build())
            *///?} elif 26.2 {
            /*.withBindGroupLayout(BindGroupLayout.builder()
                .withSampler("InSampler")
                .build())
            *///? } else {
            .withSampler("InSampler")
            //?}
            .build()
    )

    val UP: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.POST_PROCESSING_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cascade", "kawase_up"))
            .withVertexShader(Identifier.fromNamespaceAndPath("minecraft", "core/screenquad"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/effects/blur/kawase/kawase_up"))
            .withColorTargetState(ColorTargetState.DEFAULT)
            //? if >= 26.3 {
            /*.withBindGroupLayout(BindGroupLayout.builder()
                .withUniform("InSampler", UniformType.COMBINED_IMAGE_SAMPLER)
                .build())
            *///?} elif 26.2 {
            /*.withBindGroupLayout(BindGroupLayout.builder()
                .withSampler("InSampler")
                .build())
            *///? } else {
            .withSampler("InSampler")
            //?}
            .build()
    )

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
}
