#include "Building.h"

Building::Building(const Point2D &pos):ScenarioElement(pos),name(),fill(128,128,128),border(255,0,0)
 { 
   name="";	 
   attenuation=0;
 }//Fine costruttore

Building::~Building(){}

void Building::setAttenuation(float attenuation)
  {
   this->attenuation=attenuation;	  
   
   if(this->attenuation>1)
	 this->attenuation=1;
   if(this->attenuation<0)
     this->attenuation=0;
  }//Fine setAttenuation
