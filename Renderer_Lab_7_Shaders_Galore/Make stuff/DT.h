#ifndef DT_H
#define DT_H
#include <Windows.h>
class DT
{
	LARGE_INTEGER timeFreq;
	LARGE_INTEGER elapsedTime;
	LARGE_INTEGER timeLastFrame;
	float deltaOfLastFrame;
public:

	DT();
	bool init();
	bool shutDown();
	void newFrame();
	float timeOfLastFrame();
	float FBS();
};

#endif