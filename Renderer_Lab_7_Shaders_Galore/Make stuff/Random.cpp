#include "Random.h"


Random::Random()
{
}

float Random::randFloat(){
	float x = (float)rand() / RAND_MAX;
	x *= 2;
	return x - 1;
}

float Random::randomInRange(float min, float max)
{
	return randFloat() * (max - min + 1) + min;
}

glm::vec3 Random::randomColor(){
	float r = (float)rand() / RAND_MAX;
	float g = (float)rand() / RAND_MAX;
	float b = (float)rand() / RAND_MAX;
	return glm::vec3(r, g, b);
}

glm::vec3 Random::randPosition()
{
	float x = randomInRange(-5.0f, 25.0f);
	float y = randomInRange(-5.0f, 25.0f);
	float z = randomInRange(-5.0f, 25.0f);
	return glm::vec3(x, y, z);
}

Random::~Random()
{
}
