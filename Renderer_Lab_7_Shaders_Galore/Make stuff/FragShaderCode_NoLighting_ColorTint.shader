#version 430

out vec4 Color;
in vec2 UV;
uniform sampler2D myTextureSampler;
uniform vec3 Tint;

void main(){

vec3 totalColor = texture( myTextureSampler, UV ).rgb;
totalColor += Tint;
Color = vec4(totalColor, 1.0f);
}
 