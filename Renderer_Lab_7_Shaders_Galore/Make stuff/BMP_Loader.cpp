#include "BMP_Loader.h"
#include <forward_list>


BMPFormat* BMP_Loader::loadBMP_custom(const char* imagepath, BMPFormat* ret)
{
	char fileName[80];
	strcpy_s(fileName, "..\\Data\\Textures\\");
	strcat_s(fileName, imagepath);
	strcat_s(fileName, ".bmp");
	// Open the file
	FILE * file;
	fopen_s(&file, fileName, "rb");
	if (!file)     { printf("Image could not be opened\n"); return nullptr; }

	if (fread(ret->header, 1, 54, file) != 54){ // If not 54 bytes read : problem
		printf("Not a correct BMP file\n");
		return nullptr;
	}
	if (ret->header[0] != 'B' || ret->header[1] != 'M'){
		printf("Not a correct BMP file\n");
		return nullptr;
	}
	// Read ints from the byte array
	ret->dataPos = *(int*)&(ret->header[0x0A]);
	ret->imageSize = *(int*)&(ret->header[0x22]);
	ret->width = *(int*)&(ret->header[0x12]);
	ret->height = *(int*)&(ret->header[0x16]);

	// Some BMP files are misformatted, guess missing information
	if (ret->imageSize == 0)    ret->imageSize = ret->width*ret->height * 3; // 3 : one byte for each Red, Green and Blue component
	if (ret->dataPos == 0)      ret->dataPos = 54; // The BMP header is done that way

	// Create a buffer
	ret->data = new unsigned char[ret->imageSize];

	// Read the actual data from the file into the buffer
	fread(ret->data, 1, ret->imageSize, file);

	//Everything is in memory now, the file can be closed
	fclose(file);
	return ret;
}

BMP_Loader::BMP_Loader()
{
}


BMP_Loader::~BMP_Loader()
{
}
