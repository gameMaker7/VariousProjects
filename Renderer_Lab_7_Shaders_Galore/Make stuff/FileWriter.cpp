#include "FileWriter.h"
#include <fstream>
#include <sstream>
std::string ReadTextFileIntoString(const char* filename)
{
	std::stringstream buffer;              // buffer to hold the text
	std::ifstream     inStream(filename); // open input file stream
	if (!inStream.good())
	{
		std::cout << "File failed to load [" << filename << "]\n";
		exit(1);
	}
	buffer << inStream.rdbuf();  // read it all in at once
	return buffer.str();         // the buffer provides the string
}
