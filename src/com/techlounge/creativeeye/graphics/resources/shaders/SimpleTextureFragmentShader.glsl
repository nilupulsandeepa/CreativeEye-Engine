#version 150 core

in vec2 passUVCoordinates;

out vec4 outColor;

uniform sampler2D uvTexture;

void main() {
    outColor = texture(uvTexture, passUVCoordinates);
}