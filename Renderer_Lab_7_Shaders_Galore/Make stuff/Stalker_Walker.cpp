#include "Stalker_Walker.h"
#include "Player.h"

void Stalker_Walker::checkPosition(float dt)
{
	if (target->getPlayerPosition() == position)
	{
	}
	else
	{
		position = lerp(position, target->getPlayerPosition(), (speed*dt));
		speed += .001f;
	}
}


void Stalker_Walker::Update(float dt)
{
	checkPosition(dt);
	glm::vec3 oldPos = position;
	if (CollisionCallback(position)){
		position = oldPos;
	}
		LawsOfGravity(dt);


}

void Stalker_Walker::setDestination(Player* newTarget)
{
	speed = 0.001f;
	target = newTarget;
}

Stalker_Walker::Stalker_Walker()
{
}


Stalker_Walker::~Stalker_Walker()
{
}

glm::vec3 Stalker_Walker::destinationPosition()
{
	return target->getPlayerPosition();
}