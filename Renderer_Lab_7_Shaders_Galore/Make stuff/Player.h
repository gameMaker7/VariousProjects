#ifndef PLAYER_H
#define PLAYER_H
#include "../Render Engine/Renderable.h"
#include "Camera.h"
#include "MovementController.h"

class Player
{
public:
	void setShape(Renderable* shape);
	void setPosition(glm::vec3 newPosition);
	glm::vec3 getRotation();
	void setDirection(glm::vec3 newDirection);
	glm::vec3 getUP();
	void setCameraValues(glm::vec3 position, glm::vec3 direction);
	void setRotation(glm::vec3 rotation);
	void Update(float dt);
	glm::vec3 getPosition(); 
	glm::vec3 getDirection();
	void setController(MovementController* newController = nullptr); 
	void axisLocks(bool x = true, bool y = false, bool z = false);
	Player(glm::vec3 offset = glm::vec3(0, 0, 0));
	~Player();
	bool Sutdown();
	Camera* getCamera();
	void setOffset(glm::vec3 tvec3);
	glm::vec3 getPlayerPosition();
	glm::vec3 getPlayerDirection();
	void setControllerValues();
protected:
	glm::vec3 offset_for_camera_view;
	boundingBox boundry;
	MovementController* last_controller;
	MovementController* current_controller;
	Renderable* shape;
	Camera* view;  
	glm::vec3 position;
	glm::vec3 direction;
	bool locked_Y_axis = true;
	bool locked_X_axis = false;
	bool locked_Z_axis = false;
};

#endif
