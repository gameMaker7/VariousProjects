#include "DT.h"
DT::DT(){}
bool DT::init(){

	int b = QueryPerformanceFrequency(&timeFreq);
	if (!b) return false;
	int x = QueryPerformanceCounter(&timeFreq);
	if (!x){
		return false;
	}
	else{
		newFrame();
		return true;
	}


}
bool DT::shutDown(){ return true; }

void DT::newFrame(){
	QueryPerformanceFrequency(&timeFreq);
	LARGE_INTEGER startTime;
	QueryPerformanceCounter(&startTime);
	LARGE_INTEGER delta;
	delta.QuadPart = startTime.QuadPart - timeLastFrame.QuadPart;
	deltaOfLastFrame = ((float)delta.QuadPart) / timeFreq.QuadPart;
	timeLastFrame.QuadPart = startTime.QuadPart;
}
float DT::timeOfLastFrame(){
	return deltaOfLastFrame;
}
float DT::FBS(){
	return 1 / deltaOfLastFrame;
}