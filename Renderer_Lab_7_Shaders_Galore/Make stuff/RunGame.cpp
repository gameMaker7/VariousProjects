#include "RunGame.h"
#include "GLWindow.h"
#include "LogWriter.h"
#include "../Render Engine/RenderEngine.h"
#include "FileWriter.h"
#include "..\glm/gtc/matrix_transform.hpp"
#include <ctime>

Light* LightuniformPtr;
Camera* cameraUinformPtr;
Datamanager* ModelUniformPtr;
boundingBox boundries[MAXOBJECTS];

void BeforeDrawCallbackTextures(Renderable* object)
{
	GLint fullTransformMatrixUniformLocation = glGetUniformLocation(object->m_ShaderInfo.programID, "ModeltoProjectionMatrix");
	glUniformMatrix4fv(fullTransformMatrixUniformLocation, 1, GL_FALSE, &(object->FinalTransform)[0][0]);
}

void BeforeDrawCallbackLighting(Renderable* object)
{
	GLint fullTransformMatrixUniformLocation = glGetUniformLocation(object->m_ShaderInfo.programID, "ModeltoProjectionMatrix");
	glUniformMatrix4fv(fullTransformMatrixUniformLocation, 1, GL_FALSE, &(object->FinalTransform)[0][0]);

	GLuint WorldLightPosition = glGetUniformLocation(object->m_ShaderInfo.programID, "WorldLightPosition");
	glUniform3fv(WorldLightPosition, 1, &(LightuniformPtr->LightPosition)[0]);

	GLuint WorldCameraPosition = glGetUniformLocation(object->m_ShaderInfo.programID, "WorldCameraPosition");
	glUniform3fv(WorldCameraPosition, 1, &(cameraUinformPtr->position)[0]);

	GLuint intensityOfDiffuseLight = glGetUniformLocation(object->m_ShaderInfo.programID, "intensityOfDiffuseLight");
	glUniform3fv(intensityOfDiffuseLight, 1, &(ModelUniformPtr->diffuseLight)[0]);

	GLuint intensityOfSpecularLight = glGetUniformLocation(object->m_ShaderInfo.programID, "intensityOfSpecularLight");
	glUniform3fv(intensityOfSpecularLight, 1, &(ModelUniformPtr->SpecularColor)[0]);

	GLuint SpecExponent = glGetUniformLocation(object->m_ShaderInfo.programID, "SpecExponent");
	glUniform1f(SpecExponent, ModelUniformPtr->SpecExponent);

	GLuint ambientLight = glGetUniformLocation(object->m_ShaderInfo.programID, "ambientLight");
	glUniform3fv(ambientLight, 1, &(ModelUniformPtr->ambientLight)[0]);

	GLuint WorldMatrix = glGetUniformLocation(object->m_ShaderInfo.programID, "WorldMatrix");
	glUniformMatrix4fv(WorldMatrix, 1, GL_FALSE, &(object->transform)[0][0]);
}

void BeforeDrawCallbackTintedTextures(Renderable* object)
{
	GLint fullTransformMatrixUniformLocation = glGetUniformLocation(object->m_ShaderInfo.programID, "ModeltoProjectionMatrix");
	glUniformMatrix4fv(fullTransformMatrixUniformLocation, 1, GL_FALSE, &(object->FinalTransform)[0][0]);

	//GLint ColorTint = glGetUniformLocation(object->m_ShaderInfo.programID, "Tint");
	//glUniform3fv(ColorTint, 1, GL_FALSE, &(object->FinalTransform)[0][0]);

}

bool CollisionCheckFunction(glm::vec3 position){

	for (int j = 0; j < MAXOBJECTS; ++j){
		if (boundries[j].checkCollisionOutside(position)){
			return true;
		}
	}
	return false;
}

bool RunGame::Initialize(GLWindow* window){
	LOG(Info, "init game");
	this->window = window;
	ConfigInfo.initialize("..\\Data\\Config.txt", ConfigInfo);
	srand((unsigned int)time(NULL));
	dt_timer.init();
	RenderEngine::Initialize();
	cameraJump = false;
	return true;
}
bool RunGame::Shutdown(){
	dt_timer.shutDown();
	RenderEngine::Shutdown();
	window->shutdown();
	LOG(Info, "Game shutdown");
	return true;
}
void RunGame::Update(){
	dt_timer.newFrame();
	float dt = dt_timer.timeOfLastFrame();
	if (!ProcessKeys(dt)){
		Shutdown();
		return;
	}
	for (int j = 0; j < MAXOBJECTS; ++j)
	{
		if (!pauseState){ objects[j].update(dt); }
	}
	for (int j = 0; j < MAXPLAYERS; ++j)
	{
		players[j].Update(dt);
	}
	while (searcher.getPosition() == searcher.destinationPosition())
	{
		searcher.setDestination(&players[gen.randInt(1, 4)]);
	}
		lightBulb.Update(dt, model.ambientLight, model.diffuseLight);

	Draw();
	}
void RunGame::changePlayer()
{
	if (!cameraJump && !BodySwap)
	{
		if (currentPlayer >= MAXPLAYERS-1)
		{
			players[currentPlayer].setController();
			currentPlayer = 1;
			players[currentPlayer].setController(&player_controller);
		}
		else
		{
			++currentPlayer;
			players[currentPlayer].setController(&player_controller);
			players[currentPlayer - 1].setController();
		}
	
		view = players[currentPlayer].getCamera();
		
		printf("Player: %d ", currentPlayer);
		BodySwap = true;
	}
	if (cameraJump && !BodySwap)
	{
		players[currentPlayer].setPosition(players[0].getPlayerPosition());
		
		players[currentPlayer].setController(&player_controller);
		players[currentPlayer].setControllerValues();
		view = players[currentPlayer].getCamera();
		players[0].setControllerValues();
		printf("Return to controller");
		BodySwap = true;
		cameraJump = false;
	}

}
bool RunGame::ProcessKeys(float dt){
	if (!GetAsyncKeyState('P')){
		CPP = false;
	}
	if (GetAsyncKeyState('X')){
		return false;
	}
	if (GetAsyncKeyState('P')){
		Pause();
	}
	if (!GetAsyncKeyState('G')){
		FreeCamera = false;
	}
	if (GetAsyncKeyState('G')){
		RoamingCamera();
	}

	if (GetAsyncKeyState('J'))
	{
		changePlayer();
	}
	if (!GetAsyncKeyState('J'))
	{
		BodySwap = false;
	}
	dt;
	return true;
}
void RunGame::ProcessMouse(QMouseEvent* e){
	if (e->buttons() & Qt::RightButton){
		player_controller.mouseUpdate(glm::vec2(e->x(), e->y()));
	}
}
void RunGame::BuildShapes(){
	glClearColor(0.0, 0.0, 0.0, 0.0);
	DragonInfo = ShapeGen().CreateScene("AeroVeedramon");
	jolteon = ShapeGen().CreateScene("jolteon");
	CubePT = ShapeGen().makeCube();
	objects[1].info = &jolteon;
	RenderEngine::AddRenderInfo(&objects[1]);
	objects[1].setTextureInfo("jolteon", 2);
	objects[1].setRotation(glm::vec3(0.0, 0.0, 0.0));
	objects[1].setPosition(glm::vec3(0, 15, 0));
	objects[1].setScale(glm::vec3(0.01f, 0.01f, 0.01f));
	objects[1].setShaders(LightShader);
	objects[1].SetCallback(BeforeDrawCallbackLighting);

	objects[2].info = &jolteon;
	RenderEngine::AddRenderInfo(&objects[2]);
	objects[2].setTextureInfo("jolteon", 2);
	objects[2].setRotation(glm::vec3(0.0, 0.0, 0.0));
	objects[2].setPosition(glm::vec3(0, 5, 0));
	objects[2].setScale(glm::vec3(0.01f, 0.01f, 0.01f));
	objects[2].setShaders(LightShader);
	objects[2].SetCallback(BeforeDrawCallbackLighting);

	objects[3].info = &jolteon;
	RenderEngine::AddRenderInfo(&objects[3]);
	objects[3].setTextureInfo("jolteon", 2);
	objects[3].setRotation(glm::vec3(0.0, 0.0, 0.0));
	objects[3].setPosition(glm::vec3(0, 5, 0));
	objects[3].setScale(glm::vec3(0.01f, 0.01f, 0.01f));
	objects[3].setShaders(LightShader);
	objects[3].SetCallback(BeforeDrawCallbackLighting);

	objects[4].info = &jolteon;
	RenderEngine::AddRenderInfo(&objects[4]);
	objects[4].setTextureInfo("jolteon", 2);
	objects[4].setRotation(glm::vec3(0.0, 0.0, 0.0));
	objects[4].setPosition(glm::vec3(0, 5, 0));
	objects[4].setScale(glm::vec3(0.01f, 0.01f, 0.01f));
	objects[4].setShaders(LightShader);
	objects[4].SetCallback(BeforeDrawCallbackLighting);


	
	objects[5].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[5]);
	objects[5].setShaders(LightShader);
	objects[5].SetCallback(BeforeDrawCallbackLighting);
	objects[5].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[5].setPosition(glm::vec3(0, -15, 0));
	objects[5].setScale(glm::vec3(15, 15, 15));

	objects[6].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[6]);
	objects[6].setShaders(LightShader);
	objects[6].SetCallback(BeforeDrawCallbackLighting);
	objects[6].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[6].setPosition(glm::vec3(0, 0, 0));

	objects[7].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[7]);
	objects[7].setShaders(LightShader);
	objects[7].SetCallback(BeforeDrawCallbackLighting);
	objects[7].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[7].setPosition(glm::vec3(-2, 1, 0));

	objects[8].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[8]);
	objects[8].setShaders(LightShader);
	objects[8].SetCallback(BeforeDrawCallbackLighting);
	objects[8].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[8].setPosition(glm::vec3(-2, 0, 4));


	objects[9].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[9]);
	objects[9].setShaders(LightShader);
	objects[9].SetCallback(BeforeDrawCallbackLighting);
	objects[9].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[9].setPosition(glm::vec3(-2, 2, 4));


	objects[10].info = &CubePT;
	RenderEngine::AddRenderInfo(&objects[10]);
	objects[10].setShaders(LightShader);
	objects[10].SetCallback(BeforeDrawCallbackLighting);
	objects[10].setRotation(glm::vec3(0, 0.01, 0.0));
	objects[10].setPosition(glm::vec3(-2, 2, 2));

	players[0].axisLocks(false);

	players[1].setShape(&objects[1]);
	players[1].setOffset(glm::vec3(0, 1, 0));
	players[1].setController(&player_controller);


	players[2].setShape(&objects[2]);
	players[2].setOffset(glm::vec3(3.66580462, 3.77695656, 1.84409487));
	players[2].setController(&random_Mover);

	players[3].setShape(&objects[3]);
	players[3].setOffset(glm::vec3(3.66580462, 3.77695656, 1.84409487));
	players[3].setController(&random_Mover2);

	players[4].setShape(&objects[4]);
	players[4].setOffset(glm::vec3(3.66580462, 3.77695656, 1.84409487));
	players[4].setController(&searcher);

	player_controller.init(glm::vec3(0.0202393848, -0.0617476925, 0.160553426), glm::vec3(-0.407811731, 5.00144315, -1.08663762), 1);
	player_controller.setCollisonCallback(CollisionCheckFunction);
	player_controller.setGravity();

	random_Mover.setCenter(glm::vec3(0, 0, 0));
	random_Mover.setDirection(glm::vec3(-0.139055893, -0.0681694821, -0.0775642917));
	random_Mover.setPosition(glm::vec3(0, 5, 0));
	random_Mover.setCollisonCallback(CollisionCheckFunction);

	random_Mover2.setCenter(glm::vec3(0, 0, 0));
	random_Mover2.setDirection(glm::vec3(-0.139055893, -0.0681694821, -0.0775642917));
	random_Mover2.setCollisonCallback(CollisionCheckFunction);
	random_Mover2.setPosition(glm::vec3(0, 5, 0));

	searcher.setDestination(&players[2]);
	searcher.setDirection(glm::vec3(-0.139055893, -0.0681694821, -0.0775642917));
	searcher.setCollisonCallback(CollisionCheckFunction);
	searcher.setPosition(glm::vec3(0, 5, 0));

	view = players[1].getCamera();
	cameraUinformPtr = view;

	boundries[0] = boundingBox(glm::vec3(-15, -30, -15), glm::vec3(15, 0, 15));
	boundries[1] = boundingBox(glm::vec3(-1, 0, -1), glm::vec3(1, 1, 1));
	boundries[2] = boundingBox(glm::vec3(-3, 0, -1), glm::vec3(-1, 2, 1));
	boundries[3] = boundingBox(glm::vec3(-3, 0, 3), glm::vec3(-1, 1, 5));
	boundries[4] = boundingBox(glm::vec3(-3, 1, 3), glm::vec3(-1, 3, 5));
	boundries[5] = boundingBox(glm::vec3(-3, 1, 1), glm::vec3(-1, 3, 3));


	LOG(Info, "Build shapes Complete");
}
void RunGame::installShaders(){
	LOG(Info, "install shaders start");
	NoLightTexture = RenderEngine::AddShader("NoLighting_Texture");
	LightShader = RenderEngine::AddShader("Lighting");
	lightBulb = Light(glm::vec3(0.1f, 0.1f, 0.1f));
	ModelUniformPtr = &model;
	LightuniformPtr = &lightBulb;
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	BuildShapes();
	LOG(Info, "install Shaders complete");
}
void RunGame::RoamingCamera()
{
	if (cameraJump && !FreeCamera){

		players[0].setController();
		players[currentPlayer].setController(&player_controller);
		view = players[currentPlayer].getCamera();
		FreeCamera = true;
		cameraJump = false;
		printf("Player: %d ", currentPlayer);
	}

	if (!cameraJump && !FreeCamera){

		players[currentPlayer].setController();
		players[0].setController(&player_controller);
		view = players[0].getCamera();
		printf("Free camera");
		FreeCamera = true;
		cameraJump = true;
	}
}
Datamanager* RunGame::getModel()
{
	return &model;
}
void RunGame::Draw(){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glViewport(0, 0, window->width(), window->height());
	glm::mat4 project = glm::perspective(glm::radians(60.0f), ((float)window->width()) / window->height(), 0.1f, 2000.0f);
	glm::mat4 worldMat = view->getWorldToViewMatrix();
	RenderEngine::Draw(project, worldMat);
	}
void RunGame::Pause(){
	if (pauseState && !CPP){
		pauseState = false;
		CPP = true;
		std::cout << pauseState << std::endl;
		LOG(Info, "UnPause");
	}
	if (!pauseState && !CPP){
		pauseState = true;
		CPP = true;
		std::cout << pauseState << std::endl;
		LOG(Info, "Paused");
	}
};