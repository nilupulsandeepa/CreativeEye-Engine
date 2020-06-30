#version 150 core

in vec3 vertexPosition;
in vec2 uvCoordinates;

out vec2 passUVCoordinates;

void main() {
    gl_Position = vec4(vertexPosition, 1.0);
    passUVCoordinates = uvCoordinates;
}