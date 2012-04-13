#ifndef RANDOMWAYPOINTMESSAGENODE_H_
#define RANDOMWAYPOINTMESSAGENODE_H_

#include"Node.h"
#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"SimTime.h"
#include"MessageNode.h"

class RandomWaypointMessageNode: public MessageNode
{
 protected:
  bool pause;
  Point2D *target;
  Scenario *scenario;	
	
 public:
  RandomWaypointMessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time, Scenario *scenario);
  virtual ~RandomWaypointMessageNode();

  void think(SimTime *simTime);

 private:
  void generateTarget();
  void moveToTarget(SimTime *simTime);
};

#endif /*RANDOMWAYPOINTMESSAGENODE_H_*/
