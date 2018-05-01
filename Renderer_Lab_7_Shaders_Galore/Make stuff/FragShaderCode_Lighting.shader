#version 430

out vec4 Color;
in vec2 UV;
in vec3 WorldNormal;
in vec3 theColor;
in vec3 WorldPosition;
uniform vec3 WorldLightPosition;
uniform vec3 WorldCameraPosition;
uniform vec3 intensityOfDiffuseLight;
uniform vec3 intensityOfSpecularLight;
uniform float SpecExponent;
uniform vec3 ambientLight;
uniform sampler2D myTextureSampler;

void main(){

vec3 totalColor = texture( myTextureSampler, UV ).rgb;
totalColor += theColor;

//diffuse
vec3 Lightvector = normalize(WorldLightPosition - WorldPosition);
float brightness = dot(Lightvector, WorldNormal);
vec3 diffuse = vec3(brightness*intensityOfDiffuseLight.r, brightness*intensityOfDiffuseLight.g, brightness*intensityOfDiffuseLight.b);


//specular

vec3 worldReflectedLightVector = reflect(-Lightvector, WorldNormal);
vec3 worldEyeVector = normalize(WorldCameraPosition - WorldPosition);
float specularity = dot(worldReflectedLightVector, worldEyeVector);
specularity = pow(specularity, SpecExponent);
vec3 specularLight = vec3((specularity*intensityOfSpecularLight.r), (specularity*intensityOfSpecularLight.g), (specularity*intensityOfSpecularLight.b));
Lightvector =  (ambientLight + clamp(diffuse, 0, 1))*totalColor + clamp(specularLight, 0, 1);
Color = vec4(Lightvector, 1);     

}
 