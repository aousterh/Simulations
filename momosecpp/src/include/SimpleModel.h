#ifndef SIMPLEMODEL_H_
#define SIMPLEMODEL_H_

#include"Model.h"

class SimpleModel:public Model
{
 public:
	SimpleModel();
	virtual ~SimpleModel();
	
 protected:
        int numNodes;
        float nodeRadius;
        float vMin;
        float vMax;
        float antennaRadius;
        float pauseTime;
  
 public:
  void setModel(int numNodes,float nodeRadius,float antennaRadius,
		        float pauseTime,float vMin,float vMax,bool physical);
 
  //Metodi ereditati virtuali
  inline virtual void think(SimTime *simTime){} 
	
};

#endif /*SIMPLEMODEL_H_*/
