#ifndef LOGGER
#define LOGGER

#include <vector>


using std::vector;
using std::string;



enum Severity { Info, Warning, Error, Severe };

#define LOG( severity, message) Logger::Log( severity, message, __FILE__, __LINE__ );
#define END_LOG Logger::shutDown();

#pragma warning ( disable : 4100)

class Logger
{
public:
	Logger();
	~Logger();
	static void StringReplace(string& str, const string& from, const string& to);
	static string Sanitize(string str);
	static void Log(Severity severity, const char* message, const char * logFile, int logLine);
	static void shutDown();

private:
	static vector <string> logList;
	static vector <Severity> severityList;
	static void WriteFile();
};

#pragma warning ( default : 4100)

#endif