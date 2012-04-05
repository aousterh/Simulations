#ifndef HOTSPOTMODELBUILDER_H_
#define HOTSPOTMODELBUILDER_H_

#include<iostream>
#include<string>

#include"SimpleModelBuilder.h"
#include"Model.h"

class HotSpotModelBuilder:public SimpleModelBuilder
 {
   public:	
    inline HotSpotModelBuilder(std::string name):SimpleModelBuilder(name)
     {}
 
    inline virtual ~HotSpotModelBuilder(){}
	    
    Model* create();	    
     
 };

#endif /*HOTSPOTMODELBUILDER_H_*/
