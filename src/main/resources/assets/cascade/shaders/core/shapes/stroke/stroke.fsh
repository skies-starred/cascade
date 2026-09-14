#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>

//$ layout '0' 'in' >> vec
in vec4 vertexColor;
//$ layout '1' 'in' >> vec
in vec2 localUV;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    vec2 uv = abs(localUV);
    vec2 width = max(fwidth(uv), vec2(0.0001));
    vec2 edge = (uv - 1.0) / width;

    float alpha = 1.0 - smoothstep(-0.5, 0.5, max(edge.x, edge.y));
    if (alpha <= 0.0) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}