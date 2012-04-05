#include "Simulation.h"

#include<string>
#include <sys/time.h>

#include"Node.h"

Simulation::Simulation(Scenario *scenario,vector<Model*> *models,
                       vector<DataRecorder*> *recorders,
		               SimTime *simTime,ostream *outStream,bool verbose,bool progress)
		               :aiManager(),physicEngine(scenario)
 {
   //Imposto i dati della simulazione
   this->scenario=scenario;
   this->models=models;
   this->recorders=recorders;	
   this->simTime=simTime;
   this->outStream=outStream;
   this->verbose=verbose;
   this->progress=progress;	
   loop=true;	 
   lastValue=0;	 
 }//Fine costruttore

Simulation::~Simulation(){}

void Simulation::run() 
  {
   //Fase di setup	 
   setup();
   //Ciclo di simulazione
   simulationCicle(); 	 
   //Fine simulazione
   endSimulation(); 
 }//Fine run
  
void Simulation::setup()
  {
   *outStream<<"Setup started...";	 
   
   *outStream<<"\n Models setup...";
   setupModels();
   
   *outStream<<"\n AI Manager setup...";
   setupAI();
   
   *outStream<<"\n Physic Engine setup...";
   setupPhysic();
   
   *outStream<<"\n Data-Recorders setup...";
   setupRecorders();
   
   *outStream<<"\nSetup ended..."<<endl;
   
   
   //Stampo le info gnerali
   printGeneralInfo();
  }//Fine setup
  
void Simulation::setupModels() 
  {
   Model *model;	
   vector<Model*>::iterator i;
   for(i=models->begin();i!=models->end();i++)
	{
	 model=*i;
        //Stampo le info
	 *outStream<<"\n  setup "<<model->getName()<<"...";
	 //Genero i nodi del modello;
	 model->setup(scenario,simTime);
	}	
  }//Fine setupModels  


void Simulation::setupAI() 
  {
   vector<Node*> *nodes=NULL;	 
   Model *model;
   vector<Model*>::iterator i;
   for(i=models->begin();i!=models->end();i++)
	{
	 model=*i;
	 //Aggiungo i ThinkerObject al manager dell'AI;
	 nodes=model->getNodes();
	 aiManager.addThinkers(nodes);
	 //Stampo le info
	 *outStream<<"\n  "<<model->getName()<<" add "<<nodes->size()<<" nodes to AI manager";
	 
	 //Se il modello e' "Pensante" lo carico nel manager dell'IA
	 if(model->isThinker())
	 {	 
	  aiManager.addThinkerModel(model);
	  //Stampo le info
	  *outStream<<"\n  "<<model->getName()<<" is Thinker, added to AI Manager";
	 } 
	}	   	
   }//Fine setupAI
   

void Simulation::setupPhysic() 
  {
   vector<Node*> *nodes=NULL;	 
   Model *model;
   vector<Model*>::iterator i;
   for(i=models->begin();i!=models->end();i++)
	{
	 model=*i;
	 //Aggiungo i nodi da muovere al motore fisico;
	 nodes=model->getNodes();
	 physicEngine.addNodes(nodes);
         //Stampo le info
	 *outStream<<"\n  "<<model->getName()<<" add "<<nodes->size()<<" nodes to physic engine";
	 
	 //Controllo se i nodi sono anche oggeti fisici
	 //Se si, li aggiungo alla lista degli oggetti fisici
	 if(model->isPhysical()==true)
	  {	 
	   physicEngine.addPhysicalNodes(model->getNodes());
       //Stampo le info
	   *outStream<<"\n  "<<model->getName()<<" is physical, added nodes to physical objects list"; 
	  }  
    }
  
  //Aggiungo i physicaObject dello scenario
  physicEngine.addBuildings(scenario->getBuildings());
  
  //Setto il tipo di bordo
  physicEngine.setBorder(scenario);
 }//Fine setupPhysic  
 

void Simulation::setupRecorders()
  {
   DataRecorder *recorder;	
   vector<DataRecorder*>::iterator i;
   for(i=recorders->begin();i!=recorders->end();i++)
	{
	 recorder=*i;
	 //Stampo le info
	 *outStream<<"\n  setup "<<recorder->getName()<<"...";
	 //Registro i dati;
	 recorder->setup(models,scenario,simTime);
	}	  
  }//Fine setupRecorders  

void Simulation::simulationCicle()
  {
    cout<<"\nSimulation cicle started..."<<endl;
    
    //Calcolo il tempo attuale	  
    timeval tval;
    gettimeofday(&tval, NULL);
    double tStart=tval.tv_sec+(tval.tv_usec/1000000.0); 
	  
   //Ciclo di simulazione
   while((simTime->getTime()<=simTime->getT())&&(loop==true)) 
    {
	 //Stampo le info sul tempo
	 printSimCicleInfo();      
	    
	//Raccolta dati
	 record();
	    
	if(simTime->getTime()<simTime->getT())
	  {	
	    //Calcolo AiManager
	    printCicleInfo("AiManager");	  
	    aiManager.animateAI(simTime);
	  
	   //Calcolo PhysicEngine
           printCicleInfo("physic Engine");		  
	   physicEngine.animatePhysic();
	  }
	
        //Incremento del tempo  
	simTime->upTime();
	
   }//Fine ciclo di simulazione		 
 
   //Rimetto a posto il tempo
   simTime->downTime();
   
   //Fine simulazione
   cout<<"Simulation cicle ended..."<<endl;
   
   //Calcolo il tempo trascorso
   gettimeofday(&tval,NULL);
  double tEnd=tval.tv_sec+(tval.tv_usec/1000000.0);
  *outStream<<"\nSimulation cicle finished in "<<tEnd-tStart<<" seconds."<<endl;	 
 }//Fine simulationCicle


void Simulation::record()
  {
   DataRecorder *recorder;	
   vector<DataRecorder*>::iterator i;
   for(i=recorders->begin();i!=recorders->end();i++)
    {
     recorder=*i;
     //Registro i dati;
     recorder->record(simTime);
     //Stampo le info
     if(verbose==true) 	    
     *outStream<<"\n "<<recorder->getName()<<" recording...";
    }	  
  }//Fine record


void Simulation::endSimulation()
  {
   //Comunico l'inzio della fase di chisura simulazione	 
   *outStream<<"\nEnd simulation phase...";
     
   DataRecorder *recorder;	
   vector<DataRecorder*>::iterator i;
   for(i=recorders->begin();i!=recorders->end();i++)
	{
	 recorder=*i;
	 //Registro i dati;
	 recorder->close();
	 //Stampo le info
	 *outStream<<"\n "<<recorder->getName()<<" ended...";
	}	    	 
   
   //Comunico fine simulazione
   *outStream<<"\nEND SIMULATION!!!\n"<<endl;
  }//Fine endSimulation


///Metodi per la stampa delle info///
void Simulation::printSimCicleInfo()
  {
    if(verbose==true)	  
     *outStream<<"\nIteration #"<<simTime->getLoop()<<": time="<<simTime->getTime();
   if(progress==true)
     setProgressValue(simTime->getTime(),simTime->getT());
  }//Fine printSimInfo
  
  void Simulation::printCicleInfo(string name)
      {
        if(verbose)    
	  *outStream<<"\n "<<name<<" working..."<<flush;   
      }//Fine printCicleInfo   
  
 void Simulation::printGeneralInfo()
  {
   *outStream<<"\nSummarising information:";
   //Info sullo scenario
   printScenarioInfo();
   //Info sul tempo
   printSimTimeInfo();
   *outStream<<"\n Number of thinkers in AI Manager: "
		     <<aiManager.getNumThinkers();
   *outStream<<"\n Number of models thinkers in AI Manager: "
             <<aiManager.getNumModThinkers();
   *outStream<<"\n Number of nodes in Physic engine: "
		     <<physicEngine.getNumNodes();
   *outStream<<"\n Number of physical objects in Physic engine: "
		     <<physicEngine.getNumPhysObjs()<<endl;
  }//Fine printGeneralInfo
 
 void Simulation::printScenarioInfo()
  {
   *outStream<<"\n Scenario info:";	 
   *outStream<<"\n  Dimension: "<<scenario->getWidth()<<"x"<<scenario->getHeight();
	  
   string borderType=!scenario->getBorderType()?"Bounded":"Boundless";
   *outStream<<"\n  BorderType: "<<borderType;
	  
   *outStream<<"\n  Number of buildings: "<<scenario->getNumBuildings();
   *outStream<<"\n  Number of Hot-spot: "<<scenario->getNumHotSpots();
  }//Fine printScnenarioInfo
 
 void Simulation::printSimTimeInfo()
  {
   *outStream<<"\n Simulation time info:"; 	 
   *outStream<<"\n  Simulation duration(s): "<<simTime->getT();	 
   *outStream<<"\n  Step time(s): "<<simTime->getDT();
   *outStream<<"\n  Number of interations: "<<simTime->getN();
  }//Fine printTimeInfo
  
void Simulation::setProgressValue(float time,float totalTime)
  {
   float alpha=(time/totalTime)*100;
   if((int)alpha==lastValue+1)  	  
    {	     
      cout<<"*"<<flush;
      lastValue=(int)alpha; 
      if(lastValue==25)
       cout<<"[25%]"<<flush;
      if(lastValue==50)
        cout<<"[50%]"<<endl<<flush;
      if(lastValue==75)
        cout<<"[75%]"<<flush;
      if(lastValue==100)
        cout<<"[100%]"<<endl<<flush;
   }      
 }//Fine setProgreesValue  
 
 

 

