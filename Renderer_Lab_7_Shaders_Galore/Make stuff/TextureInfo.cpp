#include "TextureInfo.h"


void TextureInfo::init(const char* filename, GLuint ID)
{
	textureID = ID;
	image = BMP_Loader().loadBMP_custom(filename, image);
	// Create one OpenGL texture
	glGenTextures(1, &textureID);

	// "Bind" the newly created texture : all future texture functions will modify this texture
	glBindTexture(GL_TEXTURE_2D, textureID);

	// Give the image to OpenGL
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image->width, image->height, 0, GL_BGR, GL_UNSIGNED_BYTE, image->data);

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);


}

TextureInfo::TextureInfo()
{
	image = new BMPFormat;
}

void TextureInfo::Shutdown()
{
	delete image;
}

TextureInfo::~TextureInfo()
{
}
