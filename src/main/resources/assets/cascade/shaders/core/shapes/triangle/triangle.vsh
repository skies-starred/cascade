#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;

out vec4 vertexColor;
out vec2 localCoord;
flat out vec2 p1Coord;
flat out vec2 p2Coord;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color * ColorModulator;
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