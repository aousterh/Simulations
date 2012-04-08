#ifndef MESSAGEMODEL_H_
#define MESSAGEMODEL_H_

#include"Model.h"
#include"MessageNode.h"

class MessageModel:public Model
{
 public:
	MessageModel();
	virtual ~MessageModel();
	
 protected:
        int numNodes;
        float nodeRadius;
        float antennaRadius;
	int lastNodeId;  // last used node id
	float EXCHANGE_DISTANCE;  // TODO: read this in from parser?
  
 public:
	void setModel(float exchangeDistance);
	void setup(Scenario *scenario, SimTime *simTime);
	void think(SimTime *simTime);

	// Virtual methods
	virtual MessageNode *CreateMessageNode(Scenario *scenario, SimTime *time){}
};

#endif /*MESSAGEMODEL_H_*/