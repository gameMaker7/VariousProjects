#ifndef VERTEX_SHADER_INFO_H
#define VERTEX_SHADER_INFO_H
#include <GL/glew.h>
class Renderable;
typedef void(*Callback)(Renderable*);

class ShaderInfo
{
public:
	void setID(GLuint ID);
	void SetCallback(Callback function);
	ShaderInfo();
	~ShaderInfo();
	Callback GetCallback();
	GLuint programID;
	Callback BeforeDrawing;

};

#endif
