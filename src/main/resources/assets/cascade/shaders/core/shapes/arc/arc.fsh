#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <cascade:antialias.glsl>

//$ layout '0' 'in' >> vec
in vec4 color0;
//$ layout '1' 'in' >> vec
in vec2 coord0;
//$ layout '2' 'flat in' >> vec
flat in vec2 radius0;
//$ layout '3' 'flat in' >> vec
flat in vec2 angle0;

//$ layout '0' 'out' >> vec
out vec4 fragColor;

void main() {
    float distance0 = length(coord0);

    float radius1 = radius0.x;
    float radius2 = abs(radius0.y);
    float radius3 = (radius1 + radius2) * 0.5;
    float radius4 = (radius2 - radius1) * 0.5;

    float distance1 = abs(distance0 - radius3);
    float alpha0 = antialias(distance1, radius4);

    float angle1 = angle0.x;
    float angle2 = angle0.y;
    float angle3 = mod(angle2 - angle1 + 360.0, 360.0);

    if (angle3 > 0.1 && angle3 < 359.9) {
        float angle4 = degrees(atan(coord0.x, -coord0.y));
        if (angle4 < 0.0) angle4 += 360.0;

        float angle5 = mod(angle4 - angle1 + 360.0, 360.0);
        if (angle5 > angle3) {
            if (radius0.y >= 0.0) discard;

            vec2 cap0 = vec2(sin(radians(angle1)), -cos(radians(angle1))) * radius3;
            vec2 cap1 = vec2(sin(radians(angle2)), -cos(radians(angle2))) * radius3;

            float distance2 = length(coord0 - cap0);
            float distance3 = length(coord0 - cap1);

            alpha0 = antialias(min(distance2, distance3), radius4);
        }
    }

    if (alpha0 <= 0.0) discard;

    fragColor = color0 * ColorModulator;
    fragColor.a *= alpha0;
}
