#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:antialias.glsl>

//$ layout '0' 'in' >> vec
in vec4 color0;
//$ layout '1' 'in' >> vec
in vec2 coord0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = length(coord0);
    float alpha0 = antialias(distance0, 1.0);
    if (alpha0 <= 0.0) discard;

    fragColor = color0 * ColorModulator;
    fragColor.a *= alpha0;
}