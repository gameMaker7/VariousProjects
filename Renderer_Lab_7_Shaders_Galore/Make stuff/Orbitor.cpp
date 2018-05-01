#include "Orbitor.h"
#include <iostream>
#include <gtc/matrix_transform.inl>


void Orbitor::Update(float dt)
{
	move(dt);
}

void Orbitor::move(float dt)
{
	rotation.y += rotation_speed*dt;
	glm::mat3 rotate = glm::mat3(glm::rotate(glm::mat4(), glm::radians(rotation_speed), glm::vec3(0, 1, 0)));
	view_direction = rotate*view_direction;
	float lock = position.y;
	position += Speed_Current*view_direction*dt;
	position.y = lock;

}


void Orbitor::setCenter(glm::vec3 center)
{
	center_of_circle = center;
}

Orbitor::Orbitor()
{
}


Orbitor::~Orbitor()
{
}
