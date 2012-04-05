#include "NomadicModel.h"

#include<time.h>
#include<stdlib.h>

#include"NomadicNode.h"
#include"Point2D.h"
#include"Node.h"



//Costruttore e distruttore
NomadicModel::NomadicModel()
 { 
  setName("NomadicModel");

  setPhysicalProp(false);
  setThinkerProp(true);
  
  target.set(0,0);
  referencePoint.set(0,0);
	
  vMax=5;
  vMin=1;
  pauseTime=5;
  alpha=0.7f;
    
  pause=true;
  localTime=0;
  this->scenario=NULL;

   //Imposto il seme per generare i numeri casuali
    srand((unsigned)time(NULL));
 }//Fine costruttore

NomadicModel::~NomadicModel(){}

void NomadicModel::setModel(int numNodes,float nodeRadius,float antennaRadius,
                      float alpha,float vMin,float vMax,
                      float pauseTime,bool physical)
   {
    this->numNodes=numNodes; this->nodeRadius=nodeRadius;
    this->antennaRadius=antennaRadius; this->alpha=alpha;
    this->vMax=vMax; this->vMin=vMin;
    this->physical=physical;
    this->pauseTime=pauseTime;
   }//Fine setModel

void NomadicModel::setup(Scenario *scenario,SimTime *simtTime) 
 {
 
  //Salvo un riferimento allo scenario   	
  this->scenario=scenario;
   	 
  NomadicNode *newNode;
  for(int  i=0;i<numNodes;i++)
   {
    float p=5+i;
    newNode=new NomadicNode(Point2D(p,p),nodeRadius,&referencePoint);
    newNode->setColor(Color(0,128,255));
    newNode->setParams(antennaRadius,vMax,vMin,alpha);
    nodes.push_back(newNode);
    }
	
    
   //Calcolo punto medio
   Point2D middle=middlePoint(nodes);
   referencePoint.set(middle.x,middle.y);
 }//Fine setup 

 
 void NomadicModel::think(SimTime *simTime)
  {
   if(pause==true)
      {
       if(localTime>pauseTime)	  
       {
	pause=false;
	//Calcolo un nuovo target
	generateTarget();
        moveToTarget(simTime);//Mi muovo verso il target
       }	 
     }
    else
     {moveToTarget(simTime);} //Mi muovo verso il target  

   //Incremento il tempo locale
   localTime+=simTime->getDT();
  }//Fine think 


Point2D NomadicModel::middlePoint(vector<Node*> localNodes)
  {
   float xMin=0,xMax=0,yMin=0,yMax=0;  
	
   Node *node;
   vector<Node*>::iterator i;
   if(localNodes.size()>0)
    {
      node=localNodes.at(0);
     Point2D position=node->getPosition();
     xMin=xMax=position.x;
     yMin=yMax=position.y;
	  
     for(i=localNodes.begin();i!=localNodes.end();i++)
      {
       node=*i;
       Point2D nextPos=node->getPosition();
	    
       if(nextPos.x<xMin)
	   xMin=nextPos.x;  
       if(nextPos.x>xMax)
	   xMax=nextPos.x; 
	     
       if(nextPos.y<xMin)
	   yMin=nextPos.y;  
       if(nextPos.y>yMax)
	   yMax=nextPos.y;
      }		
     }
	
     //Calcolo coordiante medie
     float middleX=(xMin+xMax)/2;
     float middleY=(yMin+yMax)/2;
	
     //Ritorno il punto medio
     return Point2D(middleX,middleY);  
   }//Fine middlePoint

void NomadicModel::moveToTarget(SimTime *simTime) 
  {
   //Creo un vettore verso il target
   Vector2D toTarget=target-referencePoint;
   //Normalizzo il vettore verso il target
   Vector2D actV=toTarget.unitVector();
   //Calcolo la velocita'  e la direzione del nodo
   float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
   actV*=velocity;
   //Rapporto la velocita'  all'intervallo di tempo scelto
   actV*=simTime->getDT();
   
	 
   //Calcolo la distanza dal target	 
   float distance=Point2D::distance(referencePoint,target); 
	 
   if(distance<=actV.getLength())
    {referencePoint=referencePoint+toTarget;}  
   else
    {referencePoint=referencePoint+actV;} 
	
   float finalDist=Point2D::distance(referencePoint,target);
   //Se sono arrivato a destinazione mi fermo
   if(finalDist<=1)
    {
     pause=true;
     localTime=0;
    }   
  }//Fine moveToTarget

 void  NomadicModel::generateTarget()
  {
   target.set((float)(((float)rand()/(float)(RAND_MAX))*scenario->getWidth()),
	      (float)(((float)rand()/(float)(RAND_MAX))*scenario->getHeight()));  
     
  }//Fine generate Target  
