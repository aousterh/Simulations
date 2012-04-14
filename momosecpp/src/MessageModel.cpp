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

void MessageModel::setModel(int numNodes, float nodeRadius, float antennaRadius,
			    float pauseTime, float vMin, float vMax, bool isPhysical,
			    float probability, int max_trust_distance, 
			    int node_exchange_num, int msg_exchange_num)
{
  this->numNodes = numNodes;
  this->nodeRadius = nodeRadius;
  this->antennaRadius = antennaRadius;
  this->pauseTime = pauseTime;
  this->vMax = vMax;
  this->vMin = vMin;
  this->EXCHANGE_DISTANCE = antennaRadius; // use antenna radius as exchange distance for now
  this->probability = probability;
  this->max_trust_distance = max_trust_distance;
  this->node_exchange_num = node_exchange_num;
  this->msg_exchange_num = msg_exchange_num;

  isPhysical = true;
  setThinkerProp(true);
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

void MessageModel::think(SimTime *simTime)
{
  // TODO: any node could be receiving from many others at once, although
  // each only sends to one other node at once


  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *node1 = (MessageNode*) nodes[i];
      
      // obtain nodes within physical and trust distances
      vector<Node*> potentials;
      for (int j = 0; j < numNodes; j++)
	{
	  if (j != i)
	    {
	      MessageNode *node2 = (MessageNode*) nodes[j];
	      if (node1->distanceTo(node2) <= EXCHANGE_DISTANCE &&
		  node1->trustDistance(node2) <= max_trust_distance &&
		  node1->trustDistance(node2) > 0) {
		potentials.push_back(node2);
	      }
	    }
	}

      // TODO: messages could travel many hops in one timestep if
      // there are big groups of connected components . . . does this happen?
      // choose a random node to send messages to - push model
      int exchanges_per_t = 4;
      int exchanges_left = exchanges_per_t;
      while (exchanges_left > 0 && potentials.size() > 0)
	{
	  int index = ((float) rand()) / RAND_MAX * potentials.size();
	  MessageNode *node2 = (MessageNode *) potentials[index];
	  node1->exchangeWith(node2);  // send messages to node2
	  potentials.erase(potentials.begin() + index);
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
  printf("diameter: %d\n", diameter);

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


