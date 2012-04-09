#ifndef RANDOMWALKMESSAGEMODELBUILDER_H_
#define RANDOMWALKMESSAGEMODELBUILDER_H_

#include<iostream>
#include<string>

#include"MessageModelBuilder.h"
#include"RandomWalkMessageModel.h"

class RandomWalkMessageModelBuilder:public MessageModelBuilder
 {
   public:	
    inline RandomWalkMessageModelBuilder(std::string name):MessageModelBuilder(name)
     {}
 
    inline virtual ~RandomWalkMessageModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*RANDOMWALKMESSAGEMODELBUILDER_H_*/
