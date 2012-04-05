#ifndef DATARECORDERBUILDER_H_
#define DATARECORDERBUILDER_H_

#include<iostream>
#include<string>

#include"Builder.h"
#include"DataRecorder.h"

class DataRecorderBuilder:public Builder
 {
   public:	
    inline DataRecorderBuilder(std::string name):Builder(name) {}
    inline virtual ~DataRecorderBuilder() {}
	    
  inline std::string getName()
   {return name;}  
  
  
  //Metodi astratti che vanno riscritti
  virtual DataRecorder* create()=0;
   
};

#endif /*DATARECORDERBUILDER_H_*/
