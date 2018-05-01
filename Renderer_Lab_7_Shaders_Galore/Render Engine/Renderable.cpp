#include "Renderable.h"
#include <gtc/matrix_transform.inl>// safe

Renderable::Renderable()
{
	next = nullptr;
	m_transformInfo.m_scaleTransform = glm::vec3(1.0f, 1.0f, 1.0f);
	m_transformInfo.m_translateTransform = glm::vec3(0.0f, 0.0f, 0.0f);
	angle = 0;
	rotation_speed = 0;
	rotation = false;
	isEnabled = true;
	m_transformInfo.m_rotateTransform = glm::vec3(.5, -.2, .8);
}
void Renderable::update(float dt){
	if (rotation){
		angle += (dt * rotation_speed);
	}
}

void Renderable::Draw(const glm::mat4 &project, const glm::mat4 &world){
	glBindVertexArray(info->VAO);

	glBindTexture(GL_TEXTURE_2D, m_textureInfo.textureID);
	transform = glm::translate(glm::mat4(), m_transformInfo.m_translateTransform);

	transform = glm::rotate(transform, m_transformInfo.m_rotateTransform.x, glm::vec3(1.0f, 0.0f, 0.0f));
	transform = glm::rotate(transform, m_transformInfo.m_rotateTransform.y, glm::vec3(0.0f, 1.0f, 0.0f));
	transform = glm::rotate(transform, m_transformInfo.m_rotateTransform.z, glm::vec3(0.0f, 0.0f, 1.0f));

	transform = glm::scale(transform, m_transformInfo.m_scaleTransform);
	FinalTransform = (project * world * transform);

	m_ShaderInfo.BeforeDrawing(this);
	if (info->m_use16BitIndices)
	{
		glDrawElements(info->mode, info->m_indexCount, GL_UNSIGNED_INT, (void*)info->m_indexByteOffset);
	}
	else{
		glDrawElements(info->mode, info->m_indexCount, GL_UNSIGNED_SHORT, (void*)info->m_indexByteOffset);
	}
	glBindVertexArray(0);
}
void Renderable::Shutdown()
{
	m_textureInfo.Shutdown();
	if (next != NULL)
	{
		next->Shutdown();
		next = nullptr;
	}
}
GLuint Renderable::getVID()
{
	return info->m_vertexBufferID;
}
void Renderable::setTextureInfo(const char* filename, int id)
{
	m_textureInfo.init(filename, id);
}
void Renderable::setPosition(glm::vec3 new_position)
{
	m_transformInfo.m_translateTransform = new_position;
}
void Renderable::setScale(glm::vec3 newScale)
{
	m_transformInfo.m_scaleTransform = newScale;
}
glm::vec3 Renderable::getPosition()
{
	return m_transformInfo.m_translateTransform;
}
glm::vec3 Renderable::getRotation()
{
	return m_transformInfo.m_rotateTransform;
}
void Renderable::setShaders(GLuint shaderID)
{
	m_ShaderInfo.setID(shaderID);
}
void Renderable::SetCallback(Callback function)
{
	m_ShaderInfo.BeforeDrawing = function;
}
Callback Renderable::GetCallback()
{
	return m_ShaderInfo.GetCallback();
}
void Renderable::setRotation(glm::vec3 new_direction)
{
	m_transformInfo.m_rotateTransform = new_direction;
}
Renderable::~Renderable(){

}