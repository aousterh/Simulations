#ifndef RANDOMWALKMESSAGEMODELBUILDER_H_
#define RANDOMWALKMESSAGEMODELBUILDER_H_

#include<iostream>
#include<string>

#include"SimpleModelBuilder.h"
#include"RandomWalkMessageModel.h"

class RandomWalkMessageModelBuilder:public SimpleModelBuilder
 {
   public:	
    inline RandomWalkMessageModelBuilder(std::string name):SimpleModelBuilder(name)
     {}
 
    inline virtual ~RandomWalkMessageModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*RANDOMWALKMESSAGEMODELBUILDER_H_*/
