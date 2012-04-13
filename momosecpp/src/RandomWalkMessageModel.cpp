#include <stdlib.h>
#include <stdio.h>

#include "RandomWalkMessageModel.h"
#include "RandomWalkMessageNode.h"

RandomWalkMessageModel::RandomWalkMessageModel()
{
  setName("RandomWalkMessageModel");
}

RandomWalkMessageModel::~RandomWalkMessageModel(){} 

void RandomWalkMessageModel::setModel(int numNodes, float nodeRadius, float antennaRadius, 
				      float pauseTime, float vMin, float vMax, bool isPhysical,
				      float probability, int max_trust_distance)
{
  ((MessageModel *) this)->setModel(antennaRadius);

  this->numNodes = numNodes;
  this->nodeRadius = nodeRadius;
  this->antennaRadius = antennaRadius;
  this->pauseTime = pauseTime;
  this->vMax = vMax;
  this->vMin = vMin;
  this->probability = probability;
  this->max_trust_distance = max_trust_distance;

  isPhysical = true;
  setThinkerProp(true);
}

RandomWalkMessageNode *RandomWalkMessageModel::CreateMessageNode(Scenario *scenario, SimTime *simTime)
{
  float px = (float) (((float) rand()/(float) RAND_MAX) * scenario->getWidth());
  float py = (float) (((float) rand()/(float) RAND_MAX) * scenario->getHeight());
        
  Point2D initialPos = Point2D(px, py);
  RandomWalkMessageNode *messageNode = 
    new RandomWalkMessageNode(initialPos, nodeRadius, lastNodeId, simTime);
        
  return messageNode;
}
