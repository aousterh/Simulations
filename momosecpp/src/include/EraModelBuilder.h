#ifndef ERAMODELBUILDER_H_
#define ERAMODELBUILDER_H_

#include<iostream>
#include<string>

#include"ModelBuilder.h"
#include"Model.h"
#include"EraModelParser.h"

class EraModelBuilder:public ModelBuilder
 {
   private:
    EraModelParser *configParser;

   public:	
    inline EraModelBuilder(std::string name):ModelBuilder(name)
     {configParser=new EraModelParser();}
 
    inline virtual ~EraModelBuilder(){}
	    
    inline BaseParser* getConfigParser() 
     {return configParser;}

    Model* create();	    
     
 };

#endif /*ERAMODELBUILDER_H_*/
