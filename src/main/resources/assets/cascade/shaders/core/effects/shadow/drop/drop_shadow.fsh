#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:box.glsl>
#moj_import <cascade:antialias.glsl>

//$ layout '0' 'in' >> vec
in vec2 coord0;
//$ layout '1' 'in' >> vec
in vec4 color0;
//$ layout '2' 'flat in' >> vec
flat in vec2 half0;
//$ layout '3' 'flat in' >> vec
flat in vec4 radius0;
//$ layout '4' 'flat in' >> float
flat in float blur0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    vec2 half1 = max(half0 - vec2(blur0), vec2(0.0));
    float distance0 = box(coord0, half1, radius0);

    float alpha0;
    if (blur0 > 0.0) {
        alpha0 = distance0 <= 0.0 ? 1.0 : clamp(1.0 - smoothstep(0.0, blur0, distance0), 0.0, 1.0);
    } else {
        alpha0 = antialias(distance0);
    }

    if (alpha0 < 0.001) discard;

    fragColor = color0 * ColorModulator;
    fragColor.a *= alpha0;
}
