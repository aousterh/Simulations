#ifndef RANDOMWAYPOINTMODELBUILDER_H_
#define RANDOMWAYPOINTMODELBUILDER_H_

#include<iostream>
#include<string>

#include"SimpleModelBuilder.h"
#include"Model.h"

class RandomWaypointModelBuilder:public SimpleModelBuilder
 {
   public:	
    inline RandomWaypointModelBuilder(std::string name):SimpleModelBuilder(name)
     {}
 
    inline virtual ~RandomWaypointModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*RANDOMWAYPOINTMODELBUILDER_H_*/
