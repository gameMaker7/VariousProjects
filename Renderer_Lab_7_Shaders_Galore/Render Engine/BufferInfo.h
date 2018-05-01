#ifndef BUFFER_INFO_H
#define BUFFER_INFO_H
#include "GL\glew.h"
#include "Renderable.h"
class Renderable;

class BufferInfo
{
public:

	bool HasRoomForVertices(unsigned int totalBytes);
	bool HasRoomForIndices(unsigned int totalBytes);
	bool Shutdown();
	void Draw(const glm::mat4 &c, const glm::mat4 &d);
	GLuint m_vert_currentByteOffset;// – new verts will go here
	GLuint m_vert_bufferSizeInBytes;
	GLuint m_vert_vertexSize;
	GLuint m_vert_ID;
	GLuint m_index_currentByteOffset;// – new indices will go here
	GLuint m_index_bufferSizeInBytes;
	GLuint m_index_indexSize;// – 2 or 4 ?
	GLuint M_index_ID;
	VertexFormats type;
	Renderable* List;
	static GLuint VAO_Amount;
	BufferInfo();
	~BufferInfo(); 
};

#endif