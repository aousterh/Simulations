#include "AiManager.h"

AiManager::AiManager()
 {
  thinkers=new vector<Node*>();
  thinkersModels=new vector<Model*>(); 	
 }//Fine costruttore
 
void AiManager::addThinkers(vector<Node*> *newThinkers)
 {
  vector<Node*>::iterator i;
   for(i=newThinkers->begin();i!=newThinkers->end();i++)
    {thinkers->push_back(*i);}
 }//Fine addThinkers
 
void AiManager::animateAI(SimTime *simTime)
 {
  //Faccio pensare i modelli	
  ThinkerObject *modThinker;	
  vector<Model*>::iterator i;
   for(i=thinkersModels->begin();i!=thinkersModels->end();i++)
	{
	 modThinker=*i;
	 //Faccio pensare il thinker
	 modThinker->think(simTime);
	} 	  
	  
	//Faccio pensare i nodi registrati  
	ThinkerObject *nodeThinker;	
    vector<Node*>::iterator j;
    for(j=thinkers->begin();j!=thinkers->end();j++)
	 {
	  ThinkerObject *nodeThinker=*j;
	  //Faccio pensare il thinker
	  nodeThinker->think(simTime);
	 } 	  
 }//Fine animateAI

AiManager::~AiManager(){}
