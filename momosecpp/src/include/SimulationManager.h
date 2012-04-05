#ifndef SIMULATIONMANAGER_H_
#define SIMULATIONMANAGER_H_

#include<iostream>
#include<string>
#include<vector>

#include"Simulation.h"
#include"SimTime.h"

#include"ConfigSimParser.h"

#include"XMLParser.h"

#include"Builder.h"
#include"ModelBuilder.h"
#include"DataRecorderBuilder.h"

class SimulationManager
 {
    private:
        vector<ModelBuilder*> *modelsBuilders;    
        vector<DataRecorderBuilder*> *recordersBuilders;  
	 
   public:	
    SimulationManager();
    virtual ~SimulationManager();
	    
    Simulation* createSimulation(string filename,ostream *outStream,bool verbose,bool progress);
   
   private:
    ConfigSimParser* parseConfigFile(const char* filename);  	   
    int parseXMLFile(const char* filename, XMLEvtHandler* pHandler);  
    bool ctrlSimTime(SimTime *simTime);
    void setModelBuilders();
    void setRecorderBuilders();
    vector<ModelBuilder*>* getModBuildersVector(vector<string> names);
    vector<DataRecorderBuilder*>* getRecBuildersVector(vector<string> names);
    ModelBuilder* searchModName(string name);
    DataRecorderBuilder* searchRecName(string name);
    vector<ModelBuilder*>* setupModBuilders(vector<string> names,string cfgFileName);
    vector<DataRecorderBuilder*>* setupRecBuilders(vector<string> names,string cfgFileName);
    void setupBuilder(Builder *builder,string cfgFileName);  
    string getCfgFilePath(string modelName,string mainCfgFile);
    vector<Model*>* getModels(vector<ModelBuilder*> *builders);
    vector<DataRecorder*>* getRecorders(vector<DataRecorderBuilder*> *builders);
   
 };

#endif /*SIMULATIONMANAGER_H_*/	
 
 
 