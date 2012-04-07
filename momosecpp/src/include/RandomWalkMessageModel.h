#ifndef RANDOMWALKMESSAGEMODEL_H_
#define RANDOMWALKMESSAGEMODEL_H_

#include"MessageModel.h"
#include"MessageNode.h"

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
		      float pauseTime, float vMin, float vMax, bool isPhysical);
	MessageNode *CreateMessageNode(Scenario *scenario, SimTime *time);
 
  // Virtual methods
	
};

#endif /*RANDOMWALKMESSAGEMODEL_H_*/
