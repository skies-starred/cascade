#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:box.glsl>
#moj_import <cascade:antialias.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

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
//$ layout '5' 'flat in' >> float
flat in float blend0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = box(coord0, half0, radius0);
    float alpha0 = antialias(distance0);

    if (alpha0 < 0.001) discard;

    vec4 tint0 = color0 * ColorModulator;
    vec3 blur0 = texture(Sampler0, screen0).rgb;
    vec3 blur1 = texture(Sampler1, screen0).rgb;
    vec3 blur2 = mix(blur0, blur1, blend0);

    tint0.rgb = mix(blur2, tint0.rgb, tint0.a);
    tint0.a = 1.0;

    fragColor = tint0;
    fragColor.a *= alpha0;
}