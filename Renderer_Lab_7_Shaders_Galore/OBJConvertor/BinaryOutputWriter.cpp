#include "BinaryOutput.h"
#include <algorithm>
#include <sstream>
#include <iostream>
#include <fstream>
#include "../Make stuff/Random.h"
#include "../Make stuff/LogWriter.h"
Random gen;
BinaryOutput::BinaryOutput(){}

std::string ReadTextFileIntoString(const char* filename)
{
	std::stringstream buffer;              // buffer to hold the text
	std::ifstream     inStream(filename); // open input file stream
	if (!inStream.good())
	{
		std::cout << "File failed to load [" << filename << "]\n";
		exit(1);
	}
	buffer << inStream.rdbuf();  // read it all in at once
	inStream.close();
	return buffer.str();         // the buffer provides the string
}


void BinaryOutput::WriteCustomBinaryFile(const char * filename, const char* newFile, glm::vec3* vertices, glm::vec3* colors, glm::vec3* normals,
	int** index, bool TexturesEnabled, glm::vec2* Textures, glm::vec3 ColorValue, bool NormalsEnabled)
{

	this->HasTexture = TexturesEnabled;
	verts = vertices;
	this->colors = colors;
	this->normals = normals;
	this->index = index;
	this->Textures = Textures;
	colorsInFile = ColorValue;
	this->HasNormals = NormalsEnabled;
	if (ColorValue != glm::vec3(0, 0, 0)){ HasColors = true; }
	int totalBytes = 0;
	std::ofstream outputStream(newFile, std::ios::binary | std::ios::out);
	outputStream.seekp(0);
	WriteInt(outputStream, totalBytes);
	readLines(filename);
	LOG(Info, "Write Header");
	totalBytes += WriteHeader(outputStream);
	LOG(Info, "Write Header Complete");
	LOG(Info, "Write file");
	totalBytes+= writeFile(outputStream);
	LOG(Info, "Write file Complete");
	LOG(Info, "Write Indices");
	totalBytes += WriteIndices(outputStream);
	LOG(Info, "Write Indices Complete");
	outputStream.seekp(0);
	WriteInt(outputStream, totalBytes);
	outputStream.close();
	printf("Total : wrote %d bytes.\n", totalBytes);
}
int BinaryOutput::sizeOfFormat()
{
	int result = 3*sizeof(float);
	if (HasColors) result += 3 * sizeof(float);
	if (HasTexture) result += 2 * sizeof(float);
	if (HasNormals) result += 3 * sizeof(float);
	LOG(Info, "Size of Format Complete");
	return result;
}
void BinaryOutput::setFakeValues()
{
	LOG(Info, "Write Set Fake Values");
	if (HasTexture && TexturesCount == 0){
		Textures[0] = glm::vec2(0.0f, 0.5f);
	}
	if (HasNormals && normalCount == 0){
		normals[0] = glm::vec3(1.0f, 0.0f, 1.0f);
	}
}
int BinaryOutput::writeFile(std::ofstream& out){
	int totalBytes = 0;
	for (GLuint i = 0; i < indexCount; ++i){
		int j = 0;
			int p = index[i][j];
			totalBytes += WriteVec3(out, verts[p - 1]);
		if (HasColors)
		{
			++j;
			p = index[i][j];
			totalBytes += WriteVec3(out, colors[p - 1]);
		}
		if (HasTexture)
		{
			++j;
			p = index[i][j];
			totalBytes += WriteVec2(out, Textures[p - 1]);
		}
		if (HasNormals)
		{
			++j;
			p = index[i][j];
			totalBytes += WriteVec3(out, normals[p - 1]);
		}

	}
	printf("total bytes from vertices & normals: %d \n", totalBytes);
	return totalBytes;

}
void BinaryOutput::readLines(const char* filename){
	std::string Line;
	std::ifstream inStream(filename);
	if (!inStream.good()){
		std::cout << "ERROR inStream not working" << std::endl;
		return;
	}
	while (std::getline(inStream, Line)){
		ParseLines(Line);
	}
}
void BinaryOutput::ParseLines(std::string Line){
	std::string word;
	std::stringstream parser(Line);
	parser >> word;
	if (parser.fail()) return;
	if (word[0] == '#') return;
	if (word == "v") addVector(parser);
	if ((word == "vt") && (HasTexture))
	{
		addTexture(parser);
	}
	if ((word == "v") && (HasColors))
	{
		if (colorsInFile == glm::vec3(-1, -1, -1))
		{
			genColors(gen.randomColor());
		}
		else{ genColors(colorsInFile); }
	}
	if ((word == "vn") && HasNormals){
		addNormal(parser);
	}
	if (word == "f"){
		if (indexCount == 0){ setFakeValues(); }
		if(vertFormat == Zero) setVertexFormat();
		addIndice(parser);
	}
}
void BinaryOutput::addVector(std::stringstream& parser){
	std::string word;
	parser >> word;
	verts[vertCount].x = GetFloatFromString(word.c_str());
	parser >> word;
	verts[vertCount].y = GetFloatFromString(word.c_str());
	parser >> word;
	verts[vertCount].z = GetFloatFromString(word.c_str());
	//std::cout << "Vertex[" << vertCount << "] " << verts[vertCount].x << ", " << verts[vertCount].y << ", " << verts[vertCount].z << std::endl;
	++vertCount;
}
void BinaryOutput::addTexture(std::stringstream& parser){
	std::string word;
	parser >> word;
	Textures[TexturesCount].x = GetFloatFromString(word.c_str());
	parser >> word;
	Textures[TexturesCount].y = GetFloatFromString(word.c_str());
	//std::cout << "Texture[" << TexturesCount << "] " << Textures[TexturesCount].x << ", " << Textures[TexturesCount].y << std::endl;
	++TexturesCount;

}
void BinaryOutput::addNormal(std::stringstream& parser){
	std::string word;
	parser >> word;
	normals[normalCount].x = GetFloatFromString(word.c_str());
	parser >> word;
	normals[normalCount].y = GetFloatFromString(word.c_str());
	parser >> word;
	normals[normalCount].z = GetFloatFromString(word.c_str());
	//std::cout << "Normal[" << normalCount << "] " << normals[normalCount].x << ", " << normals[normalCount].y << ", " << normals[normalCount].z << std::endl;
	
	++normalCount;
}
void BinaryOutput::addIndice(std::stringstream& parser){
	if (vertFormat == Vertex)
	{
		std::string word;
		for (short j = 0; j < 3; ++j){
			parser >> word;
			//std::cout << word << std::endl;
			index[indexCount] = new int[0];
			std::replace(word.begin(), word.end(), '/', ' ');
			std::stringstream smallParser(word);
				smallParser >> index[indexCount][0];			
			++indexCount;
		}
	}
	if (vertFormat == VertexColor || vertFormat == VertexNormal || vertFormat == VertexTexture)
	{
		std::string word;
		for (short j = 0; j < 3; ++j){
			parser >> word;
			//std::cout << word << std::endl;
			index[indexCount] = new int[1];
			std::replace(word.begin(), word.end(), '/', ' ');
			std::stringstream smallParser(word);
				smallParser >> index[indexCount][0];
					if (colorCount != 0){ index[indexCount][1] = index[indexCount][0]; }
				else{ smallParser >> index[indexCount][1]; }
				if (normalCount == 0 || TexturesCount == 0)
				{
					index[indexCount][1] = 1;
				}
			
			++indexCount;
		}
	}
	if (vertFormat == VertexColorNormal || vertFormat == VertexColorTexture || vertFormat == VertexTextureNormal)
	{
		std::string word;
		for (short j = 0; j < 3; ++j){
			parser >> word;
			std::cout << word << std::endl;
			index[indexCount] = new int[2];
			std::replace(word.begin(), word.end(), '/', ' ');
			std::stringstream smallParser(word);
				smallParser >> index[indexCount][0];
				if (TexturesCount == 0){ index[indexCount][1] = 1; }
				else{ smallParser >> index[indexCount][1]; }
				if (colorCount != 0){ index[indexCount][1] = index[indexCount][0]; }
				if (normalCount == 0 || TexturesCount == 0){ index[indexCount][2] = 1; }
				else{ smallParser >> index[indexCount][2]; }
			++indexCount;
		}
	}	
	if (vertFormat == VertexColorTextureNormal)
	{
		std::string word;
		for (short j = 0; j < 3; ++j){
			parser >> word;
			//std::cout << word << std::endl;
			index[indexCount] = new int[3];
			std::replace(word.begin(), word.end(), '/', ' ');
			std::stringstream smallParser(word);
				smallParser >> index[indexCount][0];
				if (colorsInFile == glm::vec3(0, 0, 0)){ smallParser >> index[indexCount][1]; }
				else{ index[indexCount][1] = index[indexCount][0]; }
				if (TexturesCount == 0){ index[indexCount][2] = 1; }
				else{ smallParser >> index[indexCount][2]; }
				if (normalCount == 0){ index[indexCount][3] = 1; }
				else{ smallParser >> index[indexCount][3]; }
				++indexCount;
		}
	}

}
int BinaryOutput::WriteHeader(std::ofstream & out)
{
	int totalBytes = 0;
	totalBytes += WriteInt(out, indexCount);
	totalBytes += WriteInt(out, indexCount);
	totalBytes += WriteInt(out, sizeOfFormat());
	if (indexCount > SHRT_MAX){ totalBytes += WriteInt(out, sizeof(GLuint)); }
	if (indexCount <= SHRT_MAX){ totalBytes += WriteInt(out, sizeof(GLushort)); }
	totalBytes += WriteInt(out, vertFormat);
	totalBytes += WritePointer(out, 2);
	printf("total bytes from header: %d \n", totalBytes);
	return totalBytes;
}
void BinaryOutput::genColors(glm::vec3 colorValue){
	colors[colorCount] = colorValue;
	++colorCount;
}
VertexFormats BinaryOutput::setVertexFormat()
{

	if (HasTexture && !HasNormals && !HasColors){ return vertFormat = VertexTexture; }
	if (!HasTexture && !HasNormals && HasColors){ return vertFormat = VertexColor; }
	if (!HasTexture && HasNormals && !HasColors){ return vertFormat = VertexNormal; }
	if (HasTexture && HasNormals && HasColors){ return vertFormat = VertexColorTextureNormal; }
	if (HasTexture && HasNormals && !HasColors){ return vertFormat = VertexTextureNormal; }
	if (!HasTexture && HasNormals && HasColors){ return vertFormat = VertexColorNormal; }
	if (HasTexture && !HasNormals && HasColors){ return vertFormat = VertexColorTexture; }
	else{ return vertFormat = Vertex; }
}
int BinaryOutput::WriteIndices(std::ofstream & out)
{
	int totalBytes = 0;
	if (indexCount > SHRT_MAX){
		for (GLuint j = 0; j < indexCount; ++j)
		{
			totalBytes += WriteGLuint(out, j);
		}
	}
	else
	{
		for (GLushort j = 0; j < indexCount; ++j)
		{
			totalBytes += WriteGLushort(out, j);
		}
	}
	printf("total bytes from indices: %d \n", totalBytes);
	return totalBytes;
}
// ----------------------------------------------------------------------
// Helper functions
// ----------------------------------------------------------------------
int BinaryOutput::WriteInt(std::ofstream & out, int value)
{
	int size = sizeof(int);
	out.write(reinterpret_cast<char*> (&value), size);
	return size;
}
int BinaryOutput::WriteVec2(std::ofstream& out, glm::vec2 value)
{
	std::cout << value.x << ", " << value.y << std::endl;
	int size = sizeof(value);
	out.write(reinterpret_cast<char*> (&value), size);
	return size;

}
int BinaryOutput::WritePointer(std::ofstream & out, int count)
{
	int    size = sizeof(void*);
	void* pointer = 0;
	for (int j = 0; j < count; ++j)
	{
		out.write(reinterpret_cast<char*> (&pointer), size);
	}
	return count * size;
}
int BinaryOutput::WriteVec3(std::ofstream & out, glm::vec3 vec)
{
	int size = sizeof(vec);
	out.write(reinterpret_cast<char*> (&vec), size);
	return size;
}
int BinaryOutput::WriteGLuint(std::ofstream & out, GLuint value)
{
	int size = sizeof(GLuint);
	out.write(reinterpret_cast<char*> (&value), size);
	return size;
}
int BinaryOutput::WriteGLushort(std::ofstream & out, GLushort value)
{
	int size = sizeof(GLushort);
	out.write(reinterpret_cast<char*> (&value), size);
	return size;
}
float BinaryOutput::GetFloatFromString(const char* s) const
{
	std::istringstream buffer(s);
	float value;
	buffer >> value;
	return value;
}


