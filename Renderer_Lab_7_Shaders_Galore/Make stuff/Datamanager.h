#ifndef DATA_MANAGER_H
#define DATA_MANAGER_H
#pragma warning(push)
#pragma warning(disable : 4201)
#include <glm.hpp>
#pragma warning(pop)
struct Datamanager
{
	glm::vec3 ambientLight;
	glm::vec3 diffuseLight;
	glm::vec3 SpecularColor;
	float SpecExponent;
};
#endif

