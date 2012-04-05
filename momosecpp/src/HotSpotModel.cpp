#include "HotSpotModel.h"

#include "HotSpotNode.h"

//Costruttore e distruttore
HotSpotModel::HotSpotModel()
 { setName("HotSpotModel"); }

HotSpotModel::~HotSpotModel(){}

void HotSpotModel::setup(Scenario *scenario,SimTime *time) 
 {
   for(int  i=0;i<numNodes;i++)
    {
     //float p=2+i*1.5f;  
	 float p=5+i;
	 HotSpotNode *newNode=new HotSpotNode(Point2D(p,p),nodeRadius,scenario->getHotSpots());
	 newNode->setColor(Color(255,255,0));
	 newNode->setAntennaRadius(antennaRadius);
	 newNode->setSpeedBounds(vMin,vMax);
	 newNode->setPauseTime(pauseTime);
	 nodes.push_back(newNode);
    }
 }//Fine setup 
