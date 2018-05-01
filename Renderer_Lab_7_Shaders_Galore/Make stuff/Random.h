#ifndef RANDOM_H
#define RANDOM_H
struct Random
{

	Random()
	{
	}

	float randFloat(){
		float x = (float)rand() / RAND_MAX;
		x *= 2;
		return x - 1;
	}

	float randomInRange(float min, float max)
	{
		float check = randFloat();
		float done = check * ((max - min + 1) + min);
		return done;
	}

	glm::vec3 randomColor(){
		float r = (float)rand() / RAND_MAX;
		float g = (float)rand() / RAND_MAX;
		float b = (float)rand() / RAND_MAX;
		return glm::vec3(r, g, b);
	}

	glm::vec3 randPosition(glm::vec3 center)
	{
		float x = randomInRange(center.x - 10.0f, center.x + 10.0f);
		float y = randomInRange(center.y - 10.0f, center.y + 10.0f);
		float z = randomInRange(center.z - 10.0f, center.z + 10.0f);
		return glm::vec3(x, y, z); 
	}

	~Random()
	{
	}

	inline int Random::randInt(int min, int max)
	{
		float x = (float)rand() / RAND_MAX;
		int done = (int)(x * (max - min + 1) + min);
		return done;
	}
	
};

#endif

