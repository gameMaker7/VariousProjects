#ifndef BO_H
#define BO_H
#include "..\..\Middleware\glew\include\GL\glew.h"
#pragma warning(push)
#pragma warning(disable : 4201)
#include "glm.hpp" 
#pragma warning(pop)
#include <string>
#include "..\Make stuff/Vertex.h"
#define ARRAY_MAX 1000000

class BinaryOutput
{
public:
	VertexFormats vertFormat = Zero;
	int vertCount = 0;
	int normalCount = 0;
	GLuint indexCount = 0;
	int TexturesCount = 0;
	int colorCount = 0;
	bool HasTexture = false;
	bool HasNormals = false;
	bool HasColors = false;
	int vertsSize;
	int indicesSize;
	glm::vec3* verts;
	glm::vec3* colors;
	glm::vec2* Textures;
	glm::vec3* normals;
	int** index;
	glm::vec3 colorsInFile;
	void readLines(const char* filename);
	int writeFile(std::ofstream&);
	void setFakeValues();
	void ParseLines(std::string Line);
	void addVector(std::stringstream& Line);
	void addTexture(std::stringstream& Line);
	void addNormal(std::stringstream& Line);
	void addIndice(std::stringstream& Line);
	void genColors(glm::vec3 colorValue);
	VertexFormats setVertexFormat();
	void WriteCustomBinaryFile(const char* filename, const char* newFile, glm::vec3* Verts, glm::vec3* color,
		glm::vec3* normals, int** index, bool texturesEnabled, glm::vec2* textures = nullptr, glm::vec3 colorValue = glm::vec3(), bool normalEnabled = false); 
	int sizeOfFormat();
	int WriteHeader(std::ofstream& out);
	int WriteInt(std::ofstream& out, int value);
	int WritePointer(std::ofstream& out, int count);
	int WriteVec2(std::ofstream& out, glm::vec2 value);
	int WriteVec3(std::ofstream& out, glm::vec3 vec);
	int WriteIndices(std::ofstream& out);
	int WriteGLuint(std::ofstream &out, GLuint value);
	int WriteGLushort(std::ofstream& out, GLushort value);
	float BinaryOutput::GetFloatFromString(const char* s) const;
	BinaryOutput();
	};

#endif