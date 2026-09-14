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
out vec4 vertexColor;
//$ layout '1' 'out' >> vec
out vec2 localCoord;
//$ layout '2' 'flat out' >> vec
flat out vec2 p1Coord;
//$ layout '3' 'flat out' >> vec
flat out vec2 p2Coord;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color;
    localCoord = UV0;

    int p1x = UV1.x;
    if (p1x > 32767) p1x -= 65536;

    int p1y = UV1.y;
    if (p1y > 32767) p1y -= 65536;

    p1Coord = vec2(float(p1x), float(p1y));

    int p2x = UV2.x;
    if (p2x > 32767) p2x -= 65536;

    int p2y = UV2.y;
    if (p2y > 32767) p2y -= 65536;

    p2Coord = vec2(float(p2x), float(p2y));
}