#ifndef RANDOMWAYPOINTMESSAGEMODELBUILDER_H_
#define RANDOMWAYPOINTMESSAGEMODELBUILDER_H_

#include<iostream>
#include<string>
#include"MessageModelBuilder.h"
#include"RandomWaypointMessageModel.h"

class RandomWaypointMessageModelBuilder:public MessageModelBuilder
 {
   public:	
    inline RandomWaypointMessageModelBuilder(std::string name):MessageModelBuilder(name)
     {}
 
    inline virtual ~RandomWaypointMessageModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*RANDOMWAYPOINTMESSAGEMODELBUILDER_H_*/
