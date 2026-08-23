#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec4 vertexColor;
in vec2 localUV;

out vec4 fragColor;

void main() {
    float dist = length(localUV);
    float aa = fwidth(dist);
    float alpha = 1.0 - smoothstep(1.0 - aa, 1.0, dist);
    if (alpha <= 0.0) discard;

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}