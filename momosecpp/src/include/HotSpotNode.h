#ifndef HOTSPOTNODE_H_
#define HOTSPOTNODE_H_

#include<vector>

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"SimpleNode.h"
#include"SimTime.h"
#include"Scenario.h"
#include"HotSpot.h"

class HotSpotNode: public SimpleNode
{
 private:	
  bool pause;
  float localTime;
  vector<HotSpot*>*hotSpots;
  HotSpot *targetSpot;
	
 
 public:
  HotSpotNode(Point2D pos,const float &radius,vector<HotSpot*>*hotSpots);
  virtual ~HotSpotNode();
	
  //Metodi virtuale ereditati
  void think(SimTime *simTime); 
  
 private:
  void moveToTarget(SimTime *simTime);
  void  resolveCollision(SimTime *simTime);
  HotSpot* nextSpot();
  HotSpot* nearestSpot();
  
  
};

#endif /*RANDOMWAYPOINTNODE_H_*/
