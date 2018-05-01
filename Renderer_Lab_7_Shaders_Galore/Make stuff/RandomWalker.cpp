#include "RandomWalker.h"
#include "Random.h"
extern Random gen;


void RandomWalker::checkPosition(float dt)
{
	if (destination == position)
	{
		setRandomDestination(center);
		speed = 0.001f;
	} else
	{
		position = lerp(position, destination, (speed*dt));
		speed += .001f;
	}
}

void RandomWalker::setCenter(glm::vec3 center)
{
	this->center = center;
}

void RandomWalker::Update(float dt)
{
	glm::vec3 oldPos = position;
	checkPosition(dt);
	if (CollisionCallback(position)){
		position = oldPos;
		setRandomDestination(center);
	}

}

void RandomWalker::setRandomDestination(glm::vec3 center)
{
	float locked = destination.y;
	this->center = center;
	destination = gen.randPosition(center);
	destination.y = locked;
}

RandomWalker::RandomWalker()
{
	speed = 0.05f;
}

