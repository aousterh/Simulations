#include "PursueNode.h"

#include <stdlib.h>
#include <time.h>

PursueNode::PursueNode(Point2D pos,const float &radius,Node *referenceNode): Node(pos,radius) 
   {
    //Salvo un riferimento al nodo di riferimento
    this->referenceNode=referenceNode;
    vMax=5;
    vMin=1;
    alpha=0.7f;
    //Imposto il seme per generare i numeri casuali
    srand((unsigned)time(NULL));
   }//Fine costruttore

PursueNode::~PursueNode(){}

void PursueNode::think(SimTime *simTime) 
 {
  //Se ho sbattuto contro un muro calcolo nuova direzione	 
  if(collided==true)
   {resolveCollision(simTime);}  
  else
   {	
    //Imposto una velocita'
    float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
    
     //Vettore verso target
    Point2D refPoint=referenceNode->getPosition();
    Vector2D toTarget=refPoint-getPosition();
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
 }//Fine think
   

Vector2D PursueNode::randomUnitVector()
  {
    Vector2D randomVect;
   randomVect.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   randomVect.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   return randomVect;
  }//Fine randomVector
 
void PursueNode::resolveCollision(SimTime *simTime)
  {
   //Calcolo il nuovo vettore velocita'
   v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
   v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
	     
   //Rapporto la velocita'  all'intervallo di tempo 
   v*=simTime->getDT();
  }//Fine resolveCollision


 void PursueNode::setParams(float antennaRadius,float vMax,float vMin,float alpha)
   {
    setAntennaRadius(antennaRadius);  
    this->vMin=vMin;
    this->vMax=vMax;
    this->alpha=alpha;
   }//Fine setParams
