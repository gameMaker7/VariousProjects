#include <forward_list>
typedef void(*functionPtr)(void);
typedef float(*functionPtr2)(char*);
void myCallback()
{
	printf("You rang\n");
}
float meCallback(char* msg)
{
	printf("Stop calling - %s\n", msg);
	return 5.34f;
}
int main(int argc, char* argv[])
{
	printf("Hello from: [%s]\n", argv[0]);
	myCallback();
	functionPtr call = myCallback;
	call();
	float (*floatCall) (char*);
	floatCall = meCallback;
	floatCall("gra");
	functionPtr2 stopCalling = meCallback;
	stopCalling("meow");

	functionPtr2 shawns[42];

	for (int j = 0; j < 42; ++j)
	{
		shawns[j] = meCallback;
		shawns[j]("j");
	}
}

