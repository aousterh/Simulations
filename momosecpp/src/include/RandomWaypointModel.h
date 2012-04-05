#ifndef RANDOMWAYPOINTMODEL_H_
#define RANDOMWAYPOINTMODEL_H_

#include"SimpleModel.h"
#include"Scenario.h"
#include"SimTime.h"

class RandomWaypointModel:public SimpleModel
{
public:
	RandomWaypointModel();
	virtual ~RandomWaypointModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
};

#endif /*RANDOMWAYPOINTMODEL_H_*/
