#include "BufferManager.h"
#include "..\Make stuff/LogWriter.h"
int BufferManager::m_numVertexBuffers = 0;
int BufferManager::m_numIndexBuffers = 0;
BufferInfo BufferManager::m_bufferPool[INITIAL_SIZE];
bool BufferManager::Initialize(){
	return true;
}
bool BufferManager::Shutdown(){
	for (int i = 0; i < m_numVertexBuffers; ++i)
	{
		m_bufferPool[i].Shutdown();
	}

	return true;
}
void BufferManager::AddGeometry(void* vertices, unsigned vertexCount, unsigned vertexSize, Geometry* mesh){
	BufferInfo* buffer;
	if ((buffer = FindUsableBuffer(vertexCount))->m_vert_ID == 0)
	{
		buffer->type = mesh->type;
		buffer->m_vert_vertexSize = vertexSize;
		buffer->m_index_indexSize = 0;
		buffer->m_vert_bufferSizeInBytes = buffer->m_vert_vertexSize*INITIAL_BYTE_SIZE;
		glGenBuffers(1, &buffer->m_vert_ID);
		glBindBuffer(GL_ARRAY_BUFFER, buffer->m_vert_ID);
		glBufferData(GL_ARRAY_BUFFER, buffer->m_vert_bufferSizeInBytes, 0, GL_STATIC_DRAW);
	}
	glBindBuffer(GL_ARRAY_BUFFER, buffer->m_vert_ID);
	glBufferSubData(GL_ARRAY_BUFFER, buffer->m_vert_currentByteOffset, (vertexCount*vertexSize), vertices);
	mesh->m_vertexByteOffset = buffer->m_vert_currentByteOffset;
	buffer->m_vert_currentByteOffset += (vertexCount*vertexSize);
	mesh->m_vertexBufferID = buffer->m_vert_ID;
	
}
// change shapegen to use geometry*
// create buffers when all are full
// implement list of renderables
// alter draw to go through all buffers
void BufferManager::AddGeometry(void* vertices, GLuint vertexCount, GLuint vertexSize, void* indices, unsigned indexCount, unsigned indexSize, Geometry* mesh){
	if (!mesh->IsIndexed())
	{
		AddGeometry(vertices, vertexCount, vertexSize, mesh); // why have tons of ways to call this just do it here and no where else;
		return;
	}
	BufferInfo* buffer;
	buffer = FindUsableBuffer(vertexCount, indexSize, mesh->type);
	if (buffer->m_vert_ID == 0)
	{
		buffer->type = mesh->type;
		buffer->m_index_indexSize = indexSize;
		buffer->m_vert_vertexSize = vertexSize;
		buffer->m_vert_bufferSizeInBytes = buffer->m_vert_vertexSize*INITIAL_BYTE_SIZE;
		if (buffer->m_vert_bufferSizeInBytes < (vertexSize*vertexCount)) buffer->m_vert_bufferSizeInBytes = (vertexSize*vertexCount);
		glGenBuffers(1, &buffer->m_vert_ID);
		glBindBuffer(GL_ARRAY_BUFFER, buffer->m_vert_ID);
		glBufferData(GL_ARRAY_BUFFER, buffer->m_vert_bufferSizeInBytes, 0, GL_STATIC_DRAW);
		buffer->m_index_bufferSizeInBytes = buffer->m_index_indexSize*INITIAL_BYTE_SIZE;
		if (buffer->m_index_bufferSizeInBytes < (indexSize*indexCount)) buffer->m_index_bufferSizeInBytes = (indexCount*indexSize);
		glGenBuffers(1, &buffer->M_index_ID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer->M_index_ID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer->m_index_bufferSizeInBytes, 0, GL_STATIC_DRAW);
	}
	glBindBuffer(GL_ARRAY_BUFFER, buffer->m_vert_ID);
	glBufferSubData(GL_ARRAY_BUFFER, buffer->m_vert_currentByteOffset, (vertexCount*vertexSize), mesh->verts);
	mesh->m_vertexByteOffset = buffer->m_vert_currentByteOffset;
	buffer->m_vert_currentByteOffset += (vertexCount*vertexSize);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer->M_index_ID);
	glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, buffer->m_index_currentByteOffset, (indexCount*indexSize), indices);
	mesh->m_indexByteOffset = buffer->m_index_currentByteOffset;
	buffer->m_index_currentByteOffset += (indexCount*indexSize);
	mesh->m_vertexBufferID = buffer->m_vert_ID;
	mesh->m_indexBufferID = buffer->M_index_ID;
	mesh->VAO = buffer->VAO_Amount;
	++buffer->VAO_Amount;
	mesh->setVertexAttributes();
}

BufferInfo* BufferManager::FindUsableBuffer(GLuint vertCount, GLuint indexSize, VertexFormats type)
{

	for (int i = 0; i < m_numVertexBuffers; ++i)
	{
		if (m_bufferPool[i].m_vert_ID != 0){
			if (!m_bufferPool[i].HasRoomForVertices(vertCount) || (m_bufferPool[i].m_index_indexSize != indexSize) || (m_bufferPool[i].type != type)){ continue; }
			else{ return &m_bufferPool[i]; }
		}
		return &m_bufferPool[i];
	}

	LOG(Severe, "ERROR: No Bufffers Left");
	return nullptr;
}

bool BufferManager::AddRenderInfo(Renderable* info){
	BufferInfo* buffer = nullptr;
	for (int i = 0; i < m_numVertexBuffers; ++i){
		if (m_bufferPool[i].m_vert_ID == info->getVID())
		{
			buffer = &m_bufferPool[i];
			break;
		}
	}
	if (buffer == nullptr) return false;
	Renderable* current = buffer->List;
	while (current->next != NULL){
		current = current->next;
	}
	current->next = info;
	return true;
}

BufferManager::BufferManager()
{
	m_numVertexBuffers = INITIAL_SIZE;
	m_numIndexBuffers = INITIAL_SIZE;
}
