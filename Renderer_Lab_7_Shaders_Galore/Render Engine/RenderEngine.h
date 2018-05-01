#ifndef RENDER_ENGINE_H
#define RENDER_ENGINE_H
#include "Renderable.h"
#include "BufferManager.h"
#define MAX_SHADER_SETUPS 20
class RenderEngine
{
public:
	static void Initialize();
	static void Shutdown();
	static void AddGeometry(void* vertices, unsigned vertexCount, unsigned vertexSize, void* indices, unsigned indexCount, unsigned indexSize, Geometry& mesh); // – for indexed data
	static void AddRenderInfo(Renderable* info);
	static void Draw(const glm::mat4 &c, const glm::mat4 &d);
	static GLuint AddShader(char* shaderName);
	static void checkCompile();
	static void checkLink();
private:
	static GLuint vertexShaderIDs[MAX_SHADER_SETUPS];
	static GLuint fragmentShaderIDs[MAX_SHADER_SETUPS];
	static GLuint programIDs[MAX_SHADER_SETUPS];
	static int SetupsRan;
	static BufferManager storage;
	RenderEngine();// – prevents anyone from constructing one of these
	static void DrawAllVertexBuffers(const glm::mat4 &c, const glm::mat4 &d);// – low level draw.Loops over all the buffers and Draw()s all the enabled RenderInfo’s in the current buffer
};

#endif