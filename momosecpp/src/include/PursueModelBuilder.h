#ifndef PURSUEMODELBUILDER_H_
#define PURSUEMODELBUILDER_H_

#include<iostream>
#include<string>

#include"ModelBuilder.h"
#include"Model.h"
#include"PursueModelParser.h"

class PursueModelBuilder:public ModelBuilder
 {
   private:
    PursueModelParser *configParser;

   public:	
    inline PursueModelBuilder(std::string name):ModelBuilder(name)
     {configParser=new PursueModelParser();}
 
    inline virtual ~PursueModelBuilder(){}
	    
    inline BaseParser* getConfigParser() 
     {return configParser;}

    Model* create();	    
     
 };

#endif /*PURSUEMODELBUILDER_H_*/
