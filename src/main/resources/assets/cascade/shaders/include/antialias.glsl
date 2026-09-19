#version 330

float antialias(float distance0) {
    float delta0 = length(vec2(dFdx(distance0), dFdy(distance0)));
    return clamp(0.5 - distance0 / max(delta0, 0.0001), 0.0, 1.0);
}

float antialias(float distance0, float radius0) {
    float delta0 = length(vec2(dFdx(distance0), dFdy(distance0)));
    return clamp((radius0 - distance0) / max(delta0, 0.0001), 0.0, 1.0);
}
