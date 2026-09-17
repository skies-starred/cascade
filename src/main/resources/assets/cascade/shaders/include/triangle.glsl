#version 330

float triangle(vec2 coord0, vec2 point0, vec2 point1, vec2 point2) {
    vec2 edge0 = point1 - point0;
    vec2 edge1 = point2 - point1;
    vec2 edge2 = point0 - point2;

    vec2 vector0 = coord0 - point0;
    vec2 vector1 = coord0 - point1;
    vec2 vector2 = coord0 - point2;

    vec2 segment0 = vector0 - edge0 * clamp(dot(vector0, edge0) / dot(edge0, edge0), 0.0, 1.0);
    vec2 segment1 = vector1 - edge1 * clamp(dot(vector1, edge1) / dot(edge1, edge1), 0.0, 1.0);
    vec2 segment2 = vector2 - edge2 * clamp(dot(vector2, edge2) / dot(edge2, edge2), 0.0, 1.0);

    float sign0 = sign(edge0.x * edge2.y - edge0.y * edge2.x);
    vec2 distance0 = min(
        min(vec2(dot(segment0, segment0), sign0 * (vector0.x * edge0.y - vector0.y * edge0.x)), vec2(dot(segment1, segment1), sign0 * (vector1.x * edge1.y - vector1.y * edge1.x))),
        vec2(dot(segment2, segment2), sign0 * (vector2.x * edge2.y - vector2.y * edge2.x))
    );

    return -sqrt(distance0.x) * sign(distance0.y);
}
