#pragma once
#include "BMP_Loader.h"
#include <GL/glew.h>
class TextureInfo
{
public:
	BMPFormat* image;
	GLuint textureID;
	void init(const char* filename, GLuint ID);
	TextureInfo();
	void Shutdown();
	~TextureInfo();
};

