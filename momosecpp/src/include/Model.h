#ifndef MODEL_H_
#define MODEL_H_

#include<iostream>
#include<vector>
#include<string>

#include"Node.h"
#include"Scenario.h"
#include"SimTime.h"
#include"ThinkerObject.h"

class Model: public ThinkerObject
{
 public:	
   //Costruttore e distruttore 	
   Model();
   virtual ~Model();
   
 private:
   string name;		
	
 protected:
   vector<Node*> nodes;	
   bool physical;
   bool thinker;  
	
 public:	
  //Metodi inline  
  inline void setName(const string &name)
   {this->name=name;}
  
  inline string getName()
   {return name;}
  
  inline vector<Node*>* getNodes()
   {return &nodes;}
  
  inline bool isPhysical()
   {return physical;}
  
  inline void setPhysicalProp(const bool &flag)
   {physical=flag;}
  
  inline bool isThinker()
   {return thinker;}
 
 inline void setThinkerProp(const bool &flag)
  {thinker=flag;}
  
  
  //Metodi virtuali
  virtual void setup(Scenario *scenario,SimTime *time)=0;  
	
};

#endif /*MODEL_H_*/
