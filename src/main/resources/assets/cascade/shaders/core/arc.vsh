#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;

out vec4 vertexColor;
out vec2 localUV;
flat out vec2 arcRadii;
flat out vec2 arcAngles;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color * ColorModulator;
    localUV = UV0;
    arcRadii = vec2(float(UV1.x), float(UV1.y));
    arcAngles = vec2(float(UV2.x) / 10.0, float(UV2.y) / 10.0);
}