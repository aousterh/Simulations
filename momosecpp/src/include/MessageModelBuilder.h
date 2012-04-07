#ifndef SIMPLEMODELBUILDER_H_
#define SIMPLEMODELBUILDER_H_

#include<iostream>
#include<string>

#include"ModelBuilder.h"
#include"SimpleModelParser.h"
#include"BaseParser.h"


class SimpleModelBuilder:public ModelBuilder
 {
    protected:
       SimpleModelParser *configParser;
   	 
   public:	
    inline SimpleModelBuilder(std::string name):ModelBuilder(name) 
     {configParser=new SimpleModelParser();}
    
   inline virtual ~SimpleModelBuilder() {}
	    
   inline BaseParser* getConfigParser() 
    {return configParser;}
  
   
};

#endif /*SIMPLEMODELBUILDER_H_*/
