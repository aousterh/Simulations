#ifndef MESSAGEMODELBUILDER_H_
#define MESSAGEMODELBUILDER_H_

#include<iostream>
#include<string>

#include"ModelBuilder.h"
#include"MessageModelParser.h"
#include"BaseParser.h"


class MessageModelBuilder:public ModelBuilder
 {
    protected:
       MessageModelParser *configParser;
   	 
   public:	
    inline MessageModelBuilder(std::string name):ModelBuilder(name) 
     {configParser=new MessageModelParser();}
    
   inline virtual ~MessageModelBuilder() {}
	    
   inline BaseParser* getConfigParser() 
    {return configParser;}
  
   
};

#endif /*MESSAGEMODELBUILDER_H_*/
