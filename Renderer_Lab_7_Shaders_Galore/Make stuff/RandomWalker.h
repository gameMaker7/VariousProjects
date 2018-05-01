#pragma once
#include "MovementController.h"
class RandomWalker :
	public MovementController
{
public:
	void checkPosition(float dt);
	void setCenter(glm::vec3 center);
	void Update(float dt);
	void setRandomDestination(glm::vec3 center);
	RandomWalker();

protected:
	glm::vec3 center;
	glm::vec3 destination;
	float speed;
};

