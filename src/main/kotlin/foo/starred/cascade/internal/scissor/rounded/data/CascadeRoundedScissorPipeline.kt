package foo.starred.cascade.internal.scissor.rounded.data

import com.mojang.blaze3d.pipeline.RenderPipeline
import foo.starred.cascade.internal.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.internal.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.renderer.RenderPipelines

object CascadeRoundedScissorPipeline {
    val START: RenderPipeline = cascadeRenderPipeline(
        "rounded_scissor_start",
        CascadeVertexFormats.UV3,
        "core/effects/scissor/rounded/rounded_scissor",
        RenderPipelines.GUI_TEXTURED_SNIPPET
    )

    val BLIT: RenderPipeline = cascadeRenderPipeline(
        "rounded_scissor_blit",
        CascadeVertexFormats.UV3,
        "core/effects/scissor/rounded/rounded_scissor",
        RenderPipelines.GUI_TEXTURED_SNIPPET
    )
}
