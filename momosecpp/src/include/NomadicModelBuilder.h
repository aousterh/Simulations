#ifndef NOMADICMODELBUILDER_H_
#define NOMADICMODELBUILDER_H_

#include<iostream>
#include<string>

#include"ModelBuilder.h"
#include"Model.h"
#include"NomadicModelParser.h"

class NomadicModelBuilder:public ModelBuilder
 {
   private:
    NomadicModelParser *configParser;

   public:	
    inline NomadicModelBuilder(std::string name):ModelBuilder(name)
     {configParser=new NomadicModelParser();}
 
    inline virtual ~NomadicModelBuilder(){}
	    
    inline BaseParser* getConfigParser() 
     {return configParser;}

    Model* create();	    
     
 };

#endif /*NOMADICMODELBUILDER_H_*/
