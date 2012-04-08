#ifndef MESSAGERECORDERBUILDER_H_
#define MESSAGERECORDERBUILDER_H_

#include<iostream>
#include<string>

#include"DataRecorderBuilder.h"
#include"DataRecorder.h"
#include"BaseParser.h"
#include"ViewerRecorderParser.h"

class MessageRecorderBuilder: public DataRecorderBuilder
 {
   private:
   ViewerRecorderParser *configParser;  //TODO: create our own
   
   public:	
    inline MessageRecorderBuilder(std::string name):DataRecorderBuilder(name)
     {configParser=new ViewerRecorderParser();}
 
    inline virtual ~MessageRecorderBuilder(){}
     
   inline std::string getName()
   {return name;}  
  
  
  //Metodi ereditati
  DataRecorder* create();
   
  inline BaseParser* getConfigParser() 
   {return configParser;}
   
};

#endif /*MESSAGERECORDERBUILDER_H_*/
