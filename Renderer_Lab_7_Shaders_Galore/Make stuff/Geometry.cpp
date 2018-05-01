#include "Geometry.h"

bool Geometry::IsIndexed(){
	if (m_indexCount != 0)
	{
		return true;
	}
	return false;
}
void Geometry::setVertexAttributes()
{
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);
	glBindBuffer(GL_ARRAY_BUFFER, m_vertexBufferID);
	int format = m_vertexByteOffset;
	int attribIndex = 0;
	if (type & HasPosition){
		glEnableVertexAttribArray(attribIndex);
		glVertexAttribPointer(attribIndex, 3, GL_FLOAT, GL_FALSE, m_vertexStride, (void*)format);
		format += (sizeof(float) * 3);
		++attribIndex;
	}
	if (type & HasColor){
		glEnableVertexAttribArray(attribIndex);
		glVertexAttribPointer(attribIndex, 3, GL_FLOAT, GL_FALSE, m_vertexStride, (void*)format);
		format += (sizeof(float) * 3);
	}
	++attribIndex;
	if (type & HasTexture){
		glEnableVertexAttribArray(attribIndex);
		glVertexAttribPointer(attribIndex, 2, GL_FLOAT, GL_FALSE, m_vertexStride, (void*)format);
		format += (sizeof(float) * 2);
	}
	++attribIndex;
	if (type & HasNormal){
		glEnableVertexAttribArray(attribIndex);
		glVertexAttribPointer(attribIndex, 3, GL_FLOAT, GL_FALSE, m_vertexStride, (void*)format);
	}
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_indexBufferID);
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}

bool Geometry::Uses16BitIndices()
{
	return m_use16BitIndices;
}




