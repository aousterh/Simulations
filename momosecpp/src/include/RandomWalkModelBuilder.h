#ifndef RANDOMWALKMODELBUILDER_H_
#define RANDOMWALKMODELBUILDER_H_

#include<iostream>
#include<string>

#include"SimpleModelBuilder.h"
#include"Model.h"

class RandomWalkModelBuilder:public SimpleModelBuilder
 {
   public:	
    inline RandomWalkModelBuilder(std::string name):SimpleModelBuilder(name)
     {}
 
    inline virtual ~RandomWalkModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*RANDOMWALKMODELBUILDER_H_*/
