#ifndef MODELBUILDER_H_
#define MODELBUILDER_H_

#include<iostream>
#include<string>

#include"Model.h"
#include"Builder.h"

class ModelBuilder:public Builder
 {
   public:	
    inline ModelBuilder(std::string name):Builder(name)
     {}
 
    inline virtual ~ModelBuilder(){}
    
    virtual Model* create()=0;	    
 };

#endif /*MODELBUILDER_H_*/
