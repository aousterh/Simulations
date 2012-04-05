#ifndef DATARECORDER_H_
#define DATARECORDER_H_

#include<string>
#include<vector>

#include"Model.h"
#include"Scenario.h"
#include"SimTime.h"

class DataRecorder
{
 private: 
  string name;	
	
public:
	inline DataRecorder(): name() {}
	inline virtual ~DataRecorder(){}
	
	inline void setName(const string &name)
     {this->name=name;}
 
    inline string getName()
     {return name;}
 
  virtual void setup(vector<Model*>* models,Scenario *scenario,SimTime *simTime)=0;
  virtual void record(SimTime *simTime)=0;
  virtual void close()=0; 
};

#endif /*DATARECORDER_H_*/
