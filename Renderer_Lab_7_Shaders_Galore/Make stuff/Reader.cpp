#include "Reader.h"
#include "LogWriter.h"
#include <algorithm> 
#include "..\glew/include/GL/glew.h"
#include <stdio.h>
#include <fstream>
#include <sstream>
#include <direct.h> 
#include <iostream>
bool ConfigData::initialize(const char* filename, ConfigData& test){
	std::ifstream inStream(filename);
	std::string Line;
	if (!inStream.good()){
		printf("ERROR File does not Work %s", filename);
		return false;
	}
	int keyCount = 0;
	std::string word;
	while (std::getline(inStream, Line)){
		std::stringstream parser(Line);
		parser >> word;
		LOG(Info, word.c_str());
		if (parser.fail()) continue;
		if (word[0] == '#') continue;
		if (word[0] == '/') continue;
		// else
		for (unsigned short i = 0; i < word.length(); ++i){
			const char* a = word.c_str();
			ConfigInfo.pairs[keyCount].key[i] = a[i];
		}
		parser >> word;
		std::replace(word.begin(), word.end(), ',', ' ');
		std::replace(word.begin(), word.end(), '"', ' ');
		for (unsigned short i = 0; i < word.length(); ++i){
			const char* a = word.c_str();
			LOG(Info, a);
			ConfigInfo.pairs[keyCount].value[i] = a[i];
		}
		++keyCount;
		if (keyCount == MAXKEYS - 1){
			LOG(Error, "Max Keys reached please increase maximum size");
			inStream.close();
			return false;
		}
	}
	test = ConfigInfo;
	inStream.close();
	return true;
}
bool ConfigData::shutdown(){
	return true;
}
const char* ConfigData::FindValueForKey(const char* Key){
	for (short i = 0; i < MAXKEYS; ++i){
		if (strcmp(ConfigInfo.pairs[i].key, Key) == 0){
			return ConfigInfo.pairs[i].value;
		}
	}
	LOG(Error, "Key Not Found");
	return 0;
}
const char* ConfigData::FindNeededValueForKey(const char* Key){
	const char* res = FindValueForKey(Key);
	if (res == NULL){
		LOG(Severe, "Cannot Proceed need key value");
	}
	return res;
}
float ConfigData::GetFloatFromString(const char* s) const{
	std::istringstream buffer(s);
	float value;
	buffer >> value;
	return value;
}
int ConfigData::GetIntFromString(const char* s) const{
	std::istringstream buffer(s);
	int value;
	buffer >> value;
	return value;
}
glm::vec3 ConfigData::GetVec3FromString(const char* s) const{
	std::istringstream buffer(s);
	glm::vec3 ret;
	string word;
	for (short i = 0; i < 3; ++i){
		buffer >> word;
		ret[i] = GetFloatFromString(word.c_str());
	}
	return ret;
}
Scene* ReadSceneFile(const char* filename)
{
char fileName[80];
strcpy_s(fileName, "..\\Data\\Scenes\\");
strcat_s(fileName, filename);
strcat_s(fileName, ".scene");

	std::ifstream inputStream(fileName, std::ios::binary | std::ios::in);
	if (!inputStream.good())
	{
		printf("ERROR : Unable to open scene file %s.\n", fileName);
		LOG(Severe, "Cannot open scene file");
		return 0;
	}

	int dataLen;
	inputStream.read(reinterpret_cast<char*> (&dataLen), sizeof(dataLen));
	printf("Reading %d bytes.\n", dataLen);

	char* data = new char[dataLen];
	assert(data);
	std::fill(data, data + dataLen - 1, 0);

	inputStream.read(data, dataLen);
	inputStream.close();

	Scene* scene = reinterpret_cast<Scene*> (data);
	char* p = data;
	p += sizeof(Scene);
	scene->vertices = (p);
	p += scene->numVertices * scene->sizeVertex;
	scene->indices = (p);
	LOG(Info, "Scene reading Complete");
	return scene;
}
void DisplayVec3(glm::vec3* vec)
{
	printf("<%f, %f, %f>\n", vec->x, vec->y, vec->z);
}
void DisplayScene(Scene* scene)
{
	printf("NumVerts : %d\n", scene->numVertices);
	printf("NumIndices : %d\n", scene->numIndices);
	printf("sizeVerts : %d\n", scene->sizeVertex);
	printf("sizeIndices : %d\n", scene->sizeIndex);
	for (int j = 0; j < scene->numVertices; ++j)
	{
		printf("Position[%d] = ", j); DisplayVec3(&((vertexPCN*)scene->vertices)[j].position);
	}
	for (int j = 0; j < scene->numVertices; ++j)
	{
		printf("Position[%d] = ", j); DisplayVec3(&((vertexPCN*)scene->vertices)[j].color);
	}
	for (int j = 0; j < scene->numVertices; ++j)
	{
		printf("Position[%d] = ", j); DisplayVec3(&((vertexPCN*)scene->vertices)[j].normal);
	}
	for (int j = 0; j < scene->numIndices; ++j)
	{
		GLuint index = ((GLuint*)scene->indices)[j];
		std::cout << index << "\n";
	}   

	glm::vec3* p = reinterpret_cast<glm::vec3*> (scene->vertices);
	for (int j = 0; j < 3 * scene->numVertices; ++j)
	{
		printf("Vec[%d] = ", j); DisplayVec3(&p[j]);
	}
}
void ShowCurrentDir()
{
	char temp[512];
	_getcwd(temp, 512);
	printf("CurrentDir = [%s].\n", temp);
}

