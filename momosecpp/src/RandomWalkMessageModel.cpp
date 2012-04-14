#include <stdlib.h>
#include <stdio.h>

#include "RandomWalkMessageModel.h"
#include "RandomWalkMessageNode.h"

RandomWalkMessageModel::RandomWalkMessageModel()
{
  setName("RandomWalkMessageModel");
}

RandomWalkMessageModel::~RandomWalkMessageModel(){} 

RandomWalkMessageNode *RandomWalkMessageModel::CreateMessageNode(Scenario *scenario, SimTime *simTime)
{
  float px = (float) (((float) rand()/(float) RAND_MAX) * scenario->getWidth());
  float py = (float) (((float) rand()/(float) RAND_MAX) * scenario->getHeight());
        
  Point2D initialPos = Point2D(px, py);
  RandomWalkMessageNode *messageNode = 
    new RandomWalkMessageNode(initialPos, nodeRadius, lastNodeId, simTime);
        
  return messageNode;
}
