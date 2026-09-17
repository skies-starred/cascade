#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>
#moj_import <cascade:box.glsl>

//$ layout '0' 'in' >> vec
in vec3 Position;
//$ layout '1' 'in' >> vec
in vec4 Color;
//$ layout '2' 'in' >> vec
in vec2 UV0;
//$ layout '3' 'in' >> ivec
in ivec2 UV1;
//$ layout '4' 'in' >> ivec
in ivec2 UV2;
//$ layout '5' 'in' >> vec
in vec3 Normal;

//$ layout '0' 'out' >> vec
out vec2 uv0;
//$ layout '1' 'out' >> vec
out vec2 coord0;
//$ layout '2' 'out' >> vec
out vec4 color0;
//$ layout '3' 'flat out' >> vec
flat out vec2 half0;
//$ layout '4' 'flat out' >> vec
flat out vec4 radius0;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    uint packed0 = floatBitsToUint(UV0.x);
    uv0 = vec2(float(packed0 & 0xFFFFu), float(packed0 >> 16u)) / 65535.0;
    color0 = Color;

    uint size0 = floatBitsToUint(UV0.y);
    half0 = vec2(float(size0 >> 16u), float(size0 & 0xFFFFu)) * 0.1;
    coord0 = Normal.xy * half0;
    radius0 = radii(UV1, UV2);
}
