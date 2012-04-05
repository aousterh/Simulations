#include "HotSpotNode.h"

#include <stdlib.h>
#include <time.h>
#include<iostream>


HotSpotNode::HotSpotNode(Point2D pos,const float &radius,vector<HotSpot*>*hotSpots): SimpleNode(pos,radius) 
   {
    this->hotSpots=hotSpots;
    vMax=5;
    vMin=1;
    localTime=0;
    pauseTime=2;
    pause=true;
    targetSpot=NULL;
   }//Fine costruttore

HotSpotNode::~HotSpotNode(){}


void HotSpotNode::think(SimTime *simTime)
  {
   //Se ho sbattuto contro un muro  calcolo la nuova direzione	 
   if(collided==true)
    resolveCollision(simTime);
  else
   {
    if(pause==true)
       {
        if(localTime<pauseTime)
	    {v.x=0; v.y=0;}
	   else
	    {
	     pause=false;
	     
	     //Calcolo un nuovo target
	     targetSpot=nextSpot();
	     
	     if(targetSpot==NULL)
	    	pause=true;
	     else
	      moveToTarget(simTime);//Mi muovo verso il target
	     }	 
	   }
	  else
	   {moveToTarget(simTime);} //Mi muovo verso il target   
    }	
   
   //Incremento il tempo locale  
   localTime+=simTime->getDT();
  }//Fine think
 
 
 void HotSpotNode::moveToTarget(SimTime *simTime)
  {
   //Calcolo il target
   Point2D target=targetSpot->getPosition();  	 
   
   //Creo un vettore verso il target
   Vector2D toTarget=target-getPosition();
   //Normalizzo il vettore verso il target
   Vector2D actV=toTarget.unitVector();
   //Calcolo la velocita'  e la direzione del nodo
   float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
   actV*=velocity;
     
   
   //Rapporto la velocita'  all'intervallo di tempo scelto
   actV*=simTime->getDT();
   
	 
    //Calcolo la distanza dal target	 
    float distance=Point2D::distance(getPosition(),target); 
	 
	if(distance<=actV.getLength())
            {v=toTarget;}  
	  else
	   {v=actV;} 
	
	 Point2D nextPoint=getPosition()+v; 
	 
	 float finalDist=Point2D::distance(nextPoint,target);
     //Se sono arrivato a destinazione mi fermo
	 if(finalDist<=getRadius())
	   {
	     pause=true;
	     localTime=0;
	   } 
 }//Fine movingToTarget 
 
 void HotSpotNode::resolveCollision(SimTime *simTime)
  {
   //Calcolo il nuovo vettore velocita'	
   v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	          *(rand()%2<1?(-1):(1));//Segno
     v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	          *(rand()%2<1?(-1):(1));//Segno
	     
   //Rapporto la velocita'  all'intervallo di tempo 
   v*=simTime->getDT();
  }//Fine resolveCollision
 
HotSpot* HotSpotNode:: nextSpot()
 {
  if(targetSpot==NULL)
   return nearestSpot();  
  else
   {
    vector<Connection*>*connections=targetSpot->getConnections();
	 if(connections->size()>0)
	  {
	   Connection *firstConn=connections->at(0);
	   return firstConn->getEndSpot();
	  }
	 else
	  return NULL;	 
  }   
 }//Fine nextSpot 
 
HotSpot* HotSpotNode::nearestSpot()
  {
   HotSpot *nearest=NULL;
   float near;
   
   
   if(hotSpots->size()>0)
    {
     nearest=hotSpots->at(0);
     near=Point2D::distance(getPosition(),nearest->getPosition());
    }
   else
    return NULL;
   
   vector<HotSpot*>::iterator iSpot;
   HotSpot *nextSpot;
   for(iSpot=hotSpots->begin();iSpot!=hotSpots->end();iSpot++)
    {
	 nextSpot=*iSpot;
	 float distance=Point2D::distance(getPosition(),nextSpot->getPosition());
	 if(distance<near)
	  {
	   nearest=nextSpot;
	   near=Point2D::distance(getPosition(),nextSpot->getPosition());	   	 
	  }	 
    }   
   return nearest;	 
  }//Fine nearestSpot
