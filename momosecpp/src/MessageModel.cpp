#include <queue>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

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

void MessageModel::setup(Scenario *scenario, SimTime *simTime)
{
   int i = 0;
  for (int i = 0; i < numNodes; i++)
    {
      lastNodeId++;
      MessageNode *newNode = CreateMessageNode(scenario, simTime);
      newNode->setAntennaRadius(antennaRadius);
      nodes.push_back(newNode);
      newNode->initFriendships(numNodes);
    }
  
  // seed random number generator
  srand((unsigned) time(NULL));

  // assign friendships
  for (int i = 0; i < numNodes - 1; i++)
    {
      MessageNode *n1 = (MessageNode *) nodes[i];

      for (int j = i + 1; j < numNodes; j++)
	{
	  MessageNode *n2 = (MessageNode *) nodes[j];

	  float random = (float) ((float) rand())/RAND_MAX;
	  if (random <= probability)
	    {
	      n1->setFriendship(j, true);
	      n2->setFriendship(i, true);
	    }
	}
    }

  computeTrustDistances();
}

void MessageModel::think(SimTime *time)
{
  // see if any nodes can exchange messages
  // with current implementation, message can travel a long way in one time step if
  // several nodes are connected :-/
  for (int i = 0; i < numNodes - 1; i++)
    {
      MessageNode *node1 = (MessageNode*) nodes[i];
      for (int j = 0; j < numNodes; j++)
	{
	  MessageNode *node2 = (MessageNode*) nodes[j];
	  if (node1->distanceTo(node2) <= EXCHANGE_DISTANCE &&
	      node1->trustDistance(node2) < max_trust_distance) {
	    node1->exchangeWith(node2);
	    node2->exchangeWith(node1);
	  }
	}
    }
}

void MessageModel::computeTrustDistances()
{
  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *node = (MessageNode *) nodes[i];
      BFS(node);
    }

  int diameter = 0;
  for (int i = 0; i < numNodes - 1; i++)
    {
      MessageNode *node = (MessageNode *) nodes[i];
      int diameter_per_node = 0;
      for (int j = i; j < numNodes; j++)
	{
	  MessageNode *that = (MessageNode *) nodes[j];

	  if (node->trustDistance(that) > diameter_per_node)
	    diameter_per_node = node->trustDistance(that);
	}
      if (diameter_per_node > diameter)
	diameter = diameter_per_node;
    }
  //  printf("diameter: %d\n", diameter);

  /*
  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *node = (MessageNode *) nodes[i];
      printf("\nNode: %d\n", i);
      for (int j = 0; j < numNodes; j++)
	{
	  printf("%d ", node->trust_distances[j]);
	}
	}*/
}

void MessageModel::BFS(MessageNode *root)
{
  // queue of <nodeId, distance>
  queue<pair<int, int> > nodeIds;

  // start with all of root's neighbors on the queue
  for (int i = 0; i < numNodes; i++)
    {
      if (root->trust_distances[i] == 1)
	nodeIds.push(make_pair(i, 1));
    }

  while (nodeIds.size() > 0)
    {
      pair<int, int> current = (pair<int, int>) nodeIds.front();
      nodeIds.pop();
      int currentNodeId = current.first;
      int currentDistance = current.second;
      MessageNode *node = (MessageNode *) nodes[currentNodeId];

      if (root->trust_distances[currentNodeId] == -1)
	root->trust_distances[currentNodeId] = currentDistance;

      for (int i = 0; i < numNodes; i++)
	{
	  /* we only recurse and compute for trust distances of 1 */
	  if ((node->trust_distances[i] == 1) && (root->trust_distances[i] == -1)) // aka we haven't visited i before, but currentNodeId is friends with them
	    {
	      nodeIds.push(make_pair(i, currentDistance + 1));	      
	    }
	}
    }

}


