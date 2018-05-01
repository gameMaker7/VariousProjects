#ifndef BUFFER_MANAGER_H
#define BUFFER_MANAGER_H
#include "Renderable.h"
#include "BufferInfo.h"
#define INITIAL_SIZE 10
#define INITIAL_BYTE_SIZE 1000000;
class BufferManager
{
public:
	bool Initialize();
	bool Shutdown();
	void AddGeometry(void* vertices, unsigned int vertexCount, unsigned int vertexSize, Geometry* mesh); // – for non - indexed data
	void AddGeometry(void* vertices, unsigned int vertexCount, unsigned int vertexSize, void* indices, unsigned int indexCount, unsigned int indexSize, Geometry* mesh); // – for indexed data
	BufferInfo* FindUsableBuffer(GLuint, GLuint index = 0, VertexFormats type = VertexColorNormal);
	bool AddRenderInfo(Renderable* info);
	static Renderable* list;
	static BufferInfo m_bufferPool[INITIAL_SIZE];
	static int m_numVertexBuffers;
	static int m_numIndexBuffers;

	BufferManager();
};

#endif