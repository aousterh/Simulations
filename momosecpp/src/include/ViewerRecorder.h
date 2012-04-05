#ifndef VIEWERRECORDER_H_
#define VIEWERRECORDER_H_

#include<iostream>
#include<fstream>
#include<vector>
#include<string>

#include"Node.h"
#include"SimTime.h"
#include"Scenario.h"
#include"DataRecorder.h"
#include"Color.h"


class ViewerRecorder;
	
class LastInfo
 {
  private:
       float ar;
       Color color;
  
  public:
    inline LastInfo()
     {ar=-1; color.setRi(-1);color.setGi(-1);color.setBi(-1);}
   
   inline virtual ~LastInfo(){}
  
  bool cfrAndSwap(float nowAr);
  bool cfrAndSwap(Color nowCol);
};//Fine LastInfo
	


class ViewerRecorder: public DataRecorder
{
 private:
  vector<Node*> *nodes;
  SimTime *simTime;
  string outputFilePath;
  fstream outputFile;	
  bool compressOutput;
  vector<LastInfo*>lasts;
	
 public:
	ViewerRecorder();
	virtual ~ViewerRecorder();
 
      inline  void setOutputFilePath(string outputFileName)
       { this->outputFilePath=outputFileName; }
       
      inline string getOutputPath()
        {return outputFilePath;}
	
     inline  void setCompressOutput(bool compressOutput)
       { this->compressOutput=compressOutput; }
       
      inline bool getCompressOutput()
        {return compressOutput;}	
       
	
	//Metodi virtuali ereditati
       void setup(vector<Model*>* models,Scenario *scenario,SimTime *simTime);
       void record(SimTime *simTime);
       void close(); 
    
 private:
   void setupNodes(vector<Model*>* models); 
   void addNodesToVector(vector<Node*>  *nodes,vector<Node*> *nodIter);
   void createXmlFile(SimTime *simTime); 
   void writeStaticInfo(SimTime *simTime, Scenario *scenario);
   void writeBaseNode();   
   void writeNodeInfo(); 
   void setupLastInfo(int num);
    
    
};

#endif /*VIEWERRECORDER_H_*/
