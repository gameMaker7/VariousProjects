#ifndef GEOMETRY_H
#define GEOMETRY_H
#include "GL\glew.h"
#include "Reader.h"

struct Geometry
{
	GLenum mode;
	GLuint m_vertexByteOffset;
	GLuint m_vertexCount;
	GLuint m_vertexStride;
	GLuint m_vertexBufferID;
	GLuint m_indexByteOffset;
	GLuint m_indexCount;
	GLuint m_indexStride;
	GLuint m_indexBufferID;
	GLuint m_vertsPerPrimitive;
	VertexFormats type;
	void* verts;
	void* indices;
	bool  m_use16BitIndices = false;
	GLuint VAO;
	bool IsIndexed();
	void setVertexAttributes();
	bool Uses16BitIndices();

	int Vertsize(){
		return sizeof(vertexPCN) * m_vertexCount;
	}
	int Indicessize(){
		return sizeof(int) * m_indexCount;
	}
};

#endif