#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

out vec2 localCoord;
out vec4 vertexColor;
out vec2 screenUv;
flat out vec2 rectSize;
flat out vec4 cornerRadii;
flat out float outlineW;
flat out float blurRadius;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    screenUv = (gl_Position.xy / gl_Position.w) * 0.5 + 0.5;

    localCoord = UV0;
    vertexColor = Color;

    rectSize = vec2(float(UV1.x), float(UV1.y));
    cornerRadii = vec4(float(UV2.x & 0xFF), float((UV2.x >> 8) & 0xFF), float(UV2.y & 0xFF), float((UV2.y >> 8) & 0xFF)) * 0.1;

    outlineW = Normal.x * 127.0;
    blurRadius = max(Normal.y * 127.0, 0.0);
}