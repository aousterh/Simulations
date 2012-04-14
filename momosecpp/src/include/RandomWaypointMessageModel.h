#ifndef RANDOMWAYPOINTMESSAGEMODEL_H_
#define RANDOMWAYPOINTMESSAGEMODEL_H_

#include"MessageModel.h"
#include"RandomWaypointMessageNode.h"

class RandomWaypointMessageModel:public MessageModel
{
 public:
	RandomWaypointMessageModel();
	virtual ~RandomWaypointMessageModel();

 public:
	RandomWaypointMessageNode *CreateMessageNode(Scenario *scenario, SimTime *simTime);
 
  // Virtual methods
	
};

#endif /*RANDOMWAYPOINTMESSAGEMODEL_H_*/
