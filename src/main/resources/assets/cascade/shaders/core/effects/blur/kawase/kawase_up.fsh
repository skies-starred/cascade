#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

uniform sampler2D InSampler;

//$ layout '0' 'in' >> vec
in vec2 texCoord;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    vec2 halfPixel = 0.5 / vec2(textureSize(InSampler, 0));

    vec4 sum = vec4(0.0);
    sum += texture(InSampler, texCoord + vec2(-halfPixel.x * 2.0, 0.0));
    sum += texture(InSampler, texCoord + vec2(-halfPixel.x, halfPixel.y)) * 2.0;
    sum += texture(InSampler, texCoord + vec2(0.0, halfPixel.y * 2.0));
    sum += texture(InSampler, texCoord + vec2(halfPixel.x, halfPixel.y)) * 2.0;
    sum += texture(InSampler, texCoord + vec2(halfPixel.x * 2.0, 0.0));
    sum += texture(InSampler, texCoord + vec2(halfPixel.x, -halfPixel.y)) * 2.0;
    sum += texture(InSampler, texCoord + vec2(0.0, -halfPixel.y * 2.0));
    sum += texture(InSampler, texCoord + vec2(-halfPixel.x, -halfPixel.y)) * 2.0;

    fragColor = sum * (1.0 / 12.0);
}
