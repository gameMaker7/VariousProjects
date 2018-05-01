#ifndef ORBIT_CONTROLLER_H
#define ORBIT_CONTROLLER_H
#include "MovementController.h"

class Orbitor :
	public MovementController
{
public:
	void Update(float dt);
	void move(float dt);
	void setCenter(glm::vec3 center);

	Orbitor();
	~Orbitor();

protected:
	float speed; 
	glm::vec3 last_Position;
	glm::vec3 center_of_circle;
};

#endif
