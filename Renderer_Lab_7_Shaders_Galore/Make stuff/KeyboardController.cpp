#include "KeyboardController.h"
#include <gtc/matrix_transform.inl>
#include <iostream>
glm::vec3 KeyboardController::getUP()
{
	return UP;
}
void KeyboardController::MoveForward(float dt)
{
	position += (Speed_Current * view_direction)*dt;
}

void KeyboardController::MoveBackward(float dt)
{
	position += (-Speed_Current * view_direction)*dt;
}

void KeyboardController::TurnLeft(float dt)
{
	rotation.y += rotation_speed*dt;
}

void KeyboardController::TurnRight(float dt)
{
	rotation.y += -rotation_speed*dt;
}


void KeyboardController::StrafeLeft(float dt)
{
	position += (-Speed_Current.z * strafe_direction)*dt;
}

void KeyboardController::StrafeRight(float dt)
{
	position += (Speed_Current.z * strafe_direction)*dt;
}

void KeyboardController::MoveUp(float dt)
{
	if (locked_Y_axis)
	{
		return;
	}
	position += (Speed_Current.y * UP)*dt;
}

void KeyboardController::Jump(float dt)
{
	if (!CPP){
		CPP = true;
		position += (Speed_Current.y*50 * UP)*dt;
		std::cout << position.y << "\n" << std::endl;
	}
}

void KeyboardController::MoveDown(float dt)
{
	if (locked_Y_axis){ return; }
	position += (-Speed_Current.y * UP)*dt;

}

void KeyboardController::checkMovementKeys(float dt)
{
	float y = 0;
	if (GetAsyncKeyState(jump)) Jump(dt);
	if (!GetAsyncKeyState(jump)) CPP = false;
	if (locked_Y_axis) { y = position.y; }
	if (GetAsyncKeyState(turnLeft)) TurnLeft(dt);
	if (GetAsyncKeyState(turnRight)) TurnRight(dt);
	if (GetAsyncKeyState(moveForward)) MoveForward(dt);
	if (GetAsyncKeyState(moveBackward)) MoveBackward(dt);
	if (GetAsyncKeyState(strafeLeft)) StrafeLeft(dt);
	if (GetAsyncKeyState(strafeRight)) StrafeRight(dt);
	if (GetAsyncKeyState(moveUp)) MoveUp(dt);
	if (GetAsyncKeyState(moveDown)) MoveDown(dt);
	if (locked_Y_axis) { position.y = y; }

}

void KeyboardController::Update(float dt)
{
	glm::vec3 oldPos = position;
	checkMovementKeys(dt);
	if (CollisionCallback(position)){
		position = oldPos;
	}
	if (gravity){
		LawsOfGravity(dt);
	}

}

void KeyboardController::mouseUpdate(const glm::vec2 &mouseDelta){
	glm::vec2 mousedelta = mouseDelta - oldMousePos;
	if (glm::length(mousedelta) > 50.0f){
		oldMousePos = mouseDelta;
		return;
	}
	else{
		const float rotationSpeed = 0.005f;
		strafe_direction = glm::cross(view_direction, UP);
		view_direction = glm::mat3(glm::rotate(glm::mat4(), mousedelta.x*rotationSpeed, UP)*
			glm::rotate(glm::mat4(), mousedelta.y*rotationSpeed, strafe_direction)) * view_direction;
	}
	oldMousePos = mouseDelta;
}

void KeyboardController::setStrafeDirection(glm::vec3 direction)
{
	strafe_direction = direction;
}

void KeyboardController::setUP(glm::vec3 UP)
{
	this->UP = UP;
}

void KeyboardController::setGravity(bool grav)
{
	this->gravity = grav;
}

KeyboardController::KeyboardController()
{
	UP = glm::vec3(0, 1, 0);
}

