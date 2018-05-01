#include "BinaryOutput.h"
#include <stdio.h>
#include "../Make stuff/LogWriter.h"
#include "../Make stuff/Reader.h"

glm::vec3 verts[ARRAY_MAX];
glm::vec3 colors[ARRAY_MAX];
glm::vec2 textures[ARRAY_MAX];
glm::vec3 normals[ARRAY_MAX];
int* index[ARRAY_MAX];

int main(int argc, char** argv)
{
	Logger log;
	printf("Hello from %s\n", argv[0]);
	BinaryOutput writer;
	ConfigData test;
	ConfigInfo.initialize("..\\Data\\Config.txt", test);
	bool texturesEnabled = false;
	bool NormalsEnabled = false;
	const char* ColorTest = "random";
	if (test.GetIntFromString(test.FindValueForKey("Normals")) == 1){ NormalsEnabled = true; }
	if (test.GetIntFromString(test.FindValueForKey("Textures")) == 1){ texturesEnabled = true; }
	glm::vec3 colorValue = glm::vec3(0, 0, 0);
	if (strcmp(test.FindValueForKey("Colors"), ColorTest) == 0)
	{
		colorValue = glm::vec3(-1, -1, -1);
	}
	else{ colorValue = test.GetVec3FromString(test.FindValueForKey("Colors")); }
	writer.WriteCustomBinaryFile(test.FindValueForKey("InputObj"), test.FindValueForKey("outputScene"), verts, colors, normals, index, texturesEnabled, textures, colorValue, NormalsEnabled);
	argc;
}
