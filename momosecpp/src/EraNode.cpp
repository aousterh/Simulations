#include "EraNode.h"

#include <stdlib.h>
#include <time.h>

EraNode::EraNode(Point2D pos,const float &radius,Point2D *refPoint,vector<HotSpot*> *hotSpots): Node(pos,radius) 
   {
    	type=1;
    //Variabli condivise
    vMax=5;//Velocita'  massima 
    vMin=0;//velocita'  minima 
    localTime=0;
    
    //Variabili randomWalk
    interval=1;//Intervallo di tempo prima del cambio di direzione
    
    //Variabili PursueHide
	this->refPoint=refPoint;
	alpha=0.8f;//Peso del vettore verso il target
    
    //Variabili HotSpot
	this->hotSpots=hotSpots;
	targetSpot=NULL;
	pauseInSpotTime=5;//Pausa del nodo sopra l'hotSPot
	pauseInSpot=true;


    //Imposto il seme per generare i numeri casuali
    srand((unsigned)time(NULL));
   }//Fine costruttore

EraNode::~EraNode(){}

void EraNode::think(SimTime *simTime) 
 {
  if(type==1)	 
    nomadicWalk(simTime);
   else
    if(type==2)
     randomWalk(simTime);
    else
     hotSpotWalk(simTime); 	
  //Incremento il tempo locale  
  localTime+=simTime->getDT();
 }//Fine think


void EraNode::setType(int newType)
  {
   if(newType==1)
    {type=1; setColor(Color(255,255,0));}
   else
    if(newType==2)
     {type=2; setColor(Color(255,0,0));}
     else
      {type=3; setColor(Color(0,0,255));} 
   }//Fine setType 


//Metodi per il randomWalk
 void EraNode::randomWalk(SimTime *simTime)
 {
  if((localTime>interval)||(collided==true)) 
   {	
    //Calcolo il nuovo vettore velocita' 	
    v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
    v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
    //Rapporto la velocita'  all'intervallo di tempo 
    v*=simTime->getDT();
    localTime=0;  
   }  	 
  
 }//Fine randomWalk 


//Metodi per il NomadicModel
void EraNode::nomadicWalk(SimTime *simTime)
 {
  //Se ho sbattuto contro un muro calcolo nuova direzione	 
  if(collided==true)
   {resolveCollision(simTime);}  
  else
   {	
    //Imposto una velocita'
    float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
    
    //Vettore verso target
    Vector2D toTarget=*refPoint-getPosition();
    toTarget.normalize();
    //Moltiplico per velocita'  e "peso"
    toTarget*=velocity;
    
    
      //Vettore casuale 
      Vector2D randomVector=randomUnitVector();
      randomVector*=velocity;
    
      //Per calcolare la nuova velocitÃ  faccio 
      //una combinazione lineare dei due vettori
      toTarget*=alpha;
      randomVector*=(1-alpha);
      //Calcolo il nuovo vettore velocita'
      v=toTarget+randomVector;
	
     
      //Rapporto la velocita'  all'intervallo di tempo 
      v*=simTime->getDT();
     }      
 }//Fine nomadic   
   
//Metodi per l'HotSpotModel
void EraNode::hotSpotWalk(SimTime *simTime)
 {
  //Se ho sbattuto contro un muro  calcolo nuva direzione	 
   if(collided==true)
    resolveCollision(simTime);
  else
   {
    if(pauseInSpot==true)
       {
        if(localTime<pauseInSpotTime)
	    {v.x=0; v.y=0;}
	   else
	    {
	     pauseInSpot=false;
	     
	     //Calcolo un nuovo target
	     targetSpot=nextSpot();
	     
	     if(targetSpot==NULL)
	    	pauseInSpot=true;
	     else
	      moveToTarget(simTime);//Mi muovo verso il target
	     }	 
	   }
	  else
	   {moveToTarget(simTime);} //Mi muovo verso il target    
    }	
 }//Fine hotSpotWalk

 void EraNode::moveToTarget(SimTime *simTime)
  {
   //Calcolo il target
   Point2D target=targetSpot->getPosition();  	 
   
   //Creo un vettore verso il target
   Vector2D toTarget=target-getPosition();
   //Normalizzo il vettore verso il target
   Vector2D actV=toTarget.unitVector();
   //Calcolo la velocitÃ  e la direzione del nodo
   float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
   actV*=velocity;
     
   
   //Rapporto la velocitÃ  all'intervallo di tempo scelto
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
		pauseInSpot=true;
		localTime=0;
	   } 
 }//Fine moveToTarget 


HotSpot* EraNode:: nextSpot()
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
 
HotSpot* EraNode::nearestSpot()
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



Vector2D EraNode::randomUnitVector()
  {
    Vector2D randomVect;
   randomVect.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   randomVect.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   return randomVect;
  }//Fine randomVector
 
void EraNode::resolveCollision(SimTime *simTime)
  {
   //Calcolo il nuovo vettore velocita'
   v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
	     
   //Rapporto la velocita'  all'intervallo di tempo 
   v*=simTime->getDT();
  }//Fine resolveCollision
