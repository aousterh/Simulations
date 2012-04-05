#include "PursueModel.h"

#include<vector>

#include "RandomWaypointNode.h"
#include "PursueNode.h"
#include"Point2D.h"


//Costruttore e distruttore
PursueModel::PursueModel()
 { 
  setName("PursueModel");
  numNodes=10;
  nodeRadius=0.5f;
  vMin=0;
  vMax=5;
  antennaRadius=nodeRadius*10;
  alpha=0.5f;   
  targetRadius=0.5f;	  
   
  physical=false;  
  thinker=false;
 }//Fine costruttore

PursueModel::~PursueModel(){}


void PursueModel::setModel(int numNodes,float nodeRadius,float antennaRadius,
		              float alpha,float vMin,float vMax,
		              float targetRadius,bool physical)
  {
   this->numNodes=numNodes; this->nodeRadius=nodeRadius;
   this->antennaRadius=antennaRadius; this->alpha=alpha;
   this->vMax=vMax; this->vMin=vMin;
   this->physical=physical;
   this->targetRadius=targetRadius;
  }//Fine setModel

void PursueModel::setup(Scenario *scenario,SimTime *simtTime) 
 {
  vector<Node*> *localNodes=new vector<Node*>();
	
  //Creo il nodo centrale come un waypointNode
  targetNode=new RandomWaypointNode(Point2D(15,10),targetRadius,scenario);
  targetNode->setColor(Color(0,0,0));
  targetNode->setAntennaRadius(antennaRadius);
  //Aggiungo il target
  nodes.push_back(targetNode);
	
	
   //Nodi inseguitori
    PursueNode *newNode;
   for(int i=0;i<numNodes;i++)
    {
     float p=5+i;
     newNode=new PursueNode(Point2D(p,p),nodeRadius,targetNode);
     newNode->setColor(Color(255,0,255));
     newNode->setParams(antennaRadius,vMax,vMin,alpha);
     nodes.push_back(newNode);
    }
 }//Fine generate nodes 


