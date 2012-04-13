#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "RandomWalkMessageNode.h"

RandomWalkMessageNode::RandomWalkMessageNode(Point2D pos, const float &radius, int nodeId, SimTime *simTime): MessageNode(pos, radius, nodeId, simTime)
{
  localTime = 0;
 
  //  srand((unsigned) time(NULL));
}

RandomWalkMessageNode::~RandomWalkMessageNode(){}

void RandomWalkMessageNode::think(SimTime *simTime) 
{
  randomMove(simTime);	
}

void RandomWalkMessageNode::randomMove(SimTime *simTime)
{
  static int count = 0;

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
