#version 330

float radius(vec2 coord0, vec4 radius0) {
    if (coord0.x <= 0.0) return coord0.y <= 0.0 ? radius0.x : radius0.w;
    return coord0.y <= 0.0 ? radius0.y : radius0.z;
}

float box(vec2 coord0, vec2 half0, vec4 radius0) {
    vec4 radius1 = min(radius0, vec4(min(half0.x, half0.y)));
    float corner0 = radius(coord0, radius1);
    vec2 delta0 = abs(coord0) - half0 + corner0;
    return min(max(delta0.x, delta0.y), 0.0) + length(max(delta0, 0.0)) - corner0;
}

float roundedBox(vec2 coord0, vec2 half0, vec4 radius0) {
    return box(coord0, half0, radius0);
}

float hollow(vec2 coord0, vec2 half0, vec4 radius0, float thickness0) {
    float distance0 = box(coord0, half0, radius0);
    return abs(distance0 + thickness0 * 0.5) - thickness0 * 0.5;
}

vec4 radii(ivec2 uv0, ivec2 uv1) {
    return vec4(float(uv0.x), float(uv0.y), float(uv1.x), float(uv1.y)) * 0.1;
}
