#ifndef NOMADICMODEL_H_
#define NOMADICMODEL_H_

#include"Model.h"
#include"Scenario.h"
#include"SimTime.h"
#include"Point2D.h"

class NomadicModel:public Model
 {
  private:
   int numNodes;
   float nodeRadius;	
   float vMax;
   float vMin; 	
   float pauseTime;
   float alpha;
   float antennaRadius;
  
   Scenario *scenario;
   Point2D referencePoint;
   bool pause;
   float localTime;
   Point2D target;


  public:
	NomadicModel();
	virtual ~NomadicModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
        void think(SimTime *simTime);

        void setModel(int numNodes,float nodeRadius,float antennaRadius,
                      float alpha,float vMin,float vMax,
		 float pauseTime,bool physical); 

  private:
	Point2D middlePoint(vector<Node*> localNodes);
	void generateTarget();
        void moveToTarget(SimTime *simTime);


};

#endif /*NOMADICMODEL_H_*/
