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
//$ layout '4' 'flat in' >> vec
flat in vec2 offset0;
//$ layout '5' 'flat in' >> float
flat in float blur0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = box(coord0, half0, radius0);
    float alpha0 = antialias(distance0);
    if (alpha0 < 0.001) discard;

    vec2 coord1 = coord0 - offset0;
    float distance1 = box(coord1, half0, radius0);

    float blur1 = max(blur0, 0.0);
    float alpha1;
    if (blur1 > 0.0) {
        alpha1 = smoothstep(-blur1, 0.0, distance1);
    } else {
        alpha1 = distance1 > 0.0 ? 1.0 : 0.0;
    }

    float alpha2 = alpha0 * alpha1;
    if (alpha2 < 0.001) discard;

    fragColor = color0 * ColorModulator;
    fragColor.a *= alpha2;
}
