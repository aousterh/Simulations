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
	int node_trust_distance;  // max trust distance of any node we exchange with
	int msg_trust_distance;  // max trust distance of any sender of any msg we recieve
	int node_exchange_num;  // number of nodes to exchange with at once
	int msg_exchange_num;  // number of messages to send at once to another node
	double percent_adversaries;
	double adversary_probability;
	double adversary_msg_creation_probability;
	double collaborator_msg_creation_probability;
  
 public:
	void setModel(int numNodes, float nodeRadius, float antennaRadius,
		      float pauseTime, float vMin, float vMax, bool isPhysical,
		      float probability, int node_trust_distance,
		      int msg_trust_distance, int node_exchange_num,
		      int msg_exchange_num,  float percent_adversaries,
		      float adversary_probability, float adversary_msg_creation_probability,
		      float collaborator_msg_creation_probability);
	void setup(Scenario *scenario, SimTime *simTime);
	void think(SimTime *simTime);

 private:
	void computeTrustDistances();
	void BFS(MessageNode *root);

	// Virtual methods
	virtual MessageNode *CreateMessageNode(Scenario *scenario, SimTime *time){}
};

#endif /*MESSAGEMODEL_H_*/
