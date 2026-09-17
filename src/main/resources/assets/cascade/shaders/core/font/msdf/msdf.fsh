#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:font.glsl>

uniform sampler2D Sampler0;

//$ layout '0' 'in' >> vec
in vec2 texCoord0;
//$ layout '1' 'in' >> vec
in vec4 vertexColor;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    vec4 texel0 = texture(Sampler0, texCoord0);
    float distance0 = median(texel0.r, texel0.g, texel0.b);
    if (distance0 < 0.01) discard;

    float distance1 = fontRange(Sampler0, texCoord0, 16.0) * (distance0 - 0.5);
    float opacity0 = clamp(distance1 + 0.5, 0.0, 1.0);

    fragColor = vec4(1.0, 1.0, 1.0, opacity0) * vertexColor * ColorModulator;
}
