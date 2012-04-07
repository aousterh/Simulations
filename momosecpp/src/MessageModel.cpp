#include "MessageModel.h"

MessageModel::MessageModel()
{
  lastNodeId = -1;
}

MessageModel::~MessageModel(){} 

void MessageModel::setModel(float exchangeDistance)
{
  EXCHANGE_DISTANCE = exchangeDistance;
}

void MessageModel::setup(Scenario *scenario, SimTime *time)
{
  int i = 0;
  for (int i = 0; i < numNodes; i++)
    {
      lastNodeId++;
      MessageNode *newNode = CreateMessageNode(scenario, time);
      newNode->setAntennaRadius(antennaRadius);
      nodes.push_back(newNode);
    }
}

void MessageModel::think(SimTime *time)
{
  // see if any nodes can exchange messages
  // with current implementation, nodes can travel a long way in one time step if
  // several nodes are connected :-/
  for (int i = 0; i < numNodes - 1; i++)
    {
      MessageNode *node1 = (MessageNode*) nodes[i];
      for (int j = 0; j < numNodes; j++)
	{
	  MessageNode *node2 = (MessageNode*) nodes[j];
	  if (node1->distanceTo(node2) <= EXCHANGE_DISTANCE) {
	    node1->exchangeWith(node2);
	    node2->exchangeWith(node1);
	  }
	}
    }
}
