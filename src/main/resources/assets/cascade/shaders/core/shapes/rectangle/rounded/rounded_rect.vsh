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
out vec2 localCoord;
//$ layout '1' 'out' >> vec
out vec4 vertexColor;
//$ layout '2' 'flat out' >> vec
flat out vec2 rectSize;
//$ layout '3' 'flat out' >> vec
flat out vec4 cornerRadii;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    localCoord = UV0;
    vertexColor = Color;

    rectSize = vec2(float(UV1.x), float(UV1.y));
    cornerRadii = vec4(float(UV2.x & 0xFF), float((UV2.x >> 8) & 0xFF), float(UV2.y & 0xFF), float((UV2.y >> 8) & 0xFF)) * 0.1;
}