#include "Camera.h"
#include <gtc/matrix_transform.inl>

Camera::Camera()
{
	UP = glm::vec3(0, 1, 0);
}

void Camera::initilize()
{
}

void Camera::setViewDirection(glm::vec3 new_direction)
{
	viewDirection = new_direction;
}

void Camera::setPosition(glm::vec3 new_position, glm::vec3 offset)
{
	position = new_position+offset;
}

void Camera::setUP_Direction(glm::vec3 newValue)
{
	UP = newValue;
}

glm::mat4 Camera::getWorldToViewMatrix()const {
	return glm::lookAt(position, position + viewDirection, UP);
}
