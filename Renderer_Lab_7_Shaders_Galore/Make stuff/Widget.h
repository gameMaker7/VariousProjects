#ifndef WIDGET_H
#define WIDGET_H
#pragma warning(push)
#pragma warning(disable : 4127)
#pragma warning(disable : 4251)
#include <QtGui\qwidget.h>
#pragma warning(pop)
#include "DebugSlider.h"
#include "Datamanager.h"
#include "GLWindow.h"
class Widget : public QWidget 
{
	Q_OBJECT

public:
	Datamanager model;
	DebugSlider* ambientR;
	DebugSlider* ambientG;
	DebugSlider* ambientB;
	DebugSlider* colorR;
	DebugSlider* colorG;
	DebugSlider* colorB;
	DebugSlider* DomColorR;
	DebugSlider* DomColorG;
	DebugSlider* DomColorB;
	DebugSlider* SpecularColorR;
	DebugSlider* SpecularColorG;
	DebugSlider* SpecularColorB;
	DebugSlider* SpecularExponent;

	Widget(Datamanager*, GLWindow*);

	private slots:
	void sliderValueChanged();
};

#endif