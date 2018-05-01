#ifndef  BMP_LOADER_H
#define BMP_LOADER_H

struct BMPFormat
{
	unsigned char header[54]; // Each BMP file begins by a 54-bytes header
	unsigned int dataPos; // Position in the file where the actual data begins
	unsigned int width, height;
	unsigned int imageSize; // = width*height*3
	// Actual RGB data
	unsigned char * data;
};

class BMP_Loader
{
public:
	BMPFormat* loadBMP_custom(const char * imagepath, BMPFormat* ret);
	BMP_Loader();
	~BMP_Loader();	
};

#endif
