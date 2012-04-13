#include<iostream>
#include<fstream>
#include<signal.h>


#include"SimulationManager.h"
#include"Simulation.h"
#include"CmdLineArgs.h"
#include"Utils.h"

#include "tclap/CmdLine.h"

using namespace std;

void parseCmdLine(int argc, char *argv[],CmdLineArgs &cmdLineArgs);
ostream* setOutputStream(const CmdLineArgs &cmdLineArgs,fstream *outFileStream,bool &log);

//Funzione per la gestione del segnale di interrupt (Ctrl-c)
void sigHandler(int signal_handler);

//Oggetto di simulazione
Simulation *simulation;

int main(int argc, char *argv[])
 {
    //Faccio il parsing della linea di comando	 
    CmdLineArgs cmdLineArgs;
    parseCmdLine(argc,argv,cmdLineArgs);	 
   
   //Creo il file di log (se esiste)	 
   fstream *outFileStream=NULL;	 
   bool log;	 
   ostream *outStream=setOutputStream(cmdLineArgs,outFileStream,log);
	 
   //Creo la simulazione
   SimulationManager simManager;
   simulation=simManager.createSimulation(cmdLineArgs.inputFile,outStream,cmdLineArgs.verbose,
	                                                                               ((log==false)&&(cmdLineArgs.verbose==true))?false:true);	 
	 
    //Setto il gestore per l'interrupt (Ctrl-c)	  
    signal(SIGINT,sigHandler);		 
	 
    //Avvio la simulazione
    simulation->run();	 
	 
	 
   //Chiudo il file di log (se aperto)	
   if(outFileStream!=NULL)
     outFileStream->close();	   
	 
   //Esco	 
   return 0;
 }//Fine main	 
 
 
void parseCmdLine(int argc, char *argv[],CmdLineArgs &cmdLineArgs)
 {
   try { 
	   //Definisco l'oggetto che rappresenta la linea di comando
	   TCLAP::CmdLine cmd("MoMoSE (Mobility Model Simulation Environment)", ' ', "0.1");
	   
	   //Argomento: config-file di input
	   TCLAP::ValueArg<std::string> inputFileArg("f","input","Path of the input config-file.",true,"configFile.xml","string");
             cmd.add( inputFileArg );
	   
	   //Argomento: Flag per indicare la modalità verbosa 
	   TCLAP::SwitchArg verboseModeArg("v","verbose","Display status info during simulation.",cmd,false);
	  
	  //Argomento: file di log su cui salvare i messaggi della simualzione
	  TCLAP::ValueArg<std::string> logFileArg("l","log","Path of the log file.",false,"","string");
	  cmd.add( logFileArg ); 

	   //Faccio il parsing della linea di comando
	   cmd.parse( argc, argv );
	   
	   //Estraggo il nome del config-file di input
	   cmdLineArgs.inputFile=inputFileArg.getValue();
	  
	   //Estarggo il valore della modalita' verbosa
	   cmdLineArgs.verbose=verboseModeArg.getValue();
	   
	   //Estraggo il nome del config-file di input
	   cmdLineArgs.logFile=logFileArg.getValue();
	} 
   catch (TCLAP::ArgException &e)
	{ std::cerr << "error: " << e.error() << " for arg " << e.argId() << std::endl; }	   
 }//Fine  parseCmdLine
 
 
ostream* setOutputStream(const CmdLineArgs &cmdLineArgs,fstream *outFileStream,bool &log)
 {
   if(cmdLineArgs.logFile!="")	 
     {	   
       //Converto la stringa in un vettore di caratteri
      char *filePathVect=Utils::convertStrToCharVect(cmdLineArgs.logFile);
  
      //Creo il file xml su cui salvare la simulazione	 
      outFileStream=new fstream();	 
      outFileStream->open(filePathVect,ios::out); 
      if(!(*outFileStream))
       {
        cerr<<"\nERROR: Impossible to open file "<<cmdLineArgs.logFile<<"... Info redirect to sandard output!!! "<<endl;
        log=false;     
        return &cout;  
      }
      //Cancello il vettore
      free(filePathVect); 
      log=true;
      return outFileStream;
    } 
   else     
    {	 
     log=false;	   
     return &cout;    
    }	   
 }//Fine setOutputStream
 
 
 
void sigHandler(int signal_number)
  {
    /* vengono ignorati altri arrivi del segnale */ 
    signal(signal_number,SIG_IGN);
    /* gestione del segnale */
    cout<<"\nSimulation interrupted by user...\n "<<endl;	
    simulation->loop=false;	  
    /* ripristino del signal handler */ 
    signal(signal_number,sigHandler);
  }//Fine singHandler
