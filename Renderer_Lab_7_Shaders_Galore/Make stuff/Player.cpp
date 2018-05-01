#include "Player.h"
#include <iostream>


void Player::setShape(Renderable* shape)
{
	this->shape = shape;
	current_controller->setRotation(shape->getRotation());
	current_controller->setPosition(shape->getPosition());
}

void Player::setPosition(glm::vec3 newPosition)
{
	if (shape != nullptr)
	{
		shape->setPosition(newPosition);
	}
	if (view != nullptr){ view->setPosition(newPosition, offset_for_camera_view); }
	position = newPosition;
}

glm::vec3 Player::getRotation()
{
	return current_controller->getRotation();
}

glm::vec3 Player::getUP()
{
	return current_controller->getUP();
}

void Player::setRotation(glm::vec3 rotation)
{
	if (shape != nullptr)
	{
		shape->setRotation(rotation);
	}
}

void Player::setCameraValues(glm::vec3 position, glm::vec3 direction)
{
	view->setPosition(position, offset_for_camera_view);
	view->setViewDirection(direction);
}

void Player::setDirection(glm::vec3 newDirection)
{
	if (view != nullptr){ view->setViewDirection(newDirection); }
	direction = newDirection;
}
void Player::Update(float dt)
{

	if (current_controller != nullptr)
	{
		current_controller->Update(dt);
	}
	if (current_controller != nullptr)
	{
		setPosition(getPosition());
	}
	if (current_controller != nullptr)
	{
		setDirection(getDirection());
	}
	if (view != nullptr && current_controller!= nullptr)
	{
		setCameraValues(getPosition(), getDirection());
	}
	if (shape != nullptr)
	{
		shape->setRotation(getRotation());
		shape->update(dt);
	}
}
glm::vec3 Player::getPosition()
{
	return current_controller->getPosition();
}
glm::vec3 Player::getDirection()
{
	return current_controller->getDirection();
}
void Player::setController(MovementController* newController)
{
	if (newController == nullptr)
	{
		current_controller = last_controller;
		last_controller = nullptr;
	}
	else
	{
		last_controller = current_controller;
		current_controller = newController;
		current_controller->AxisLocks(locked_X_axis, locked_Y_axis, locked_Z_axis);
	}
	setControllerValues();
}

void Player::axisLocks(bool x, bool y, bool z)
{
	locked_Y_axis = x;
	locked_X_axis = y;
	locked_Z_axis = z;
}

Player::Player(glm::vec3 offset)
{
	offset_for_camera_view = offset;
	current_controller = new MovementController;
	current_controller->init(glm::vec3(.1, .1, .1));
	last_controller = new MovementController;
	last_controller->init(glm::vec3(.1, .1, .1));
	shape = nullptr;
	view = new Camera;
}
Player::~Player()
{
}

void Player::setOffset(glm::vec3 offset)
{
	offset_for_camera_view = offset;
}

glm::vec3 Player::getPlayerPosition()
{
	return position;
}


void Player::setControllerValues()
{
	current_controller->setPosition(position);
	current_controller->setDirection(direction);
	if (shape != nullptr){ current_controller->setRotation(shape->getRotation()); }
}

glm::vec3 Player::getPlayerDirection()
{
	return direction;
}

Camera* Player::getCamera()
{
	return view;
}

bool Player::Sutdown()
{
	delete shape;
	delete current_controller;
	delete last_controller;
	delete view;
	return true;
}