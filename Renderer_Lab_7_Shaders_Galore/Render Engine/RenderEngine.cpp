#include "RenderEngine.h"
#include <forward_list>
#include "../Make stuff/FileWriter.h"
#include "../Make stuff/LogWriter.h"
BufferManager RenderEngine::storage;
GLuint RenderEngine::vertexShaderIDs[MAX_SHADER_SETUPS];
GLuint RenderEngine::fragmentShaderIDs[MAX_SHADER_SETUPS];
GLuint RenderEngine::programIDs[MAX_SHADER_SETUPS];
int RenderEngine::SetupsRan = 0;
RenderEngine::RenderEngine()
{
	
}

void RenderEngine::Initialize(){
	storage.Initialize();
}
void RenderEngine::Shutdown(){
	storage.Shutdown();
}
void RenderEngine::AddGeometry(void* vertices, unsigned vertexCount, unsigned vertexSize, void* indices, unsigned indexCount, unsigned indexSize, Geometry& mesh){
	storage.AddGeometry(vertices, vertexCount, vertexSize,indices,indexCount, indexSize, &mesh);
}

void RenderEngine::Draw(const glm::mat4& c, const glm::mat4& d)
{
	DrawAllVertexBuffers(c, d);
}

GLuint RenderEngine::AddShader(char* shaderName)
{
	char vertexShader[80];
	strcpy_s(vertexShader, "VertexShaderCode_");
	strcat_s(vertexShader, shaderName);
	strcat_s(vertexShader, ".shader");

	char FragShader[80];
	strcpy_s(FragShader, "FragShaderCode_");
	strcat_s(FragShader, shaderName);
	strcat_s(FragShader, ".shader");

	vertexShaderIDs[SetupsRan] = glCreateShader(GL_VERTEX_SHADER);
	fragmentShaderIDs[SetupsRan] = glCreateShader(GL_FRAGMENT_SHADER);

	const char* adapt[1];
	std::string file = ReadTextFileIntoString(vertexShader);
	adapt[0] = file.c_str();
	glShaderSource(vertexShaderIDs[SetupsRan], 1, adapt, 0);
	file = ReadTextFileIntoString(FragShader);
	adapt[0] = file.c_str();
	glShaderSource(fragmentShaderIDs[SetupsRan], 1, adapt, 0);


	glCompileShader(vertexShaderIDs[SetupsRan]);
	glCompileShader(fragmentShaderIDs[SetupsRan]);

	checkCompile();

	programIDs[SetupsRan] = glCreateProgram();
	glAttachShader(programIDs[SetupsRan], vertexShaderIDs[SetupsRan]);
	glAttachShader(programIDs[SetupsRan], fragmentShaderIDs[SetupsRan]);
	glLinkProgram(programIDs[SetupsRan]);
	checkLink();

	GLuint res = programIDs[SetupsRan];
	SetupsRan++;
	if (SetupsRan >= MAX_SHADER_SETUPS)
	{
		printf("Increase Shader Capacity");
	}
	return res;
}
void RenderEngine::checkCompile(){
	LOG(Info, "Check compile start");
	GLint CompileStatus;
	glGetShaderiv(vertexShaderIDs[SetupsRan], GL_COMPILE_STATUS, &CompileStatus);
	if (CompileStatus != GL_TRUE){
		printf("Vertex shaders issues: # - %d \n", vertexShaderIDs[SetupsRan]);
		GLint infoLogLength;
		glGetShaderiv(vertexShaderIDs[SetupsRan], GL_INFO_LOG_LENGTH, &infoLogLength);
		GLchar* buffer = new GLchar[infoLogLength];
		glGetShaderInfoLog(vertexShaderIDs[SetupsRan], infoLogLength, nullptr, buffer);
		std::cout << buffer << std::endl;
		delete[] buffer;
		LOG(Severe, "Cannot compile Vertex shader");
		exit(1);
	}
	glGetShaderiv(fragmentShaderIDs[SetupsRan], GL_COMPILE_STATUS, &CompileStatus);
	if (CompileStatus != GL_TRUE){
		printf("Fragment shader Issues: # - %d \n", fragmentShaderIDs[SetupsRan]);
		GLint infoLogLength;
		glGetShaderiv(fragmentShaderIDs[SetupsRan], GL_INFO_LOG_LENGTH, &infoLogLength);
		GLchar* buffer = new GLchar[infoLogLength];
		glGetShaderInfoLog(fragmentShaderIDs[SetupsRan], infoLogLength, nullptr, buffer);
		std::cout << buffer << std::endl;
		delete[] buffer;
		LOG(Severe, "Cannot compile Frag shader:");
		exit(1);
	}
	LOG(Info, "Check compile complete");
}
void RenderEngine::checkLink(){
	LOG(Info, "Check Link start");
	GLint linkstatus;
	glGetProgramiv(programIDs[SetupsRan], GL_LINK_STATUS, &linkstatus);
	if (linkstatus != GL_TRUE){
		GLint infoLogLength;
		glGetProgramiv(programIDs[SetupsRan], GL_INFO_LOG_LENGTH, &infoLogLength);
		GLchar* buffer = new GLchar[infoLogLength];
		glGetProgramInfoLog(programIDs[SetupsRan], infoLogLength, nullptr, buffer);
		std::cout << buffer << std::endl;
		delete[] buffer;
		exit(1);
	}
	glValidateProgram(programIDs[SetupsRan]);
	glGetProgramiv(programIDs[SetupsRan], GL_VALIDATE_STATUS, &linkstatus);
	if (linkstatus != GL_TRUE){
		std::cout << "Program Invalid: # - " << programIDs[SetupsRan] << std::endl;
	}
	else{
		std::cout << "Valid Program" << std::endl;
	}
	LOG(Info, "Check Link complete");
}
void RenderEngine::AddRenderInfo(Renderable* info){
	storage.AddRenderInfo(info);
}

void RenderEngine::DrawAllVertexBuffers(const glm::mat4 &c, const glm::mat4 &d){
	for (int i = 0; i < storage.m_numVertexBuffers; ++i)
	{
		glBindBuffer(GL_ARRAY_BUFFER, storage.m_bufferPool[i].m_vert_ID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, storage.m_bufferPool[i].M_index_ID);
		
		storage.m_bufferPool[i].Draw(c, d);
	}
}
