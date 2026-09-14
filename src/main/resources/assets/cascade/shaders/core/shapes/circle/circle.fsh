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
    float dist = length(localUV);
    float aa = fwidth(dist);
    float alpha = 1.0 - smoothstep(1.0 - aa, 1.0, dist);
    if (alpha <= 0.0) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}