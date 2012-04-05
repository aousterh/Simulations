#include "RandomWaypointModel.h"

#include "RandomWaypointNode.h"

//Costruttore e distruttore
RandomWaypointModel::RandomWaypointModel()
 { setName("RandomWaypointModel"); }

RandomWaypointModel::~RandomWaypointModel(){}

void RandomWaypointModel::setup(Scenario *scenario,SimTime *time) 
 {
  for(int  i=0;i<numNodes;i++)
    {
     //float p=2+i*1.5f;  
	 float p=5+i;
	 RandomWaypointNode *newNode=new RandomWaypointNode(Point2D(p,p),nodeRadius,scenario);
	 newNode->setColor(Color(0,255,255));
	 newNode->setAntennaRadius(antennaRadius);
	 newNode->setSpeedBounds(vMin,vMax);
	 newNode->setPauseTime(pauseTime);
	 nodes.push_back(newNode);
    }
 }//Fine generate nodes 
