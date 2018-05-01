#ifndef RENDERABLE_H
#define RENDERABLE_H
#include <GL\glew.h>
#include "..\Make stuff\Geometry.h"
#include "..\Make stuff\TransformInfo.h"
#include "..\Make stuff\TextureInfo.h"
#include "..\Make stuff\ShaderInfo.h"
#include "..\Make stuff\Vertex.h"
#include "..\Make stuff\Random.h"
extern Random gen;

struct boundingBox{
	glm::vec3 min;
	glm::vec3 max;
	boundingBox(){};
	boundingBox(glm::vec3 min, glm::vec3 max){
		this->min = min;
		this->max = max;
	}
	bool checkCollisionOutside(glm::vec3 against){
		if (against.x >= min.x && against.x <= max.x){
			if (against.y >= min.y && against.y <= max.y){
				if (against.z >= min.z && against.z <= max.z){
					return true;
				}
			}
		}
		return false;
	}
};


class Renderable
{
public:
	void update(float);
	void Draw(const glm::mat4 &c, const glm::mat4 &d);
	void Shutdown();
	void setScale(glm::vec3 newScale);
	GLuint getVID();
	void setTextureInfo(const char* filename, int id);
	void setPosition(glm::vec3 new_position);
	glm::vec3 getPosition();
	void setRotation(glm::vec3 new_direction);
	glm::vec3 getRotation();
	void setShaders(GLuint shaderID);
	void SetCallback(Callback function);
	Callback GetCallback();
	glm::mat4 FinalTransform;
	glm::mat4 transform;
	TransformInfo m_transformInfo;
	ShaderInfo m_ShaderInfo;
	TextureInfo m_textureInfo;
	Geometry* info;
	Renderable* next;

	float angle;
	float rotation_speed;
	bool rotation;
	bool isEnabled;
	Renderable();
	~Renderable();
};
#endif


