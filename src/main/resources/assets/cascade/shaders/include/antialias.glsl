#version 330

float antialias(float distance0) {
    float delta0 = fwidth(distance0);
    return 1.0 - smoothstep(-delta0, delta0, distance0);
}

float antialias(float distance0, float radius0) {
    float delta0 = fwidth(distance0);
    return 1.0 - smoothstep(radius0 - delta0, radius0, distance0);
}
