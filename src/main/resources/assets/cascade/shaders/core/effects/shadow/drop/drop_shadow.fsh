#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>

//$ layout '0' 'in' >> vec
in vec2 localCoord;
//$ layout '1' 'in' >> vec
in vec4 vertexColor;
//$ layout '2' 'flat in' >> vec
flat in vec2 rectSize;
//$ layout '3' 'flat in' >> vec
flat in vec4 cornerRadii;
//$ layout '4' 'flat in' >> float
flat in float blurRadius;

//$ layout '0' 'out' >> vec
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
    vec2 quadHalf = half0 + vec2(blurRadius);
    vec2 p = localCoord - quadHalf;

    float dist = roundedBox(p, half0, min(cornerRadii, vec4(min(half0.x, half0.y))));

    float d0 = fwidth(dist);
    float blur = max(blurRadius, 0.0);

    float alpha;
    if (blur > 0.0) {
        alpha = dist <= 0.0 ? 1.0 : clamp(1.0 - smoothstep(0.0, blur, dist), 0.0, 1.0);
    } else {
        alpha = 1.0 - smoothstep(-d0, d0, dist);
    }

    if (alpha < 0.001) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}
