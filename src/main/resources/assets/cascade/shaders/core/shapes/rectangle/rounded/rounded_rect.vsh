#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;

out vec2 localCoord;
out vec4 vertexColor;
flat out vec2 rectSize;
flat out vec4 cornerRadii;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    localCoord = UV0;
    vertexColor = Color;

    rectSize = vec2(float(UV1.x), float(UV1.y));
    cornerRadii = vec4(float(UV2.x & 0xFF), float((UV2.x >> 8) & 0xFF), float(UV2.y & 0xFF), float((UV2.y >> 8) & 0xFF)) * 0.1;
}