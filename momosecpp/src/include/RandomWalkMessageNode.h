#ifndef RANDOMWALKMESSAGENODE_H_
#define RANDOMWALKMESSAGENODE_H_

#include"Node.h"
#include"Point2D.h"
#include"SimTime.h"
#include"MessageNode.h"

class RandomWalkMessageNode: public MessageNode
{
 public:
  RandomWalkMessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time);
  virtual ~RandomWalkMessageNode();

  void think(SimTime *simTime);

 private:
  void testMove(SimTime *simTime);
  void randomMove(SimTime *simTime);
};

#endif /*RANDOMWALKMESSAGENODE_H_*/
