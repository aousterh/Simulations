#include <stdlib.h>
#include "RandomWaypointMessageNode.h"

RandomWaypointMessageNode::RandomWaypointMessageNode(Point2D pos, const float &radius, int nodeId, SimTime *simTime, Scenario *scenario): MessageNode(pos, radius, nodeId, simTime)
{
  localTime = 0;
  //  this->vMax = 5;
  //this->vMin = 0;
  //this->pauseTime = 1;
  pause=true;
  target = new Point2D();
  this->scenario = scenario;
}

RandomWaypointMessageNode::~RandomWaypointMessageNode(){}

void RandomWaypointMessageNode::think(SimTime *simTime) 
{
  //Se ho sbattuto contro un muro  calcolo nuva direzione	 
   if(collided==true)
    {
     //pause=true;
     //Calcolo il nuovo vettore velocitÃ 	
     v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	          *(rand()%2<1?(-1):(1));//Segno
     v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	          *(rand()%2<1?(-1):(1));//Segno
      
     //Rapporto la velocitÃ  all'intervallo di tempo 
     v.x=v.x*simTime->getDT();
     v.y=v.y*simTime->getDT();
    }
   else
    {
     //System.out.println(toString());	 
     if(pause==true)
      {
       if(localTime<pauseTime)
        {v.x=0; v.y=0;}
       else
        {
	 pause=false;
	 //Calcolo un nuovo target
	 generateTarget();
	 moveToTarget(simTime);//Mi muovo verso il target
	}	 
     }
    else
     {moveToTarget(simTime);} //Mi muovo verso il target  
   }
  //Incremento il tempo locale  
  localTime+=simTime->getDT();     	
 }//Fine think
   
void RandomWaypointMessageNode::generateTarget()
 {
   //delete(target);
   target->set((float)(((float)rand()/(float)(RAND_MAX))*scenario->getWidth()),
	       (float)(((float)rand()/(float)(RAND_MAX))*scenario->getHeight()));  
 }//Fine generate Target 


void RandomWaypointMessageNode::moveToTarget(SimTime *simTime)
 {
  //Creo un vettore verso il target
   Vector2D toTarget=*target-getPosition();
  //Normalizzo il vettore verso il target
  Vector2D actV=toTarget.unitVector();
   //Calcolo la velocita' 
   float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
	 
    actV*=velocity;
   //Rapporto la velocita'  all'intervallo di tempo scelto
   actV*=simTime->getDT();
   	 
   //Calcolo la distanza dal target	 
   Point2D to=*target;
   float distance=Point2D::distance(getPosition(),to); 
	 
   if(distance<=actV.getLength())
     {v=toTarget;}  
    else
     {v=actV;} 
	
   Point2D nextPoint=getPosition()+v; 
		 
   float finalDist=Point2D::distance(nextPoint,to);
   //Se sono arrivato a destinazione mi fermo
   if(finalDist<=getRadius())
     {
      pause=true;
      localTime=0;
     }
  }//Fine moveToTarget
