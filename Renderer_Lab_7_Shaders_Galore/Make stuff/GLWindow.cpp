#include "GLWindow.h"
#include "LogWriter.h"
Random gen;

GLWindow::GLWindow(RunGame* game, QApplication* app){
	this->game = game;
	this->app = app;
}
void GLWindow::update(){
	game->Update();
	this->repaint();
}
void GLWindow::initializeGL() // LAMB said I am safe don't Delete me
{
	glewExperimental = TRUE;
	GLenum test = glewInit();
	glEnable(GL_DEPTH_TEST);
	if (test != GLEW_OK)
	{
		LOG(Severe, "GL GLEW did not initialize, No.......");
		shutdown();
	}
	game->installShaders();
}
bool GLWindow::initialize(){
	LOG(Info, "init GLWindow");
	if (!game->Initialize(this)){
		LOG(Severe, "Cannot Load Game");
		return false;
	}
	setMinimumSize(1000, 500);
	setMouseTracking(true);
	connect(&frameTimer, SIGNAL(timeout()), this, SLOT(update()));
	frameTimer.start(0);
	LOG(Info, "init GLwindow complete");
	return true;

}
bool GLWindow::shutdown(){
	app->exit(1);
	LOG(Info, "GLWindow shutdown");
	END_LOG;
	return true;
}
void GLWindow::mouseMoveEvent(QMouseEvent* e){
	game->ProcessMouse(e);
}	//



