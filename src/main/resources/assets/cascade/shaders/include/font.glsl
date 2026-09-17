#version 330

float median(float color0, float color1, float color2) {
    return max(min(color0, color1), min(max(color0, color1), color2));
}

float fontRange(sampler2D sampler0, vec2 coord0, float range0) {
    vec2 unit0 = vec2(range0) / vec2(textureSize(sampler0, 0));
    vec2 size0 = vec2(1.0) / fwidth(coord0);
    return max(0.5 * dot(unit0, size0), 1.0);
}
