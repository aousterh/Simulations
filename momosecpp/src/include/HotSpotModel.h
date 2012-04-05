#ifndef HOTSPOTMODEL_H_
#define HOTSPOTMODEL_H_

#include"SimpleModel.h"
#include"Scenario.h"
#include"SimTime.h"

class HotSpotModel:public SimpleModel
{
public:
	HotSpotModel();
	virtual ~HotSpotModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
};

#endif /*HOTSPOTMODEL_H_*/
