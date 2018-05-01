#ifndef KEYBOARD_CONTROLLER_H
#define KEYBOARD_CONTROLLER_H
#include <Windows.h>
#include "MovementController.h"


class KeyboardController : public MovementController
{
public:
	glm::vec3 getUP();
	void TurnLeft(float dt);
	void TurnRight(float dt);
	void MoveForward(float dt);
	void MoveBackward(float dt);
	void StrafeLeft(float dt);
	void StrafeRight(float dt);
	void MoveUp(float dt);
	void Jump(float dt);
	void MoveDown(float dt);
	void checkMovementKeys(float dt);
	void Update(float dt);
	void mouseUpdate(const glm::vec2 &mouseDelta);
	void setStrafeDirection(glm::vec3 direction = glm::vec3(0, 0, 0));
	void setUP(glm::vec3 UP = glm::vec3(0, 0, 0));
	void setGravity(bool grav = true);
	KeyboardController();
protected:
	glm::vec3 UP;
	glm::vec2 oldMousePos;
	CollisionCheck collisionCallBack;
	bool gravity;
	bool CPP;
	char turnLeft = 'Q';
	char turnRight = 'E';
	char moveForward = 'W';
	char moveBackward = 'S';
	char strafeLeft = 'A';
	char strafeRight = 'D';
	char moveUp = 'R';
	char moveDown = 'F';
	char jump = VK_SPACE;
};

#endif