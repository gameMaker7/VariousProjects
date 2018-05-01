#ifndef MOVEMENT_CONTROLLER_H
#define MOVEMENT_CONTROLLER_H
#pragma warning(push)
#pragma warning(disable : 4201)
#include "glm.hpp"
#pragma warning(pop)

typedef bool(*CollisionCheck)(glm::vec3);

class MovementController
{
public:
	glm::vec3 virtual getUP();
	virtual void Update(float dt);
	glm::vec3 getPosition();
	glm::vec3 getDirection();
	void setRotation_speed(float rotationalSpeed);
	void LawsOfGravity(float dt);
	void setCollisonCallback(CollisionCheck);
	virtual bool init(
		glm::vec3 direction,
		glm::vec3 position = glm::vec3(0, 0, 0),
		float rotation_speed = 0.00f,
		glm::vec3 Speed_Current = glm::vec3(15, 15, 15),
		glm::vec3 Max_Speed = glm::vec3(20, 20, 20));
	MovementController();
	virtual ~MovementController();
	virtual void AxisLocks(bool x, bool y, bool z);
	void setPosition(glm::vec3 position);
	void setDirection(glm::vec3 direction);
	glm::vec3 getRotation();
	glm::vec3 lerp(const glm::vec3 current, const glm::vec3 destination, const float scaler);
	void setRotation(glm::vec3 rotation);
protected:
	CollisionCheck CollisionCallback;
	glm::vec3 rotation;
	glm::vec3 view_direction;
	glm::vec3 position;
	glm::vec3 strafe_direction;
	glm::vec3 Speed_Current;
	glm::vec3 Max_Speed;
	float rotation_speed;
	bool locked_Y_axis = true;
	bool locked_X_axis = false;
	bool locked_Z_axis = false;
};

#endif