#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:box.glsl>
#moj_import <cascade:antialias.glsl>

uniform sampler2D Sampler0;

//$ layout '0' 'in' >> vec
in vec2 coord0;
//$ layout '1' 'in' >> vec
in vec4 color0;
//$ layout '2' 'in' >> vec
in vec2 screen0;
//$ layout '3' 'flat in' >> vec
flat in vec2 half0;
//$ layout '4' 'flat in' >> vec
flat in vec4 radius0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = box(coord0, half0, radius0);
    float alpha0 = antialias(distance0);

    if (alpha0 < 0.001) discard;

    vec4 scene = texture(Sampler0, screen0);
    vec4 tint = color0 * ColorModulator;

    fragColor = scene * tint;
    fragColor.a *= alpha0;
}
