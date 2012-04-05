#include "SimpleModel.h"

SimpleModel::SimpleModel()
 {
  numNodes=10;
  nodeRadius=0.5f;
  vMin=0;
  vMax=5;
  antennaRadius=nodeRadius*10;
  pauseTime=1;   
		  
  physical=false;  
  thinker=false;
 }//Fine costruttore

SimpleModel::~SimpleModel(){}

void SimpleModel::setModel(int numNodes,float nodeRadius,float antennaRadius,
		                  float pauseTime,float vMin,float vMax,bool physical)
 {
  this->numNodes=numNodes; this->nodeRadius=nodeRadius;
  this->antennaRadius=antennaRadius; this->pauseTime=pauseTime;
  this->vMax=vMax; this->vMin=vMin; this->physical=physical;
 }//Fine setModel
