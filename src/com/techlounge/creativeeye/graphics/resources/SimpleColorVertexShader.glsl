#version 150 core

in vec3 vertexPosition;
in vec4 vertexColor;

out vec4 color;

void main() {
    gl_Position = vec4(vertexPosition, 1.0);
    color = vertexColor;
}