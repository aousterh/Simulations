#ifndef NOMADICNODE_H_
#define NOMADICNODE_H_

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"Node.h"
#include"SimTime.h"
#include"Scenario.h"

class NomadicNode: public Node
{
 private:
   Point2D *refPoint;
   float vMax;
   float vMin;
   float alpha;
  
 
	
public:
	NomadicNode(Point2D pos,const float &radius,Point2D *refPoint);
	virtual ~NomadicNode();
	
        void setParams(float antennaRadius,float vMax,float vMin,float alpha);
 
	//Metodo virtuale ereditati
	void think(SimTime *simTime); 
        
  private:
      void resolveCollision(SimTime *simTime);
      Vector2D randomUnitVector();
        
	
	
	
};

#endif /*PURSUENODE_H_*/
