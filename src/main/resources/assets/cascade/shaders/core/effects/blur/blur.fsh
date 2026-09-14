#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:blur.glsl>

uniform sampler2D Sampler0;

//$ layout '0' 'in' >> vec
in vec2 localCoord;
//$ layout '1' 'in' >> vec
in vec4 vertexColor;
//$ layout '2' 'in' >> vec
in vec2 screenUv;
//$ layout '3' 'flat in' >> vec
flat in vec2 rectSize;
//$ layout '4' 'flat in' >> vec
flat in vec4 cornerRadii;
//$ layout '5' 'flat in' >> float
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
    float dist = roundedBox(localCoord - half0, half0, min(cornerRadii, vec4(min(half0.x, half0.y))));

    float delta = fwidth(dist);
    float alpha = 1.0 - smoothstep(-delta, delta, dist);

    if (alpha < 0.001) discard;

    vec4 color = vertexColor * ColorModulator;
    vec2 texelSize = 1.0 / vec2(textureSize(Sampler0, 0));
    vec3 blur = blur(Sampler0, screenUv, texelSize, blurRadius).rgb;
    color.rgb = mix(blur, color.rgb, color.a);
    color.a = 1.0;

    fragColor = color;
    fragColor.a *= alpha;
}