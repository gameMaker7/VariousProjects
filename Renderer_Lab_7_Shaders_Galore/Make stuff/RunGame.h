#ifndef RUNGAME_H
#define RUNGAME_H

#pragma warning(push)
#pragma warning(disable:4127)
#pragma warning(disable:4251)
#include <QtGui/qmouseEvent>
#pragma warning(pop)
#include "ShapeGen.h"
#include "Camera.h"
#include "DT.h"
#include "..\Render Engine\Renderable.h"
#include "Light.h"
#include "Datamanager.h"
#include "Player.h"
#include "KeyboardController.h"
#include "RandomWalker.h"
#include "Stalker_Walker.h"
#include "Orbitor.h"
#define MAXOBJECTS 25
#define MAXPLAYERS 5

class GLWindow;
class RunGame
{
public:
	bool Initialize(GLWindow*);
	bool Shutdown();
	void Update();
	void changePlayer();
	bool ProcessKeys(float dt); // – handles keystrokes
	void ProcessMouse(QMouseEvent* e); // – handles mouse movement
	void Draw();
	void BuildShapes(); // creates any objects the game uses
	void Pause();
	void installShaders();
	void RoamingCamera();
	Datamanager* getModel();

	DT dt_timer;
	Camera* view;
	Geometry DragonInfo;
	Geometry CubePT;
	Geometry jolteon;
	Renderable objects[MAXOBJECTS];
	Light lightBulb;
	Datamanager model;
	Player players[MAXPLAYERS];
	GLWindow* window;
	KeyboardController player_controller;
	RandomWalker random_Mover;
	RandomWalker random_Mover2;
	Stalker_Walker searcher;
	bool CPP;
	bool pauseState;
	bool BodySwap;
	bool FreeCamera;
	bool cameraJump;
	int currentPlayer = 1;
	GLint WorldPositionLocation;
	GLint uniformPosLocation;
	GLuint NoLightTexture;
	GLuint NoLightTinted;
	GLuint LightShader;
}; 
#endif

