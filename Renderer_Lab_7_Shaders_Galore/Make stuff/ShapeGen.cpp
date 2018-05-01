#include "ShapeGen.h"
#include "LogWriter.h"
#include "..\Render Engine/RenderEngine.h"
extern Random gen;
#define NUM_ARRAY_ELEMENTS(a) sizeof(a) / sizeof(*a)

enum SphereDefines
{
	NUMSPHEREITERATIONS = 2,  // if you change this, you must change the next one too!!!
	FOUR_TO_NUM_ITERATIONS = 16,     // 4^numIterations, ie, 4 to the power of
	NUMSPHEREFACETS = FOUR_TO_NUM_ITERATIONS * 8, // 4^numIterations * 8
	NUMSPHEREVERTS = 3 * NUMSPHEREFACETS, // 3 verts per facet/triangle
	NUMSPHEREINDICES = 2 * NUMSPHEREVERTS   // two indices per point
};

vertexPCN sphereVerts[NUMSPHEREVERTS];
Facet3      sphereFacets[NUMSPHEREFACETS];
GLuint    sphereIndices[NUMSPHEREINDICES];
//void ShapeGen::debug(Geometry &Shape, vec3 color){
//	Shape.mode = GL_LINE_STRIP;
//	GLuint indices[] = {
//		0, 1, 2, 3, 0, // Top
//		4, 5, 6, 7, 4, // Front
//		8, 9, 10, 11, 8, // Right 
//		12, 13, 14, 15, 12, // Left
//		16, 17, 18, 19, 16, // Back
//		20, 21, 22, 23, 20, // Bottom
//	};
//	for (uint i = 0; i < Shape.m_vertexCount; ++i){
//		Shape.verts[i].color = color;
//	}
//	Shape.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
//	Shape.indices = new GLuint[Shape.m_indexCount];
//	memcpy(Shape.indices, indices, sizeof(indices));
//}
ShapeGen::ShapeGen()
{

}
Geometry ShapeGen::makePlane(glm::vec3 point1, glm::vec3 point2){

	float x1 = point1.x;
	float x2 = point2.x;
	float z1 = point1.z;
	float z2 = point2.z;

	Geometry ret;
	ret.mode = GL_TRIANGLES;
	ret.type = VertexColorNormal;

	vertexPCN verts[] =
	{
		vertexPCN(glm::vec3(x1, 0, z1), // 0
		gen.randomColor()), // Color
		vertexPCN(glm::vec3(x2, 0, z2), // 1
		gen.randomColor()), // Color
		vertexPCN(glm::vec3(-x1, +0.0f, -z1), // 2
		gen.randomColor()), // Color
		vertexPCN(glm::vec3(-x2, +0.0f, -z2), // 3
		gen.randomColor()), // Color
	};
	ret.m_vertexCount = NUM_ARRAY_ELEMENTS(verts);
	for (GLuint i = 0; i < ret.m_vertexCount; ++i){
		verts[i].normal = glm::vec3(0, 1, 0);
	}
	GLuint indices[] = {
		0, 1, 2, 2, 3, 0
	};
	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.verts = verts;
	ret.indices = indices;
	ret.m_vertexStride = 36;
	ret.m_indexStride = 2;
	RenderEngine::AddGeometry(ret.verts, ret.m_vertexCount, ret.m_vertexStride, ret.indices, ret.m_indexCount, ret.m_indexStride, ret);
	return ret;		
}
Geometry ShapeGen::makeCube(){
	Geometry ret;
	ret.mode = GL_TRIANGLES;
	ret.type = VertexColorNormal;
	vertexPCN verts[] =
	{
		vertexPCN(glm::vec3(-1.0f, +1.0f, +1.0f), // 0
		          glm::vec3(+1.0f, +0.0f, +0.0f), // Color
		          glm::vec3(0.0f, 1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, +1.0f, +1.0f), // 1
		          glm::vec3(+0.0f, +1.0f, +0.0f), // Color
		          glm::vec3(0.0f, 1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, +1.0f, -1.0f), // 2
		          glm::vec3(+0.0f, +0.0f, +1.0f), // Color
		          glm::vec3(0.0f, 1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, +1.0f, -1.0f), // 3
		          glm::vec3(+1.0f, +1.0f, +1.0f), // Color
		          glm::vec3(0.0f, 1.0f, 0.0f)), // normal

		vertexPCN(glm::vec3(-1.0f, +1.0f, -1.0f), // 4
		glm::vec3(+1.0f, +0.0f, +1.0f), // Color
		glm::vec3(0.0f, 0.0f, -1.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, +1.0f, -1.0f), // 5
		glm::vec3(+0.0f, +0.5f, +0.2f), // Color
		glm::vec3(0.0f, 0.0f, -1.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, -1.0f, -1.0f), // 6
		glm::vec3(+0.8f, +0.6f, +0.4f), // Color
		glm::vec3(0.0f, 0.0f, -1.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, -1.0f), // 7
		glm::vec3(+0.3f, +1.0f, +0.5f), // Color
		glm::vec3(0.0f, 0.0f, -1.0f)), // normal
		
		vertexPCN(glm::vec3(+1.0f, +1.0f, -1.0f), // 8
		glm::vec3(+0.2f, +0.5f, +0.2f), // Color
		glm::vec3(1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, +1.0f, +1.0f), // 9
		glm::vec3(+0.9f, +0.3f, +0.7f), // Color
		glm::vec3(1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, -1.0f, +1.0f), // 10
		glm::vec3(+0.3f, +0.7f, +0.5f), // Color
		glm::vec3(1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, -1.0f, -1.0f), // 11
		glm::vec3(+0.5f, +0.7f, +0.5f), // Color
		glm::vec3(1.0f, 0.0f, 0.0f)), // normal
		
		vertexPCN(glm::vec3(-1.0f, +1.0f, +1.0f), // 12
		glm::vec3(+0.7f, +0.8f, +0.2f), // Color
		glm::vec3(-1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, +1.0f, -1.0f), // 13
		glm::vec3(+0.5f, +0.7f, +0.3f), // Color
		glm::vec3(-1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, -1.0f), // 14
		glm::vec3(+0.4f, +0.7f, +0.7f), // Color
		glm::vec3(-1.0f, 0.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, +1.0f), // 15
		glm::vec3(+0.2f, +0.5f, +1.0f), // Color
		glm::vec3(-1.0f, 0.0f, 0.0f)), // normal

		vertexPCN(glm::vec3(+1.0f, +1.0f, +1.0f), // 16
		glm::vec3(+0.6f, +1.0f, +0.7f), // Color
		glm::vec3(0.0f, 0.0f, 1.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, +1.0f, +1.0f), // 17
		glm::vec3(+0.6f, +0.4f, +0.8f), // Color
		glm::vec3(0.0f, 0.0f, 1.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, +1.0f), // 18
		glm::vec3(+0.2f, +0.8f, +0.7f), // Color
		glm::vec3(0.0f, 0.0f, 1.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, -1.0f, +1.0f), // 19
		glm::vec3(+0.2f, +0.7f, +1.0f), // Color
		glm::vec3(0.0f, 0.0f, 1.0f)), // normal
		
		vertexPCN(glm::vec3(+1.0f, -1.0f, -1.0f), // 20
		glm::vec3(+0.8f, +0.3f, +0.7f), // Color
		glm::vec3(0.0f, -1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, -1.0f), // 21
		glm::vec3(+0.8f, +0.9f, +0.5f), // Color
		glm::vec3(0.0f, -1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(-1.0f, -1.0f, +1.0f), // 22
		glm::vec3(+0.5f, +0.8f, +0.5f), // Color
		glm::vec3(0.0f, -1.0f, 0.0f)), // normal
		vertexPCN(glm::vec3(+1.0f, -1.0f, +1.0f), // 23
		glm::vec3(+0.9f, +1.0f, +0.2f), // Color
		glm::vec3(0.0f, -1.0f, 0.0f)), // normal
	};
	ret.m_vertexCount = NUM_ARRAY_ELEMENTS(verts);

	GLshort indices[] = {
		0, 1, 2, 0, 2, 3, // Top
		4, 5, 6, 4, 6, 7, // Front
		8, 9, 10, 8, 10, 11, // Right 
		12, 13, 14, 12, 14, 15, // Left
		16, 17, 18, 16, 18, 19, // Back
		20, 22, 21, 20, 23, 22, // Bottom
	};

	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.verts = verts;
	ret.indices = indices;
	ret.m_vertexStride = 36;
	ret.m_indexStride = 2;
	RenderEngine::AddGeometry(verts, ret.m_vertexCount, sizeof(verts[0]), indices, ret.m_indexCount, sizeof(indices[0]), ret);
	return ret;
}
Geometry ShapeGen::makeGrid(short range){
	Geometry ret;
	ret.mode = GL_LINES;

	short numLines = 2 * range + 1;
	short totalLines = 2 * numLines;
	const int vertexCount = 2 * totalLines;
	vertexPCN* verts = new vertexPCN[vertexCount];
		for (int i = 0; i < numLines; ++i){ 
			verts[2*i].position = glm::vec3((i - range), 0, -range);
			verts[2*i + 1].position = glm::vec3((i - range), 0, range);
		}
	for (int i = 0; i < numLines; ++i){
		verts[2*i + totalLines].position = glm::vec3((-range), 0, (i - range));
		verts[2*i + 1 + totalLines].position = glm::vec3((range), 0, (i-range));
	}
	for (int i = 0; i < vertexCount; ++i){
		verts[i].color = glm::vec3(1.0f, 1.0f, 1.0f);
		verts[i].normal = glm::vec3(0.0f, 1.0f, 0.0f);
	}

	ret.m_vertexCount = vertexCount;
	ret.type = VertexColorNormal;

	GLuint* indices = new GLuint[vertexCount];
	for (int i = 0; i < vertexCount; ++i){
		indices[i] = i;
	}

	ret.m_indexCount = vertexCount;
	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.verts = verts;
	ret.indices = indices;
	ret.m_vertexStride = 36;
	ret.m_indexStride = 2;
	RenderEngine::AddGeometry(verts, ret.m_vertexCount, sizeof(verts[0]), indices, ret.m_indexCount, sizeof(indices[0]), ret);
	return ret;
	
}
Geometry ShapeGen::makeArrow(){

	Geometry ret;
	ret.mode = GL_TRIANGLES;

	vertexPCN verts[] =
	{
		//top side of arrow head
		vertexPCN(glm::vec3(+0.00f, +0.25f, -0.25f), //0
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.50f, +0.25f, -0.25f), //1
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.00f, +0.25f, -1.00f), //2
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.50f, +0.25f, -0.25f), //3
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		//bottom side of arrow head	 		 
		vertexPCN(glm::vec3(+0.00f, -0.25f, -0.25f), //4
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.50f, -0.25f, -0.25f), //5
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color	
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.00f, -0.25f, -1.00f), //6
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color	
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.50f, -0.25f, -0.25f), //7
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color	
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		//right side of arrow tip
		vertexPCN(glm::vec3(+0.50f, +0.25f, -0.25f), //8
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(0.83205032, 0, -0.55470026)), //normal
		vertexPCN(glm::vec3(+0.00f, +0.25f, -1.00f), //9
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color	
		glm::vec3(0.83205032, 0, -0.55470026)), //normal
		vertexPCN(glm::vec3(+0.00f, -0.25f, -1.00f), //10
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(0.83205032, 0, -0.55470026)), //normal
		vertexPCN(glm::vec3(+0.50f, -0.25f, -0.25f), //11
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(0.83205032, 0, -0.55470026)), //normal
		//left side of arrow tip
		vertexPCN(glm::vec3(+0.00f, +0.25f, -1.00f), //12
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-0.55708605, 0, -0.3713967)), // normal
		vertexPCN(glm::vec3(-0.50f, +0.25f, -0.25f), //13
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-0.55708605, 0, -0.3713967)), // normal
		vertexPCN(glm::vec3(+0.00f, -0.25f, -1.00f), //14
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-0.55708605, 0, -0.3713967)), // normal
		vertexPCN(glm::vec3(-0.50f, -0.25f, -0.25f), //15
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-0.55708605, 0, -0.3713967)), // normal
		//back side of arrow tip
		vertexPCN(glm::vec3(-0.50f, +0.25f, -0.25f), //16
		glm::vec3(+0.50f, +0.50f, +0.50f),//Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(+0.50f, +0.25f, -0.25f), //17
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(-0.50f, -0.25f, -0.25f), //18
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(+0.50f, -0.25f, -0.25f), //19
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		//top side of arrow back
		vertexPCN(glm::vec3(+0.25f, +0.25f, -0.25f), //20
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.25f, +0.25f, +1.00f), //21
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, +0.25f, +1.00f), //22
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, +0.25f, -0.25f), //23
		glm::vec3(+1.00f, +0.00f, +0.00f), //Color
		glm::vec3(+0.00f, +1.00f, +0.00f)), //normal
		//bottom side of arrow back
		vertexPCN(glm::vec3(+0.25f, -0.25f, -0.25f), //24
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.25f, -0.25f, +1.00f), //25
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, -0.25f, +1.00f), //26
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, -0.25f, -0.25f), //27
		glm::vec3(+0.00f, +0.00f, +1.00f), //Color
		glm::vec3(+0.00f, -1.00f, +0.00f)), //normal
		//right side of arrow back
		vertexPCN(glm::vec3(+0.25f, +0.25f, -0.25f), //28
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(+1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.25f, -0.25f, -0.25f), //29
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(+1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.25f, -0.25f, +1.00f), //30
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(+1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(+0.25f, +0.25f, +1.00f), //31
		glm::vec3(+0.60f, +1.00f, +0.00f), //Color
		glm::vec3(+1.00f, 0.00f, +0.00f)), //normal
		//left side of arrow back
		vertexPCN(glm::vec3(-0.25f, +0.25f, -0.25f), //32
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, -0.25f, -0.25f), //33
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, -0.25f, +1.00f), //34
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-1.00f, 0.00f, +0.00f)), //normal
		vertexPCN(glm::vec3(-0.25f, +0.25f, +1.00f), //35
		glm::vec3(+0.00f, +1.00f, +0.00f), //Color
		glm::vec3(-1.00f, 0.00f, +0.00f)), //normal
		//back side of arrow back
		vertexPCN(glm::vec3(-0.25f, +0.25f, +1.00f), //36
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(+0.25f, +0.25f, +1.00f), //37
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(-0.25f, -0.25f, +1.00f), //38
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
		vertexPCN(glm::vec3(+0.25f, -0.25f, +1.00f), //39
		glm::vec3(+0.50f, +0.50f, +0.50f), //Color
		glm::vec3(0.0f, 0.0f, 1.0f)), //normal
	};
	ret.m_vertexCount = NUM_ARRAY_ELEMENTS(verts);

	GLuint indices[] =
	{
		0, 1, 2, // top
		0, 2, 3,
		4, 6, 5, //bottom
		4, 7, 6,
		8, 10, 9, //right side of arrow tip
		8, 11, 10,
		12, 15, 13, //left side of arrow tip
		12, 14, 15,
		16, 19, 17, //back side of arrow tip
		16, 18, 19,
		20, 22, 21, //top side of back of arrow
		20, 23, 22,
		24, 25, 26, //bottom side of back of arrow
		24, 26, 27,
		28, 30, 29, //right side of back of arrow
		28, 31, 30,
		32, 33, 34, //left side of back of arrow
		32, 34, 35,
		36, 39, 37, //back side of back of arrow
		36, 38, 39,
	};

	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	RenderEngine::AddGeometry(verts, ret.m_vertexCount, sizeof(verts[0]), indices, ret.m_indexCount, sizeof(indices[0]), ret);
	return ret;



}
Geometry ShapeGen::MakeSphere(glm::vec3 color)
{
	BuildSphere(color);
	Geometry result;
	result.mode = GL_LINES;
	result.m_vertexCount = NUMSPHEREVERTS;
	result.m_indexCount = NUMSPHEREINDICES;
	result.m_vertsPerPrimitive = 2;  // Make sure you use GL_LINES
	RenderEngine::AddGeometry(((vertexPCN*)sphereVerts), result.m_vertexCount, sizeof(((vertexPCN*)sphereVerts)[0]), ((GLuint*)sphereIndices), result.m_indexCount, sizeof(((GLuint*)sphereFacets)[0]), result);
	return result;
}
void ShapeGen::BuildSphere(glm::vec3 color)
{
	GenerateSphere(sphereFacets, NUMSPHEREITERATIONS);
	for (GLuint j = 0; j < NUMSPHEREFACETS; ++j)
	{
		sphereVerts[3 * j + 0].position = sphereFacets[j].p1;
		sphereVerts[3 * j + 0].color = color;

		sphereVerts[3 * j + 1].position = sphereFacets[j].p2;
		sphereVerts[3 * j + 1].color = color;

		sphereVerts[3 * j + 2].position = sphereFacets[j].p3;
		sphereVerts[3 * j + 2].color = color;

		sphereIndices[6 * j + 0] = 3 * j + 0;
		sphereIndices[6 * j + 1] = 3 * j + 1;
		sphereIndices[6 * j + 2] = 3 * j + 1;
		sphereIndices[6 * j + 3] = 3 * j + 2;
		sphereIndices[6 * j + 4] = 3 * j + 2;
		sphereIndices[6 * j + 5] = 3 * j + 0;
	}
}
void NormalizeXYZ(glm::vec3* p)
{
	float length = sqrt(p->x * p->x + p->y * p->y + p->z * p->z);
	p->x /= length;
	p->y /= length;
	p->z /= length;
	/* Create a triangular facet approximation to a sphere
	Return the number of facets created.
	The number of facets will be (4^iterations) * 8
	*/
}
int ShapeGen::GenerateSphere(Facet3* facets, int iterations)
{
	glm::vec3 p[6] =
	{
		glm::vec3(+0, +0, +1),
		glm::vec3(+0, +0, -1),
		glm::vec3(-1, -1, +0),
		glm::vec3(+1, -1, +0),
		glm::vec3(+1, +1, +0),
		glm::vec3(-1, +1, +0)
	};
	glm::vec3 pa, pb, pc;
	int numFacets = 0;
	int ntold;

	/* Create the level 0 object */
	float a = (float)(1 / sqrt(2.0));
	for (int i = 0; i < 6; i++)
	{
		p[i].x *= a;
		p[i].y *= a;
	}

	facets[0].p1 = p[0];
	facets[0].p2 = p[3];
	facets[0].p3 = p[4];
	facets[1].p1 = p[0];
	facets[1].p2 = p[4];
	facets[1].p3 = p[5];
	facets[2].p1 = p[0];
	facets[2].p2 = p[5];
	facets[2].p3 = p[2];
	facets[3].p1 = p[0];
	facets[3].p2 = p[2];
	facets[3].p3 = p[3];
	facets[4].p1 = p[1];
	facets[4].p2 = p[4];
	facets[4].p3 = p[3];
	facets[5].p1 = p[1];
	facets[5].p2 = p[5];
	facets[5].p3 = p[4];
	facets[6].p1 = p[1];
	facets[6].p2 = p[2];
	facets[6].p3 = p[5];
	facets[7].p1 = p[1];
	facets[7].p2 = p[3];
	facets[7].p3 = p[2];
	numFacets = 8;
	if (iterations < 1) return numFacets;

	/* Bisect each edge and move to the surface of a unit sphere */
	for (int it = 0; it < iterations; it++)
	{
		ntold = numFacets;
		for (int i = 0; i < ntold; i++)
		{
			pa.x = (facets[i].p1.x + facets[i].p2.x) / 2;
			pa.y = (facets[i].p1.y + facets[i].p2.y) / 2;
			pa.z = (facets[i].p1.z + facets[i].p2.z) / 2;
			pb.x = (facets[i].p2.x + facets[i].p3.x) / 2;
			pb.y = (facets[i].p2.y + facets[i].p3.y) / 2;
			pb.z = (facets[i].p2.z + facets[i].p3.z) / 2;
			pc.x = (facets[i].p3.x + facets[i].p1.x) / 2;
			pc.y = (facets[i].p3.y + facets[i].p1.y) / 2;
			pc.z = (facets[i].p3.z + facets[i].p1.z) / 2;

			NormalizeXYZ(&pa);
			NormalizeXYZ(&pb);
			NormalizeXYZ(&pc);

			facets[numFacets].p1 = facets[i].p1;
			facets[numFacets].p2 = pa;
			facets[numFacets].p3 = pc;

			numFacets++;
			facets[numFacets].p1 = pa;
			facets[numFacets].p2 = facets[i].p2;
			facets[numFacets].p3 = pb;

			numFacets++;
			facets[numFacets].p1 = pb;
			facets[numFacets].p2 = facets[i].p3;
			facets[numFacets].p3 = pc;

			numFacets++;
			facets[i].p1 = pa;
			facets[i].p2 = pb;
			facets[i].p3 = pc;
		}
	}

	return numFacets;
}
Geometry ShapeGen::makeLine(glm::vec3 point1, glm::vec3 point2, glm::vec3 color){
	Geometry ret;
	ret.mode = GL_TRIANGLES;
	vertexPCN verts[] =
	{
		vertexPCN(point1, color), vertexPCN(point2, color)
	};
	ret.m_vertexCount = NUM_ARRAY_ELEMENTS(verts);
	for (GLuint i = 0; i < ret.m_vertexCount; ++i){
		verts[i].normal = glm::vec3(0, 1, 0);
	}

	GLuint indices[] = {
		0, 1
	};

	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	ret.m_indexCount = NUM_ARRAY_ELEMENTS(indices);
	RenderEngine::AddGeometry(verts, ret.m_vertexCount, sizeof(verts[0]), indices, ret.m_indexCount, sizeof(indices[0]), ret);
	return ret;


}
Geometry ShapeGen::CreateScene(const char* filename){
	Scene* scene = ReadSceneFile(filename);
	Geometry ret;
	ret.mode = GL_TRIANGLES;
	ret.m_indexCount = scene->numIndices;
	ret.m_vertexCount = scene->numVertices;
	ret.type = scene->format;
	ret.verts = (scene->vertices);
	ret.indices = (scene->indices);
	ret.m_vertexStride = scene->sizeVertex;
	ret.m_indexStride = scene->sizeIndex;
	if (scene->sizeIndex == 4)
	{
		ret.m_use16BitIndices = true;
	}
	RenderEngine::AddGeometry((scene->vertices), ret.m_vertexCount, ret.m_vertexStride, (scene->indices), ret.m_indexCount, ret.m_indexStride, ret);
	
	LOG(Info, "create Scene complete");
	return ret; 
}