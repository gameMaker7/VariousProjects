#include "MovementController.h"
#include <iostream>


void MovementController::Update(float dt)
{
	dt;
}

glm::vec3 MovementController::getPosition()
{

	return position;
}
void MovementController::setRotation_speed(float rotationalSpeed)
{
	rotation_speed = rotationalSpeed;
}
glm::vec3 MovementController::getDirection()
{
	return view_direction;
}

void MovementController::LawsOfGravity(float dt)
{
	glm::vec3 oldPos = position;
	//std::cout << position.y << "\n" << std::endl;
	position += -glm::vec3(0, 9.8, 0)*dt;
	if (CollisionCallback(position)){
		position = oldPos;
	}
}
void MovementController::setCollisonCallback(CollisionCheck newFunction){

	CollisionCallback = newFunction;
}


bool MovementController::init(glm::vec3 direction, glm::vec3 position, float rotation_speed, glm::vec3 Speed_Current, glm::vec3 Max_Speed)
{
	this->view_direction = direction;
	this->position = position;
	this->rotation_speed = rotation_speed;
	this->Speed_Current = Speed_Current;
	this->Max_Speed = Max_Speed;
	return true;
}

MovementController::MovementController()
{
}

MovementController::~MovementController()
{
}

void MovementController::AxisLocks(bool x, bool y, bool z)
{
	locked_X_axis = x;
	locked_Y_axis = y;
	locked_Z_axis = z;
}

void MovementController::setDirection(glm::vec3 direction)
{
	this->view_direction = direction;
}

glm::vec3 MovementController::getRotation()
{
	return rotation;
}

glm::vec3 MovementController::lerp(const glm::vec3 current, const glm::vec3 destination, const float scaler)
{
	glm::vec3 check = glm::vec3((1 - scaler) * current) + (scaler * destination);
	return check;
}

void MovementController::setRotation(glm::vec3 rotation)
{
	this->rotation = rotation;
}

void MovementController::setPosition(glm::vec3 position)
{
	this->position = position;
}

glm::vec3 MovementController::getUP()
{
	return glm::vec3(0, 1, 0);
}