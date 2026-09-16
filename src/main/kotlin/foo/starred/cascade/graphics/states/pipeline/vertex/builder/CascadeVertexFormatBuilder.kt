package foo.starred.cascade.graphics.states.pipeline.vertex.builder

import foo.starred.cascade.graphics.states.pipeline.vertex.data.CascadeVertexFormatElement

import com.mojang.blaze3d.vertex.VertexFormat

//? if >= 26.2 {
/*import com.mojang.blaze3d.vertex.VertexFormatElement
*///?} else {
import com.mojang.blaze3d.vertex.VertexFormatElement
//?}

class CascadeVertexFormatBuilder {
    private val elements = mutableListOf<CascadeVertexFormatElement>()

    fun build(): VertexFormat {
        //~ if >= 26.2 'builder()' -> 'builder(0)'
        val builder = VertexFormat.builder()

        //? if >= 26.2 {
        /*for (element in elements) when (element) {
            CascadeVertexFormatElement.POSITION -> builder.addAttribute("Position", GpuFormat.RGB32_FLOAT)
            CascadeVertexFormatElement.COLOR -> builder.addAttribute("Color", GpuFormat.RGBA8_UNORM)
            CascadeVertexFormatElement.UV0 -> builder.addAttribute("UV0", GpuFormat.RG32_FLOAT)
            CascadeVertexFormatElement.UV1 -> builder.addAttribute("UV1", GpuFormat.RG16_SINT)
            CascadeVertexFormatElement.UV2 -> builder.addAttribute("UV2", GpuFormat.RG16_SINT)
            CascadeVertexFormatElement.NORMAL -> builder.addAttribute("Normal", GpuFormat.RGBA8_SNORM)
        }
        *///? } else {
        for (element in elements) when (element) {
            CascadeVertexFormatElement.POSITION -> builder.add("Position", VertexFormatElement.POSITION)
            CascadeVertexFormatElement.COLOR -> builder.add("Color", VertexFormatElement.COLOR)
            CascadeVertexFormatElement.UV0 -> builder.add("UV0", VertexFormatElement.UV0)
            CascadeVertexFormatElement.UV1 -> builder.add("UV1", VertexFormatElement.UV1)
            CascadeVertexFormatElement.UV2 -> builder.add("UV2", VertexFormatElement.UV2)
            CascadeVertexFormatElement.NORMAL -> builder.add("Normal", VertexFormatElement.NORMAL).padding(1)
        }
        //? }

        return builder.build()
    }

    fun position() = apply {
        elements += CascadeVertexFormatElement.POSITION
    }

    fun color() = apply {
        elements += CascadeVertexFormatElement.COLOR
    }

    fun uv0() = apply {
        elements += CascadeVertexFormatElement.UV0
    }

    fun uv1() = apply {
        elements += CascadeVertexFormatElement.UV1
    }

    fun uv2() = apply {
        elements += CascadeVertexFormatElement.UV2
    }

    fun normal() = apply {
        elements += CascadeVertexFormatElement.NORMAL
    }

    companion object {
        fun cascadeVertexFormat(block: CascadeVertexFormatBuilder.() -> Unit): VertexFormat {
            return CascadeVertexFormatBuilder().apply(block).build()
        }
    }
}
