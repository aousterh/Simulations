#ifndef BUILDER_H_
#define BUILDER_H_

#include<iostream>
#include<string>

#include"BaseParser.h"

class Builder
 {
   protected:
     std::string name;	 
	 
   public:	
    inline Builder(std::string name)
     { this->name=name;}
   
    inline virtual ~Builder(){}
  
  inline std::string getName()
   {return name;}  
  
  
  //Metodi astratti che vanno riscritti
  virtual BaseParser* getConfigParser()=0;
   
};

#endif /*BUILDER_H_*/
