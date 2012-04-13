#ifndef RANDOMWAYPOINTMESSAGEMODEL_H_
#define RANDOMWAYPOINTMESSAGEMODEL_H_

#include"MessageModel.h"
#include"RandomWaypointMessageNode.h"

class RandomWaypointMessageModel:public MessageModel
{
 public:
	RandomWaypointMessageModel();
	virtual ~RandomWaypointMessageModel();
	
 protected:
	float vMin;
	float vMax;
	float pauseTime;
  
 public:
	void setModel(int numNodes, float nodeRadius, float antennaRadius, 
		      float pauseTime, float vMin, float vMax, bool isPhysical,
		      float probability, int max_trust_distance);
	RandomWaypointMessageNode *CreateMessageNode(Scenario *scenario, SimTime *simTime);
 
  // Virtual methods
	
};

#endif /*RANDOMWAYPOINTMESSAGEMODEL_H_*/
