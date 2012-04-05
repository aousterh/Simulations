#ifndef PURSUEMODEL_H_
#define PURSUEMODEL_H_

#include"Model.h"
#include"Scenario.h"
#include"SimTime.h"
#include"Node.h"

class PursueModel:public Model
 {
 private:
  Node *targetNode;
  int numNodes;
  float nodeRadius;
  float vMin;
  float vMax;
  float antennaRadius;
  float alpha;
  float targetRadius;   


  public:
	PursueModel();
	virtual ~PursueModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
        inline void think(SimTime *simTime){}

        void setModel(int numNodes,float nodeRadius,float antennaRadius,
		      float alpha,float vMin,float vMax,
		      float targetRadius,bool physical);



};

#endif /*PURSUEMODEL_H_*/
