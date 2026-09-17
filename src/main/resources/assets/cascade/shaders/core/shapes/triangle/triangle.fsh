#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:triangle.glsl>

//$ layout '0' 'in' >> vec
in vec4 color0;
//$ layout '1' 'in' >> vec
in vec2 coord0;
//$ layout '2' 'flat in' >> vec
flat in vec2 point1;
//$ layout '3' 'flat in' >> vec
flat in vec2 point2;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = triangle(coord0, vec2(0.0), point1, point2);
    float delta0 = fwidth(distance0);
    float alpha0 = 1.0 - smoothstep(-delta0, 0.0, distance0);

    if (alpha0 <= 0.0) discard;

    fragColor = color0 * ColorModulator;
    fragColor.a *= alpha0;
}