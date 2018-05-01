#include "ShaderInfo.h"

void ShaderInfo::setID(GLuint ID)
{
	programID = ID;
}

ShaderInfo::ShaderInfo()
{
}


ShaderInfo::~ShaderInfo()
{
}

Callback ShaderInfo::GetCallback()
{
	return BeforeDrawing;
}

void ShaderInfo::SetCallback(Callback function)
{
	BeforeDrawing = function;
}


