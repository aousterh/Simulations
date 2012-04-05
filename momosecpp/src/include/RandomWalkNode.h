#ifndef RANDOMWALKNODE_H_
#define RANDOMWALKNODE_H_

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"SimpleNode.h"
#include"SimTime.h"

class RandomWalkNode: public SimpleNode
{
 private:	
  float localTime;	
	
public:
	RandomWalkNode(Point2D pos,const float &radius);
	virtual ~RandomWalkNode();
	
	//Metodo virtuale ereditati
	void think(SimTime *simTime); 

  private:	
	void testMove(SimTime *simTime);
	void randomMove(SimTime *simTime);
};

#endif /*RANDOMWALKNODE_H_*/
