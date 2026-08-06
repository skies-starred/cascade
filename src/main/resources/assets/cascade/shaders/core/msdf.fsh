#version 330

layout(std140) uniform DynamicTransforms {
    mat4 ModelViewMat;
    vec4 ColorModulator;
    vec3 ModelOffset;
    mat4 TextureMat;
};

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

float range() {
    vec2 unitRange = vec2(16.0) / vec2(textureSize(Sampler0, 0));
    vec2 screenTexSize = vec2(1.0) / fwidth(texCoord0);
    return max(0.5 * dot(unitRange, screenTexSize), 1.0);
}

void main() {
    vec4 texel = texture(Sampler0, texCoord0);
    float dist = median(texel.r, texel.g, texel.b);
    if (dist < 0.01) discard;

    float pxDist = range() * (dist - 0.5);
    float opacity = clamp(pxDist + 0.5, 0.0, 1.0);

    fragColor = vec4(1.0, 1.0, 1.0, opacity) * vertexColor;
}