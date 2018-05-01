
#include <GL/glew.h>
#include "Widget.h" 
#include "LogWriter.h"
#include "RunGame.h"
int main(int argc, char** argv)
{
	LOG(Info, "Begin Log");
	QApplication app(argc, argv);
	RunGame game;
	GLWindow *window = new GLWindow(&game, &app);
	Widget myWidget(game.getModel(), window);
	game.model = myWidget.model;
	myWidget.showFullScreen(); // or just .show()
	return app.exec();
}