#ifndef RANDOMWALKMODEL_H_
#define RANDOMWALKMODEL_H_

#include"SimpleModel.h"
#include"Scenario.h"
#include"SimTime.h"

class RandomWalkModel:public SimpleModel
{
public:
	RandomWalkModel();
	virtual ~RandomWalkModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
};

#endif /*RANDOMWALKMODEL_H_*/
