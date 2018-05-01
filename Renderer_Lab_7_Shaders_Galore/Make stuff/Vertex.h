#ifndef VERTEX_H
#define VERTEX_H
#pragma warning(push)
#pragma warning(disable : 4201)
#include "glm.hpp"
#pragma warning(pop)
struct vertexPCN{
	vertexPCN(glm::vec3 p, glm::vec3 c){
		position = p;
		color = c;
	}
	vertexPCN(glm::vec3 p, glm::vec3 c, glm::vec3 n){
		position = p;
		color = c;
		normal = n;
	}
	vertexPCN(){}
	glm::vec3 position;
	glm::vec3 color;
	glm::vec3 normal;
}; 

enum VertexFormats
{
	HasPosition = 1,
	HasTexture = 2,
	HasColor = 4,
	HasNormal = 8,
	Vertex = HasPosition,
	VertexTexture = HasPosition | HasTexture,
	VertexColor = HasPosition | HasColor,
	VertexNormal = HasPosition | HasNormal,
	VertexTextureNormal = HasPosition | HasTexture | HasNormal,
	VertexColorNormal = HasPosition | HasColor | HasNormal,
	VertexColorTexture = HasPosition | HasTexture | HasColor,
	VertexColorTextureNormal = HasPosition | HasTexture | HasColor | HasNormal,
	Zero,
};


#endif