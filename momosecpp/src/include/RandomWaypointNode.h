#ifndef RANDOMWAYPOINTNODE_H_
#define RANDOMWAYPOINTNODE_H_

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"SimpleNode.h"
#include"SimTime.h"
#include"Scenario.h"

class RandomWaypointNode: public SimpleNode
{
 private:	
  bool pause;
  float localTime;
 
  Point2D *target;
  Scenario *scenario;	
	
public:
	RandomWaypointNode(Point2D pos,const float &radius,Scenario *scenario);
	virtual ~RandomWaypointNode();
	
	//Metodo virtuale ereditati
	void think(SimTime *simTime); 

  private:	
	void generateTarget();
        void moveToTarget(SimTime *simTime);
	
};

#endif /*RANDOMWAYPOINTNODE_H_*/

