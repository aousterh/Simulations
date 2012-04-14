#ifndef RANDOMWALKMESSAGEMODEL_H_
#define RANDOMWALKMESSAGEMODEL_H_

#include"MessageModel.h"
#include"RandomWalkMessageNode.h"

class RandomWalkMessageModel:public MessageModel
{
 public:
	RandomWalkMessageModel();
	virtual ~RandomWalkMessageModel();
  
 public:
	RandomWalkMessageNode *CreateMessageNode(Scenario *scenario, SimTime *simTime);
 
  // Virtual methods
	
};

#endif /*RANDOMWALKMESSAGEMODEL_H_*/
