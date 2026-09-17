#version 330
//? if >= 26.3
//#extension GL_ARB_separate_shader_objects : require

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

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

//$ layout '0' 'out' >> vec
out vec4 color0;
//$ layout '1' 'out' >> vec
out vec2 coord0;
//$ layout '2' 'flat out' >> vec
flat out vec2 radius0;
//$ layout '3' 'flat out' >> vec
flat out vec2 angle0;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    color0 = Color;
    coord0 = UV0;
    radius0 = vec2(float(UV1.x), float(UV1.y));
    angle0 = vec2(float(UV2.x) / 10.0, float(UV2.y) / 10.0);
}