#ifndef ERAMODEL_H_
#define ERAMODEL_H_

#include<vector>

#include"Model.h"
#include"Scenario.h"
#include"SimTime.h"
#include"Point2D.h"
#include"Vector2D.h"
#include"Node.h"

class EraModel:public Model
 {
  private:
   int numNodes;
   float nodeRadius;
   float antennaRadius;
  
  //Variabili modello	
  Scenario *scenario;	
  float localModelTime;
  float coloringInterval;
  
  float vMax;
  float vMin; 	 
  
  
  //Variabili modello nomadic
  Point2D referencePoint;
  bool pause;
  float pauseTime;
  float localTime;
  Point2D target;
    
  //Variabili modello HotSpot
  vector<HotSpot*> *hotSpots;	
   

  public:
	EraModel(int numNodes,float nr,float ar,bool isPhy,float pauseTime,
		 float coloringInterval,float vMin,float vMax);
	virtual ~EraModel();
	
	void setup(Scenario *scenario,SimTime *simTime); 
        void think(SimTime *simTime);
 
  private:
	Point2D middlePoint(vector<Node*> localNodes);
        void roleAssignament();
	void moveToTarget(SimTime *simTime);
        void generateTarget(); 
        void moveReferencePoint(SimTime *simTime);
        void coloring(SimTime *simTime);
        bool isVisible(Node *node,Node *targetNode);
        void setType(vector<Node*> inputSet,int type);
        bool isIndMaximal(vector<Node*> inputSet);
        vector<Node*> generateIndMaximalSet(vector<Node*> inputSet,int index);
        vector<Node*> cloneVector(vector<Node*> inputNodes);
        vector<Node*> removeFrom(vector<Node*> from,vector<Node*> inputVect);
};

#endif /*ERAMODEL_H_*/
