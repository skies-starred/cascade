package foo.starred.cascade.internal.pipeline.vertex.impl

import com.mojang.blaze3d.vertex.VertexFormat
import foo.starred.cascade.internal.pipeline.vertex.builder.CascadeVertexFormatBuilder.Companion.cascadeVertexFormat

enum class CascadeVertexFormats(val format: VertexFormat) {
    UV(cascadeVertexFormat {
        position()
        color()
        uv0()
    }),
    UV3(cascadeVertexFormat {
        position()
        color()
        uv0()
        uv1()
        uv2()
    }),
    UV3_NORMAL(cascadeVertexFormat {
        position()
        color()
        uv0()
        uv1()
        uv2()
        normal()
    })
}
