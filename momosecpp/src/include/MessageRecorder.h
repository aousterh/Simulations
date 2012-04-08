#ifndef MESSAGERECORDER_H_
#define MESSAGERECORDER_H_

#include<iostream>
#include<fstream>
#include<vector>
#include<string>

#include"Node.h"
#include"SimTime.h"
#include"Scenario.h"
#include"DataRecorder.h"
#include"Color.h"

class MessageRecorder: public DataRecorder
{
 private:
  vector<Node*> *nodes;
  SimTime *simTime;
  string outputFilePath;
  fstream outputFile;	
  bool compressOutput;
	
 public:
	MessageRecorder();
	virtual ~MessageRecorder();
 
      inline  void setOutputFilePath(string outputFileName)
       { this->outputFilePath=outputFileName; }
       
      inline string getOutputPath()
        {return outputFilePath;}
	
     inline  void setCompressOutput(bool compressOutput)
       { this->compressOutput=compressOutput; }
       
      inline bool getCompressOutput()
        {return compressOutput;}	
       
      // virtual methods
      void setup(vector<Model*>* models,Scenario *scenario,SimTime *simTime);
      void record(SimTime *simTime);
      void close(); 
    
 private:
   void setupNodes(vector<Model*>* models); 
   void addNodesToVector(vector<Node*>  *nodes,vector<Node*> *nodIter);
   void createCsvFile(SimTime *simTime); 
   void writeStaticInfo(SimTime *simTime, Scenario *scenario);
   void writeBaseNode();
   void writeMessageInfo();
    
    
};

#endif /*MESSAGERECORDER_H_*/
