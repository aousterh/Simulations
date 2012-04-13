#include "RandomWalkModel.h"

#include "RandomWalkNode.h"

//Costruttore e distruttore
RandomWalkModel::RandomWalkModel()
 { setName("RandomWalkModel"); }

RandomWalkModel::~RandomWalkModel(){}

void RandomWalkModel::setup(Scenario *scenario,SimTime *simTime) 
 {
   /*for(int  i=0;i<numNodes;i++)
    {
     //float p=2+i*1.5f;  
	 float p=5+i;
	 RandomWalkNode *newNode=new RandomWalkNode(Point2D(p,p),nodeRadius);
	 newNode->setColor(Color(0,0,255));
	 newNode->setAntennaRadius(antennaRadius);
	 newNode->setSpeedBounds(vMin,vMax);
	 newNode->setPauseTime(pauseTime);
	 nodes.push_back(newNode);
    }*/

   int numGroup=numNodes/30;
	if(numGroup==0)
	  numGroup=1;
	 
	for(int  i=0;i<numGroup;i++)
	   for(int  j=0;j<30;j++)	
	    {
	      RandomWalkNode *newNode=new RandomWalkNode(Point2D(j+25,i+25),nodeRadius);
	      newNode->setColor(Color(0,0,255));
	      newNode->setAntennaRadius(antennaRadius);
	      newNode->setSpeedBounds(vMin,vMax);
	      newNode->setPauseTime(pauseTime);
	     nodes.push_back(newNode);
           }
 }//Fine generate nodes 
