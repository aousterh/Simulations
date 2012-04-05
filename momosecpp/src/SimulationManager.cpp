#include"SimulationManager.h"


#include"Utils.h"


#include"ViewerRecorderBuilder.h"
#include"ViewerRecorder.h"
#include"BaseParser.h"


#include"RandomWalkModelBuilder.h"
#include"RandomWaypointModelBuilder.h"
#include"PursueModelBuilder.h"
#include"NomadicModelBuilder.h"
#include"EraModelBuilder.h"
#include"HotSpotModelBuilder.h"

#define BUFSIZE 1024

#ifdef WINDOWS
  #define _SEPARATOR_ '\'
#else
  #define _SEPARATOR_ '/'
#endif  

SimulationManager::SimulationManager() 
 {
   modelsBuilders=new vector<ModelBuilder*>();    
   recordersBuilders=new vector<DataRecorderBuilder*>();
    
   //Carico il vettore dei modelli	 
   setModelBuilders();
   //Carico il vettore dei data-recorder
   setRecorderBuilders();	 
 }//Fine costruttore
	
SimulationManager::~SimulationManager() 
 {
   //delete(&modelsBuilders);	 
   //delete(&recordersBuilders);
 }//Fine distruttore	
	
	
Simulation* SimulationManager::createSimulation(string filename,ostream *outStream,bool verbose,bool progress)  
  {
     char *filenameChar=Utils::convertStrToCharVect(filename);	  
	  
     //Leggo il file di configurazione	  
     ConfigSimParser	 *configSimParser=parseConfigFile(filenameChar);	 
	  
     if(configSimParser==NULL)
     {
       cerr<<"ERROR: Main config-file parsing failed... Loading aborted!"<<endl;
       exit(1);		     
     }	     
     
     //Prelevo il tempo	   
     SimTime *simTime=configSimParser->getSimTime();
     if(simTime==NULL)
     {
       cerr<<"ERROR: Simulation time data parsing failed... Loading aborted!"<<endl;
       exit(1);	
     }	     
     //Controllo il formato del tempo
     ctrlSimTime(simTime);
     
     //Prelevo lo scenario
     Scenario *scenario=configSimParser->getScenario();  
      if(scenario==NULL)
      {
        cerr<<"ERROR: Scenario data parsing failed... Loading aborted!"<<endl;
        exit(1);	
      }	     
     
      //Creo i modelli
      vector<ModelBuilder*> *modBuilders=setupModBuilders(configSimParser->getModBuildNames(),filename);
      vector<Model*> *models=getModels(modBuilders);
      
      //Creo i modelli       
      vector<DataRecorderBuilder*> *recBuilders=setupRecBuilders(configSimParser->getRecBuildNames(),filename);
      vector<DataRecorder*> *recorders=getRecorders(recBuilders);
      
      //Creo e ritorno la simulazione
      Simulation *simulation=new Simulation(scenario,models,recorders,simTime,outStream,verbose,progress);      
      return simulation;	  
  }//Fine createSimulation
  
ConfigSimParser* SimulationManager::parseConfigFile(const char* filename)
   {
     //Faccio il parsing del file	   
     ConfigSimParser	  *configSimParser=new ConfigSimParser();
     int res=parseXMLFile(filename,configSimParser);	   
	   
     if(res==-1)   
       {
	 cerr<<"ERROR: Main config-file parsing failed... Loading aborted!"<<endl;
         exit(1);	
        }     
      else
       {
	 if(!configSimParser->isConfigFile())
	  {
	    cerr<<"ERROR: The input file is not a config-file... Loading aborted!"<<endl;
	    exit(1);		  
	  }		 
	}      
	   
     return configSimParser;  
   }//Fine parseConfigFile  
  
int SimulationManager::parseXMLFile(const char* filename, XMLEvtHandler* pHandler)  
  {
    // Creo il buffer su cui inserire i dati letti dal file XML
    char buf[BUFSIZE];
		
    //Inizializzo l'istanza del parser
    XMLParser parser(pHandler);
    XML_Char sep;
    parser.Create("UTF-8",&sep);
    parser.EnableElementHandler();
    parser.EnableCharacterDataHandler();

    //Apro il file xml 
    FILE*  fh=fopen(filename,"r");
    if(fh==NULL)
	{return -1;} 	 
         	    
    //Faccio il parsing del file aperto    
    bool atEnd=false;
    size_t len;
    int done;
    do 
	{
          len=fread(buf, 1, sizeof(buf), fh);
          done=len<sizeof(buf);
          parser.Parse(buf,len,done);
	} 
    while (!done);
	
   //Chiudo il file xml	
   fclose(fh);   
    
   return 0;   
 }//Fine parseXMLFile 
 
bool SimulationManager::ctrlSimTime(SimTime *simTime)
  {
    if((simTime->getN()<=0)||(simTime->getDT()<=0)||(simTime->getT()<=0))
    {
      cerr<<"ERROR: Bad format of simulation time... Loading aborted!"<<endl;
      exit(1);                             
    }	
  }//Fine ctrlSimTime 
  

vector<ModelBuilder*>* SimulationManager::setupModBuilders(vector<string> names,string cfgFileName)
  {
    //Carico i builders	  
    vector<ModelBuilder*> *builders=getModBuildersVector(names);
	  
     vector<ModelBuilder*>::iterator i;
     for(i=builders->begin();i!=builders->end();i++)
       { setupBuilder(*i,cfgFileName); }  
       
    return builders;		  
  }//Fine   setupModBuilders
  
  
vector<DataRecorderBuilder*>* SimulationManager::setupRecBuilders(vector<string> names,string cfgFileName)
  {
    //Carico i builders	  
    vector<DataRecorderBuilder*> *builders=getRecBuildersVector(names);
	  
    vector<DataRecorderBuilder*>::iterator i;
     for(i=builders->begin();i!=builders->end();i++)
       { setupBuilder(*i,cfgFileName);  }  
       
    return builders;		  
  }//Fine   setupRecBuilders  
  
void SimulationManager::setupBuilder(Builder *builder,string cfgFileName)  
   {
     BaseParser *parser=builder->getConfigParser();
     if(parser!=NULL)
	 {
	    string configStr=getCfgFilePath(builder->getName(),cfgFileName);	 
            int res=parseXMLFile(Utils::convertStrToCharVect(configStr),parser);
            if(res==-1)
	     {
	       cerr<<"ERROR: Parsing " <<configStr<<" failed... Loading aborted!"<<endl;
               exit(1);                        
	     }		    
         }			   
   }//Fine setupBuilder
  
  
string SimulationManager::getCfgFilePath(string modelName,string mainCfgFile)
    {
      //Cerco l'inizio del nome del file di configurazione	    
      int index=mainCfgFile.size();	     
      char curCar;
      do
       {
	 index--;        
	 if(index>=0)      
	  curCar=mainCfgFile[index];
	}
     while((curCar!=_SEPARATOR_)&&(index>=0));	
        
	//Inserisco il nome del modello
	mainCfgFile.insert(index+1,modelName+"-");
	    
     //Ritorno il nuovo nome del file	
     return mainCfgFile; 	
   }//Fine getCfgFilePath
  
  
  
vector<ModelBuilder*>* SimulationManager::getModBuildersVector(vector<string> names)
  {
     ModelBuilder* modBuilder=NULL;	  
     vector<ModelBuilder*> *builders=new vector<ModelBuilder*>();
	  
     vector<string>::iterator i;
     for(i=names.begin();i!=names.end();i++)
       {
	 modBuilder=searchModName(*i);
	 if(modBuilder!=NULL) 
	   builders->push_back(modBuilder);
         else 
	  { cerr<<"WARNING: "<<*i<<" not found in current system!"<<endl; }	 
	}
    return builders;	  
  }//Fine  getBuildersVector 
  
  
vector<DataRecorderBuilder*>* SimulationManager::getRecBuildersVector(vector<string> names)
  {
     DataRecorderBuilder* recBuilder=NULL;	  
     vector<DataRecorderBuilder*> *builders=new vector<DataRecorderBuilder*>();
	  
     vector<string>::iterator i;
     for(i=names.begin();i!=names.end();i++)
       {
	recBuilder=searchRecName(*i);
	 if(recBuilder!=NULL) 
	   builders->push_back(recBuilder);
         else 
	  { cerr<<"WARNING: "<<*i<<" not found in current system!"<<endl; }	 
	}
    return builders;	  
  }//Fine  getBuildersVector   
  
 ModelBuilder* SimulationManager::searchModName(string name)
   {
     vector<ModelBuilder*>::iterator j;
     for(j=modelsBuilders->begin();j!=modelsBuilders->end();j++)
       {
	 if(name==(*j)->getName())
 	   return *j; 	 
       }	 
     return NULL; 
   }//Fine searchName  
   
 DataRecorderBuilder* SimulationManager::searchRecName(string name)
   {
     vector<DataRecorderBuilder*>::iterator j;
     for(j=recordersBuilders->begin();j!=recordersBuilders->end();j++)
       {
	 if(name==(*j)->getName())
 	   return *j; 	 
       }	 
     return NULL; 
   }//Fine searchName   
   
vector<Model*>* SimulationManager::getModels(vector<ModelBuilder*> *builders)
   {
     vector<Model*> *models=new vector<Model*>();
     vector<ModelBuilder*>::iterator i;
     for(i=builders->begin();i!=builders->end();i++)
       { models->push_back((*i)->create());}  	   
       
      return models; 
   }//Fine getModels  

vector<DataRecorder*>* SimulationManager::getRecorders(vector<DataRecorderBuilder*> *builders)
   {
     vector<DataRecorder*> *recorders=new vector<DataRecorder*>();
     vector<DataRecorderBuilder*>::iterator i;
     for(i=builders->begin();i!=builders->end();i++)
       { recorders->push_back((*i)->create());}  	   
       
      return recorders; 
   }//Fine getModels     
   
 
  
////////////////////////////////////////////////////////////////////  
void SimulationManager::setModelBuilders()
   {
     modelsBuilders->push_back(new RandomWalkModelBuilder("Random walk Model"));
     modelsBuilders->push_back(new RandomWaypointModelBuilder("Random waypoint Model")); 
     modelsBuilders->push_back(new EraModelBuilder("ERA Model")); 
     modelsBuilders->push_back(new HotSpotModelBuilder("Hot-Spot Model")); 
     modelsBuilders->push_back(new NomadicModelBuilder("Nomadic Model")); 
     modelsBuilders->push_back(new PursueModelBuilder("Pursue Model")); 
     //Insert here new ModelBuilder
     //modelsBuilder->push_back(new NewModelBuilder());
  }//end getModelBuilders
  
  void SimulationManager::setRecorderBuilders()
   {
     recordersBuilders->push_back(new ViewerRecorderBuilder("Viewer recorder"));  
     //recordersBuilders->push_back(new DebugRecorderBuilder("Debug recorder")); 
     //Insert here new DataRecorderBuilder
	//recoredersBuilder->push_back(new NewDataRecorderBuilder());  
   }//End getRecorderBuilder  
	 