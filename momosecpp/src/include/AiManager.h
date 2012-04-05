#ifndef AIMANAGER_H_
#define AIMANAGER_H_

#include<vector>

#include"Node.h"
#include"Model.h"
#include"SimTime.h"

class AiManager
{
 private:
   vector<Node*> *thinkers; 	
   vector<Model*> *thinkersModels; 	
	
 public:
	AiManager();
	virtual ~AiManager();
	
	void addThinkers(vector<Node*> *newThinkers);
	void animateAI(SimTime *simTime);
	
	//Metodi inline
	inline void addThinkerModel(Model *newThinkerModel)
     { thinkersModels->push_back(newThinkerModel); }
  
    inline int getNumThinkers()
     { return thinkers->size(); }
 
    inline int getNumModThinkers()
     { return thinkersModels->size(); }
	
};

#endif /*AIMANAGER_H_*/
