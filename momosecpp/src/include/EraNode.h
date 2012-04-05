#ifndef ERANODE_H_
#define ERANODE_H_

#include<vector>

#include"Point2D.h"
#include"Vector2D.h"
#include"Scenario.h"
#include"Node.h"
#include"SimTime.h"
#include"Scenario.h"
#include"HotSpot.h"

class EraNode: public Node
{
 private:
    int type;
  
    //Variabili condivise
    float vMax;
    float vMin;
    float localTime;
  
    //Variabili randomWalk
    float interval;
  
  //variabli per il Pursue hide model
   Point2D *refPoint;
   float alpha;
  
   //Variabili HotSpot
   vector<HotSpot*> *hotSpots;
   HotSpot *targetSpot;
   float pauseInSpotTime;
   bool pauseInSpot;
  
 public:
       EraNode(Point2D pos,const float &radius,Point2D *refPoint,vector<HotSpot*> *hotSpots);
       virtual ~EraNode();
	
       //Metodo virtuale ereditati
       void think(SimTime *simTime); 
        
       void setType(int newType);

 private:
       void nomadicWalk(SimTime *simTime);
       void randomWalk(SimTime *simTime);       
       void hotSpotWalk(SimTime *simTime);
       void resolveCollision(SimTime *simTime);
       Vector2D randomUnitVector();
       void moveToTarget(SimTime *simTime);
       HotSpot* nearestSpot();
       HotSpot* nextSpot();
 
};

#endif /*ERANODE_H_*/
