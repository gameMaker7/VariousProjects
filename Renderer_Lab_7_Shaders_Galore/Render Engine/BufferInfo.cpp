#include "BufferInfo.h"
#include "Renderable.h"

GLuint BufferInfo::VAO_Amount = 1;

bool BufferInfo::HasRoomForIndices(unsigned totalBytes)
{
	if (totalBytes + m_index_currentByteOffset > m_index_bufferSizeInBytes)
	{
		return false;
	}
	return true;
}

bool BufferInfo::HasRoomForVertices(unsigned totalBytes)
{
	if (totalBytes + m_vert_currentByteOffset > m_vert_bufferSizeInBytes)
	{
		return false;
	}
	return true;
}
bool BufferInfo::Shutdown()
{
	List->Shutdown();
	delete List;
	return true;
}

void BufferInfo::Draw(const glm::mat4 &c, const glm::mat4 &d)
{
	Renderable* current = List->next;
	if (current != nullptr){
		glUseProgram(current->m_ShaderInfo.programID);
	}
	while (current != NULL)
	{

		if ((current->isEnabled))
		{
				current->Draw(c, d);
		}
		if (current->next != nullptr && current->m_ShaderInfo.programID != current->next->m_ShaderInfo.programID)
		{
			glUseProgram(current->next->m_ShaderInfo.programID);
		}
		current = current->next;
	}
}

BufferInfo::BufferInfo()
{
	M_index_ID = 0;
	m_vert_ID = 0;
	m_vert_currentByteOffset = 0;
	m_index_currentByteOffset = 0;
	m_index_bufferSizeInBytes = 1;
	m_vert_bufferSizeInBytes = 1;
	List = new Renderable;
}


BufferInfo::~BufferInfo()
{
}
