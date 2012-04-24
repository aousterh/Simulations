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
			    float probability, int node_trust_distance,
			    int msg_trust_distance, int node_exchange_num,
			    int msg_exchange_num, float percent_adversaries,
			    float adversary_probability, float adversary_msg_creation_probability,
			    float collaborator_msg_creation_probability)
{
  this->numNodes = numNodes;
  this->nodeRadius = nodeRadius;
  this->antennaRadius = antennaRadius;
  this->pauseTime = pauseTime;
  this->vMax = vMax;
  this->vMin = vMin;
  this->EXCHANGE_DISTANCE = antennaRadius; // use antenna radius as exchange distance for now
  this->probability = probability;
  this->node_trust_distance = node_trust_distance;
  this->msg_trust_distance = msg_trust_distance;
  this->node_exchange_num = node_exchange_num;
  this->msg_exchange_num = msg_exchange_num;
  this->num_adversaries = percent_adversaries * numNodes;
  this->adversary_probability = adversary_probability;
  this->adversary_msg_creation_probability = adversary_msg_creation_probability;
  this->collaborator_msg_creation_probability = collaborator_msg_creation_probability;

  isPhysical = true;
  setThinkerProp(true);

  srand((unsigned) time(NULL));
}


void MessageModel::setup(Scenario *scenario, SimTime *simTime)
{
  for (int i = 0; i < numNodes; i++)
    {
      lastNodeId++;
      MessageNode *newNode = CreateMessageNode(scenario, simTime);
      newNode->setAntennaRadius(antennaRadius);
      nodes.push_back(newNode);
      // the first num_adversaries are adversaries, rest are collaborators
     
      if (i < num_adversaries)
	{
	  newNode->setType(ADVERSARY);
	  // adversaries start with 5 messages - make this configurable!!! start fixed
	  for (int i = 0; i < 5; i++)
	    newNode->createNewMessage();
	}
      else
	{
	  newNode->setType(COLLABORATOR);
	  newNode->createNewMessage();
	}
      newNode->initFriendships(numNodes, num_adversaries);
    }

  // check stuff
  /*  printf("CHECKING FRIENDSHIPS\n");
  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *node = (MessageNode *) nodes[i];
      printf("\nfriends of node %d: ", i);
      for (int j = 0; j < numNodes; j++)
	printf("%d, ", node->trust_distances[j]);
	}*/



  /*  // the average probability that a node makes a friendship
  double average_p = percent_adversaries * adversary_probability + 
    (1 - percent_adversaries) * probability;

  // seed random number generator
 // srand((unsigned) time(NULL));

  // assign friendships
  for (int i = 0; i < numNodes - 1; i++)
    {
      MessageNode *n1 = (MessageNode *) nodes[i];

      for (int j = i + 1; j < numNodes; j++)
	{
	  MessageNode *n2 = (MessageNode *) nodes[j];

	  double friend_p = n1->getP() * n2->getP() / average_p;

	  float random = (float) ((float) rand())/RAND_MAX;
	  if (friend_p > random)
	    {
	      n1->setFriendship(j, true);
	      n2->setFriendship(i, true);
	    }
	}
    }

    computeTrustDistances();*/

  /*    for (int i = 0; i < numNodes; i++)
    {
      MessageNode *n = (MessageNode *) nodes[i];
      printf("\nfriends of: %d\n", i);

      for (int j = 0; j < numNodes; j++)
	{
	  printf("%d, ", n->trust_distances[j]);
	}
	}*/
}

void MessageModel::think(SimTime *simTime)
{
  // TODO: any node could be receiving from many others at once, although
  // each only sends to one other node at once
  // double ADVERSARY_MSG_CREATION_PROBABILITY = 0.1;

  // SIMPLEST MODEL: NO DYNAMIC CREATION OF MESSAGES

  /*
  // adversaries create new messages
  for (int i = 0; i < numNodes * percent_adversaries; i++)
    {
      MessageNode *node = (MessageNode*) nodes[i];
      // create new message with some probability
      double random = ((float) rand()) / RAND_MAX;
      if (random < adversary_msg_creation_probability)
	node->createNewMessage();
    }
  */
  /*  if (t == 500)
    {
      // have normal nodes create their messages if at t=100
      for (int i = numNodes * percent_adversaries; i < numNodes; i++)
	{
	  MessageNode *node = (MessageNode *) nodes[i];
	  node->createNewMessage();
	}
	}*/
  /*  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *n1 = (MessageNode *) nodes[i];
      printf("Node %d: %f %f\n", n1->getNodeId(), n1->getPosition().x, n1->getPosition().y);
    }
    printf("\n");*/

  /*  static int t = 0;
  printf("TIMESTEP: %d\n", t);
  t++;*/


  // push to one node at a time, selected randomly from those within range
  for (int i = 0; i < numNodes; i++)
    {
      MessageNode *node1 = (MessageNode*) nodes[i];
      
      // obtain nodes within physical distance, to determine
      // which node to exchange with
      vector<Node*> potentials;
      for (int j = 0; j < numNodes; j++)
	{
	  if (j != i)
	    {
	      MessageNode *node2 = (MessageNode*) nodes[j];
	      if (node1->distanceTo(node2) <= EXCHANGE_DISTANCE) {
		potentials.push_back(node2);
	      }
	    }
	}

      // TODO: messages could travel many hops in one timestep if
      // there are big groups of connected components . . . does this happen?
      // choose a random node to send messages to - push model

      // only push messages to one node for now
      if (potentials.size() > 0)
	{
	  int index = ((float) rand()) / RAND_MAX * potentials.size();
	  MessageNode *node2 = (MessageNode *) potentials[index];
	  node1->pushMessagesTo(node2, msg_exchange_num);  // send messages to node2
	}
    }
}

/*void MessageModel::computeTrustDistances()
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
	}
	}*/

/*
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
	  // we only recurse and compute for trust distances of 1
	  if ((node->trust_distances[i] == 1) && (root->trust_distances[i] == -1)) // aka we haven't visited i before, but currentNodeId is friends with them
	    {
	      nodeIds.push(make_pair(i, currentDistance + 1));	      
	    }
	}
    }

}*/
