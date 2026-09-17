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
out vec2 coord0;
//$ layout '1' 'out' >> vec
out vec4 color0;
//$ layout '2' 'flat out' >> vec
flat out vec2 half0;
//$ layout '3' 'flat out' >> vec
flat out vec4 radius0;
//$ layout '4' 'flat out' >> float
flat out float thickness0;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    coord0 = UV0;
    color0 = Color;

    half0 = abs(UV0);
    radius0 = radii(UV1, UV2);

    thickness0 = Normal.x * 127.0;
}
