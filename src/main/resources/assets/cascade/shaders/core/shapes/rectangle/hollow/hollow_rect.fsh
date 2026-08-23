#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec2 localCoord;
in vec4 vertexColor;
flat in vec2 rectSize;
flat in vec4 cornerRadii;
flat in float thickness;

out vec4 fragColor;

float radius(vec2 p, vec4 r) {
    if (p.x <= 0.0) return p.y <= 0.0 ? r.x : r.w;
    return p.y <= 0.0 ? r.y : r.z;
}

float roundedBox(vec2 p, vec2 b, vec4 r) {
    float corner = radius(p, r);
    vec2 q = abs(p) - b + corner;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - corner;
}

void main() {
    vec2 half0 = rectSize * 0.5;
    float dist0 = roundedBox(localCoord - half0, half0, min(cornerRadii, vec4(min(half0.x, half0.y))));

    float ring = abs(dist0 + thickness * 0.5) - thickness * 0.5;
    float delta = fwidth(dist0);
    float alpha = 1.0 - smoothstep(-delta, delta, ring);

    if (alpha < 0.001) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}
