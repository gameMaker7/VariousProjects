#ifndef SHAPEGEN_H
#define SHAPEGEN_H
#include "Geometry.h"
typedef struct { glm::vec3 p1, p2, p3; } Facet3; // 3 points per Facet

class ShapeGen
{
public:
	ShapeGen();
	//void debug(Geometry &box, vec3 color);
	Geometry makeCube();
	Geometry makeGrid(short range);
	Geometry makeArrow();
	Geometry makePlane(glm::vec3, glm::vec3);
	Geometry MakeSphere(glm::vec3 color);
	Geometry makeLine(glm::vec3, glm::vec3, glm::vec3);
	Geometry CreateScene(const char* filename);

private:
	int  GenerateSphere(Facet3* facets, int iterations);
	void BuildSphere(glm::vec3 color);
};
   
#endif