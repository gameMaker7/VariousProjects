#ifndef STALKER_WALKER_H
#define STALKER_WALKER_H
#include "MovementController.h"

class Player;

class Stalker_Walker : public MovementController
{
public:
	bool needNewTarget;
	void checkPosition(float dt);
	void Update(float dt);
	void setDestination(Player* newTarget);
	Stalker_Walker();
	~Stalker_Walker();
	glm::vec3 destinationPosition();
protected:
	Player* target;
	float speed;
};

#endif
