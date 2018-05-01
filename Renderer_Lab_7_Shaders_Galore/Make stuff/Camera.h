#ifndef CAMERA_H
#define CAMERA_H
#pragma warning(push)
#pragma warning(disable : 4201)
#include "glm.hpp"
#pragma warning(pop)
class Camera
{
public:
	glm::vec3 position;
	glm::vec3 viewDirection;
	glm::vec3 UP;
	Camera();
	void initilize();
	glm::mat4 getWorldToViewMatrix() const;
	void setViewDirection(glm::vec3 new_direction);
	void setPosition(glm::vec3 new_position, glm::vec3 offset);
	void setUP_Direction(glm::vec3 newValue);
};

#endif