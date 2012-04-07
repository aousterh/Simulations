#include <stdlib.h>
#include "RandomWalkMessageNode.h"

RandomWalkMessageNode::RandomWalkMessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time): MessageNode(pos, radius, nodeId, time)
{
  this->localTime = 0;
  this->vMax = 5;
  this->vMin = 0;
  this->pauseTime = 1;
}

RandomWalkMessageNode::~RandomWalkMessageNode(){}

void RandomWalkMessageNode::think(SimTime *simTime) 
{
  randomMove(simTime);	
}

void RandomWalkMessageNode::randomMove(SimTime *simTime)
{
  if((localTime >= pauseTime) || (collided == true)) 
    {	
      v.x = (float)((float)rand()/(float)(RAND_MAX)) * (vMax + vMin)
   	                * (rand() % 2 < 1 ? (-1) : (1));
      v.y = (float)((float)rand()/(float)(RAND_MAX)) * (vMax + vMin)
   	                * (rand() %2 < 1 ? (-1) : (1));
     
      v*=simTime->getDT();
	 
      localTime=0;  
    }	
  
  localTime+=simTime->getDT();    
 }
