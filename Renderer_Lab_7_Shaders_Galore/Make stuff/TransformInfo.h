#ifndef TRANSFORM_INFO_H
#define TRANSFORM_INFO_H
#include "Vertex.h"
class TransformInfo
{
public:
	glm::vec3 m_scaleTransform;
	glm::vec3 m_rotateTransform;
	glm::vec3 m_translateTransform;
	TransformInfo();
	~TransformInfo();
};

#endif