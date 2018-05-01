
#version 430

in layout(location=0) vec3 ModelPosition;
in layout(location=2) vec2 vertexTexture;
uniform mat4 ModeltoProjectionMatrix;
out vec2 UV;

void main(){
vec4 v =  vec4(ModelPosition, 1.0f);
gl_Position = ModeltoProjectionMatrix * v;
UV = vertexTexture;
}
