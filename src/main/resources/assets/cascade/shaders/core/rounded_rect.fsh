#version 330

layout(std140) uniform DynamicTransforms {
    mat4 ModelViewMat;
    vec4 ColorModulator;
    vec3 ModelOffset;
    mat4 TextureMat;
};

in vec2 localCoord;
in vec4 vertexColor;
flat in vec2 rectSize;
flat in vec4 cornerRadii;
flat in float outlineW;

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
    float dist = roundedBox(localCoord - half0, half0, min(cornerRadii, vec4(min(half0.x, half0.y))));

    float dist0 = fwidth(dist);
    float alpha = outlineW > 0.0 ? 1.0 - smoothstep(-dist0, dist0, abs(dist + outlineW * 0.5) - outlineW * 0.5) : 1.0 - smoothstep(-dist0, dist0, dist);

    if (alpha < 0.001) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}