#include "Widget.h"
#include "LogWriter.h"
#include "Datamanager.h"
#pragma warning(push)
#pragma warning(disable : 4251)
#include <QtGui/qboxlayout.h>
#pragma warning(pop)

Widget::Widget(Datamanager* gameModel, GLWindow* app)
{
	LOG(Info, "Widget init");
	gameModel = &model;
	QVBoxLayout* mainLayout;
	setLayout(mainLayout = new QVBoxLayout);
	//QVBoxLayout* controlsLayout;
	//mainLayout->addLayout(controlsLayout = new QVBoxLayout);
	app->initialize();
	mainLayout->addWidget(app);
	//QHBoxLayout* colorChanger;
	//QHBoxLayout* SpecExponent;
	//QHBoxLayout* diffuseColor;
	//QHBoxLayout* SpecularColor;
	//controlsLayout->addLayout(colorChanger = new QHBoxLayout);
	//controlsLayout->addLayout(SpecExponent = new QHBoxLayout);
	//controlsLayout->addLayout(diffuseColor = new QHBoxLayout);
	//controlsLayout->addLayout(SpecularColor = new QHBoxLayout);
	//colorChanger->addWidget(ambientR = new DebugSlider(0.0f, 1.0f));
	//colorChanger->addWidget(ambientG = new DebugSlider( 0.0f, 1.0f));
	//colorChanger->addWidget(ambientB = new DebugSlider( 0.0f, 1.0f));
	//
	//SpecExponent->addWidget(SpecularExponent = new DebugSlider( 1.0f, 1000.0f, false, 1000));
	//
	//diffuseColor->addWidget(colorR = new DebugSlider(0.0f, 1.0f));
	//diffuseColor->addWidget(colorG = new DebugSlider(0.0f, 1.0f));
	//diffuseColor->addWidget(colorB = new DebugSlider(0.0f, 1.0f));
	//
	//SpecularColor->addWidget(SpecularColorR = new DebugSlider(0.0f, 1.0f));
	//SpecularColor->addWidget(SpecularColorG = new DebugSlider(0.0f, 1.0f));
	//SpecularColor->addWidget(SpecularColorB = new DebugSlider(0.0f, 1.0f));
	
	
	//connect(ambientR, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(ambientG, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(ambientB, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//
	//connect(colorG, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(colorB, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(colorR, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//
	//connect(SpecularColorG, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(SpecularColorB, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(SpecularColorR, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	//connect(SpecularExponent, SIGNAL(valueChanged(float)), this, SLOT(sliderValueChanged()));
	

	model.SpecExponent = 50;  //SpecularExponent->value();
	model.diffuseLight = glm::vec3(.8, .2, .2); //glm::vec3(ambientR->value(), ambientG->value(), ambientB->value());
	model.ambientLight = glm::vec3(.1, .1, .1);  //glm::vec3(colorR->value(), colorG->value(), colorB->value());
	model.SpecularColor = glm::vec3(.2, .2, .2); //glm::vec3(SpecularColorR->value(), SpecularColorG->value(), SpecularColorB->value());
}

void Widget::sliderValueChanged(){
	model.SpecExponent = SpecularExponent->value();
	model.ambientLight = glm::vec3(ambientR->value(), ambientG->value(), ambientB->value());
	model.diffuseLight = glm::vec3(colorR->value(), colorG->value(), colorB->value());
	model.SpecularColor = glm::vec3(SpecularColorR->value(), SpecularColorG->value(), SpecularColorB->value());
}
