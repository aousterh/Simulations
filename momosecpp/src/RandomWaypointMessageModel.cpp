#include <stdlib.h>

#include "RandomWaypointMessageModel.h"
#include "RandomWaypointMessageNode.h"

RandomWaypointMessageModel::RandomWaypointMessageModel()
{
  setName("RandomWaypointMessageModel");
}

RandomWaypointMessageModel::~RandomWaypointMessageModel(){}

RandomWaypointMessageNode *RandomWaypointMessageModel::CreateMessageNode(Scenario *scenario, SimTime *simTime)
{
  float px = (float) (((float) rand()/(float) RAND_MAX) * scenario->getWidth());
  float py = (float) (((float) rand()/(float) RAND_MAX) * scenario->getHeight());
        
  Point2D initialPos = Point2D(px, py);
  RandomWaypointMessageNode *messageNode = 
    new RandomWaypointMessageNode(initialPos, nodeRadius, lastNodeId, simTime, scenario);
        
  return messageNode;
}
