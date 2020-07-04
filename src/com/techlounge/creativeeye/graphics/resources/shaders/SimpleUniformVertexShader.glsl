#version 150 core

in vec3 vertexPosition;
in vec2 uvCoordinates;

out vec2 passUVCoordinates;

uniform float scale;

void main() {
    gl_Position = vec4(vertexPosition, 1.0) * vec4(scale, scale, scale, 1.0);
    passUVCoordinates = uvCoordinates;
}