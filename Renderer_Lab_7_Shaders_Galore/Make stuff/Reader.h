#ifndef READER_H
#define READER_H
#define MAXKEYS 25
#define MAXVALUELEN 100
#define MAXKEYLEN 100

#include "Vertex.h"
struct Scene
{
	int numVertices;
	int numIndices;
	int sizeVertex;
	int sizeIndex;
	VertexFormats format;
	void* vertices;
	void* indices;
};

struct KeyValues{
	char key[MAXKEYLEN];
	char value[MAXVALUELEN];
};

struct ConfigData{
	KeyValues pairs[MAXKEYS];
	bool initialize(const char* filename, ConfigData& test);
	bool shutdown();
	const char* FindValueForKey(const char* Key);   
	const char* FindNeededValueForKey(const char* Key);
	float GetFloatFromString(const char* s) const; // helper function
	int GetIntFromString(const char* s) const; // helper function
	glm::vec3 ConfigData::GetVec3FromString(const char* s) const; // Helper Function
};

static ConfigData ConfigInfo;
Scene* ReadSceneFile(const char* filename);

#endif
