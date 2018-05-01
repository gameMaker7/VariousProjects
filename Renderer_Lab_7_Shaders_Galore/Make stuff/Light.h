#ifndef LIGHTING_H
#define LIGHTING_H
#pragma warning(push)
#pragma warning(disable : 4201)
#include <detail/type_vec3.hpp>
#pragma warning(pop)
class Light
{
public:

	glm::vec3 LightPosition;
	float LIGHTSPEED = 0.05f;
	Light(glm::vec3 ambient);
	Light();
	void Update(float, glm::vec3, glm::vec3); 
	void diffuseChange(glm::vec3);
	~Light();
};

#endif