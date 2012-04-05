#ifndef VIEWERRECORDERBUILDER_H_
#define VIEWERRECORDERBUILDER_H_

#include<iostream>
#include<string>

#include"DataRecorderBuilder.h"
#include"DataRecorder.h"
#include"BaseParser.h"
#include"ViewerRecorderParser.h"

class ViewerRecorderBuilder:public DataRecorderBuilder
 {
   private:
       ViewerRecorderParser *configParser;
   
   public:	
    inline ViewerRecorderBuilder(std::string name):DataRecorderBuilder(name)
     {configParser=new ViewerRecorderParser();}
 
    inline virtual ~ViewerRecorderBuilder(){}
     
   inline std::string getName()
   {return name;}  
  
  
  //Metodi ereditati
  DataRecorder* create();
   
  inline BaseParser* getConfigParser() 
   {return configParser;}
   
};

#endif /*VIEWERRECORDERBUILDER_H_*/
