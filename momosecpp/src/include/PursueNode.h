#ifndef PURSUENODE_H_
#define PURSUENODE_H_

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"Node.h"
#include"SimTime.h"
#include"Scenario.h"

class PursueNode: public Node
{
 private:
    Node *referenceNode;	
    float vMax;
    float vMin;
    float alpha;
  
 
	
public:
	PursueNode(Point2D pos,const float &radius,Node *referenceNode);
	virtual ~PursueNode();
	
        void setParams(float antennaRadius,float vMax,float vMin,float alpha);
 
	//Metodo virtuale ereditati
	void think(SimTime *simTime); 
        
  private:
      void resolveCollision(SimTime *simTime);
      Vector2D randomUnitVector();
        
	
	
	
};

#endif /*PURSUENODE_H_*/
