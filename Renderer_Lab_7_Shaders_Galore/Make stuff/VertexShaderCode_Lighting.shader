
#version 430

in layout(location=0) vec3 ModelPosition;
in layout(location=1) vec3 vertexColor;
in layout(location=2) vec2 vertexTexture;
in layout(location=3) vec3 ModelNormal;
uniform mat4 ModeltoProjectionMatrix;
uniform mat4 WorldMatrix;
out vec3 WorldNormal;
out vec3 WorldPosition;
out vec3 theColor;
out vec2 UV;

void main(){
vec4 v = vec4(ModelPosition, 1.0f);
gl_Position = ModeltoProjectionMatrix * v;
theColor = vertexColor; 
UV = vertexTexture;
WorldPosition = vec3(WorldMatrix * v);
WorldNormal = vec3(WorldMatrix * vec4(normalize(ModelNormal), 0));

}