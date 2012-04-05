#ifndef PHYSICENGINE_H_
#define PHYSICENGINE_H_

#include<vector>

#include"Node.h"
#include"Scenario.h"

class PhysicEngine
{
 private: 
   vector<Node*> *nodes;
   vector<PhysicalObject*> *physicalObjs;
   Scenario *scenario;	
	
 public:
	PhysicEngine(Scenario *scenario);
	virtual ~PhysicEngine();
	
	void addPhysicalNodes(vector<Node*> *physicalNodes);
	void addBuildings(vector<Building*> *buildings);
    void addNodes(vector<Node*> *newNodes);
    void setBorder(Scenario *scenario);
    void animatePhysic();
    
 private:  
    bool collisionDetection(Node *node);
    void borderCollision(Node *node);
  
 public:	
	//Metodi inline
	inline int getNumNodes()
     {return nodes->size();}
  
    inline int getNumPhysObjs()
     {return physicalObjs->size();}
};

#endif /*PHYSICENGINE_H_*/
