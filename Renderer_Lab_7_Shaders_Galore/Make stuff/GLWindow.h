#ifndef GLWINDOW_H
#define GLWINDOW_H
#include "RunGame.h"
#include "Qt\qapplication.h"
#include <Qt\qtimer.h>
#pragma warning(push)
#pragma warning(disable : 4201)
#pragma warning(disable : 4251)
#pragma warning(disable : 4800)
#pragma warning(disable : 4127)
#include <QtOpenGL\qglwidget>
#pragma warning(pop)
class RunGame;
class GLWindow : public QGLWidget
{
	Q_OBJECT
		QTimer frameTimer; 


protected:
	void initializeGL();
	void mouseMoveEvent(QMouseEvent* e);
	private slots:
	void update();

public:
	GLWindow(RunGame*, QApplication*); 
	bool initialize();
	bool shutdown();
private:
	QApplication* app;
	RunGame* game;

};

#endif