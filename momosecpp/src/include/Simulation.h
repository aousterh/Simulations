#ifndef SIMULATION_H_
#define SIMULATION_H_

#include<iostream>
#include<vector>
#include<string>

#include"AiManager.h"
#include"PhysicEngine.h"
#include"Model.h"
#include"DataRecorder.h"
#include"SimTime.h"
#include"Scenario.h"

class Simulation
{
 private:
    //Componenti del simulatore
   AiManager aiManager;	
   PhysicEngine physicEngine;
 
  //Dati della simulazione	
  SimTime *simTime;
  vector<Model*> *models;
  vector<DataRecorder*> *recorders;
  Scenario *scenario;
  ostream *outStream;
  bool verbose;
  bool progress;
  bool loop;
  int lastValue;
  	
  public:
	Simulation(Scenario *scenario,vector<Model*> *models,
                          vector<DataRecorder*> *recorders,
		          SimTime *simTime,ostream *outStream,bool verbose,bool progress);
 
   virtual ~Simulation();

   //Metodi
    void run();	
    void setProgressValue(float time,float totalTime);
  
    //Funzione friend per uscire dal ciclo di simulazione in caso di interrupt dall'utente (Ctrl-p)
    friend void sigHandler(int signal_handler);
     
	
 private:
  void setup();
  void setupModels();
  void setupAI();
  void setupPhysic();
  void setupRecorders();
  
  void simulationCicle();
  void record();
  void endSimulation();
  
  void printSimCicleInfo();  
  void printGeneralInfo();
  void printScenarioInfo();
  void printSimTimeInfo();
  void printCicleInfo(string name);
 };

#endif /*SIMULATION_H_*/
