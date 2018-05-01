#version 430

out vec4 Color;
in vec2 UV;
uniform sampler2D myTextureSampler;
void main(){

vec3 totalColor = texture( myTextureSampler, UV ).rgb;
Color = vec4(totalColor, 1.0f);
}
 