#include "Light.h"
#include <iostream>
#include "LogWriter.h"
#include "..\Render Engine\BufferInfo.h"
#include <Windows.h>
Light::Light(glm::vec3 ambient)
{
	ambient;
	glm::vec3 lightCheck = ConfigInfo.GetVec3FromString(ConfigInfo.FindValueForKey("Light_Position"));
	if (lightCheck.x != NULL){
		LightPosition = lightCheck;
	}
	else{
		LightPosition = glm::vec3(5, 5, 0);
	}
	LOG(Info, "Light created");
}

Light::Light(){}

void Light::Update(float dt, glm::vec3 ambient, glm::vec3 diffuse){
	ambient;
	diffuse;
	dt;
	if (GetAsyncKeyState('I')){
		LightPosition.x -= LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('K')){
		LightPosition.x += LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('J')){
		LightPosition.z += LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('L')){
		LightPosition.z -= LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('U')){
		LightPosition.y -= LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('O')){
		LightPosition.y += LIGHTSPEED*dt;
	}
	if (GetAsyncKeyState('P')){
		LightPosition = glm::vec3(5, 5, 0);
	}
}


Light::~Light()
{
}
