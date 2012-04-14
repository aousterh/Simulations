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
	float vMin;
	float vMax;
	float pauseTime;
	int lastNodeId;  // last used node id
	float EXCHANGE_DISTANCE;  // TODO: read this in from parser?
	float probability;
	int max_trust_distance;
	int node_exchange_num;
	int msg_exchange_num;
  
 public:
	void setModel(int numNodes, float nodeRadius, float antennaRadius,
		      float pauseTime, float vMin, float vMax, bool isPhysical,
		      float probability, int max_trust_distance, 
		      int node_exchange_num, int msg_exchange_num);
	void setup(Scenario *scenario, SimTime *simTime);
	void think(SimTime *simTime);

 private:
	void computeTrustDistances();
	void BFS(MessageNode *root);

	// Virtual methods
	virtual MessageNode *CreateMessageNode(Scenario *scenario, SimTime *time){}
};

#endif /*MESSAGEMODEL_H_*/
