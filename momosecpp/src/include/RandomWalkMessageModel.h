#ifndef RANDOMWALKMESSAGEMODEL_H_
#define RANDOMWALKMESSAGEMODEL_H_

#include"MessageModel.h"
#include"RandomWalkMessageNode.h"

class RandomWalkMessageModel:public MessageModel
{
 public:
	RandomWalkMessageModel();
	virtual ~RandomWalkMessageModel();
	
 protected:
	float vMin;
	float vMax;
	float pauseTime;
  
 public:
	void setModel(int numNodes, float nodeRadius, float antennaRadius, 
		      float pauseTime, float vMin, float vMax, bool isPhysical,
		      float probability, int max_trust_distance);
	RandomWalkMessageNode *CreateMessageNode(Scenario *scenario, SimTime *simTime);
 
  // Virtual methods
	
};

#endif /*RANDOMWALKMESSAGEMODEL_H_*/
