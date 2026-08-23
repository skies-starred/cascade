#version 330

#moj_import <minecraft:dynamictransforms.glsl>

in vec4 vertexColor;
in vec2 localUV;
flat in vec2 arcRadii;
flat in vec2 arcAngles;

out vec4 fragColor;

void main() {
    float distance = length(localUV);
    float aa = fwidth(distance);

    float radius0 = arcRadii.x;
    float radius1 = abs(arcRadii.y);
    float radius2 = (radius0 + radius1) * 0.5;
    float radius3 = (radius1 - radius0) * 0.5;

    float distance0 = abs(distance - radius2);
    float alpha = 1.0 - smoothstep(radius3 - aa, radius3, distance0);

    float angle0 = arcAngles.x;
    float angle1 = arcAngles.y;
    float angle2 = mod(angle1 - angle0 + 360.0, 360.0);

    if (angle2 > 0.1 && angle2 < 359.9) {
        float angle3 = degrees(atan(localUV.x, -localUV.y));
        if (angle3 < 0.0)angle3 += 360.0;

        float angle4 = mod(angle3 - angle0 + 360.0, 360.0);
        if (angle4 > angle2) {
            if (arcRadii.y >= 0.0) discard;

            vec2 cap0 = vec2(sin(radians(angle0)), -cos(radians(angle0))) * radius2;
            vec2 cap1 = vec2(sin(radians(angle1)), -cos(radians(angle1))) * radius2;

            float d0 = length(localUV - cap0);
            float d1 = length(localUV - cap1);

            alpha = 1.0 - smoothstep(radius3 - aa, radius3, min(d0, d1));
        }
    }

    if (alpha <= 0.0) {
        discard;
    }

    fragColor = vertexColor * ColorModulator;
    fragColor.a *= alpha;
}