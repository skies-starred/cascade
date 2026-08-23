#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec2 localCoord;
in vec4 vertexColor;
in vec2 screenUv;
flat in vec2 rectSize;
flat in vec4 cornerRadii;
flat in vec2 shadowOffset;
flat in float blurRadius;

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
    vec4 clampedRadii = min(cornerRadii, vec4(min(half0.x, half0.y)));

    vec2 p = localCoord - half0;
    float dist = roundedBox(p, half0, clampedRadii);

    float d0 = fwidth(dist);
    float insideAlpha = 1.0 - smoothstep(-d0, d0, dist);
    if (insideAlpha < 0.001) discard;

    vec2 pOffset = p - shadowOffset;
    float shadowDist = roundedBox(pOffset, half0, clampedRadii);

    float blur = max(blurRadius, 0.0);
    float shadowAlpha;
    if (blur > 0.0) {
        shadowAlpha = smoothstep(-blur, 0.0, shadowDist);
    } else {
        shadowAlpha = shadowDist > 0.0 ? 1.0 : 0.0;
    }

    float alpha = insideAlpha * shadowAlpha;
    if (alpha < 0.001) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}
