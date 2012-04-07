#include "ViewerRecorder.h"
#include "Color.h"
#include "Point2D.h"

#include "Utils.h"

#include <stdlib.h>

ViewerRecorder::ViewerRecorder(): outputFile()
 {
  setName("ViewerRecorder");	
  nodes=new vector<Node*>();
  simTime=NULL;
  outputFilePath="/u/aousterh/IndependentWork/Simulations/momosecpp/output/viewerRecorderOutput.xml";
  compressOutput=false;
 }//Fine costruttori

ViewerRecorder::~ViewerRecorder(){}

void ViewerRecorder::setup(vector<Model*>* models,Scenario *scenario,SimTime *simTime)
 {
  //Creo la lista dei nodi
  setupNodes(models);  
	
  //Creo il file xml su cui salvare i dati
  createXmlFile(simTime);
	
  //Scrivo il preabolo del file xml
  writeStaticInfo(simTime,scenario);
	
  //Prendo un riferimento al tempo
  this->simTime=simTime;
 }//Fine setup
 
void ViewerRecorder::setupNodes(vector<Model*>* models)
  {
   vector<Model*>::iterator i;
   for(i=models->begin();i!=models->end();i++)
   {
     //Estraggo i nodi dal modello	
     addNodesToVector(nodes,(*i)->getNodes());
     //Setto le info last 
     setupLastInfo((*i)->getNodes()->size());  
   }	
  }//Fine setupNodes 
  
void ViewerRecorder::setupLastInfo(int num)
  {
    for(int i=0;i<num;i++)
	{
	 //Setto il vettore dei last
	 lasts.push_back(new LastInfo());
	}		 
  }//Fine setupLastInfo
  
void ViewerRecorder::addNodesToVector(vector<Node*>  *nodes,vector<Node*> *nodIter) 
 {
  vector<Node*>::iterator i;
   for(i=nodIter->begin();i!=nodIter->end();i++)
    {nodes->push_back(*i);}
 }//addNodesToVector
 
void ViewerRecorder::createXmlFile(SimTime *simTime)
  {
    //Converto la stringa in un vettore di caratteri
    char *filePathVect=Utils::convertStrToCharVect(outputFilePath);
  	
   //Creo il file xml su cui salvare la simulazione
   outputFile.open(filePathVect,ios::out); 
   if(!outputFile)
    {
      cerr<<outputFilePath;
     cerr<<"\nERROR: Impossible to open file... Simulation aborted!!! ";
     exit(1);
    }
   //Cancello il vettore
   free(filePathVect); 
  }//Fine createXmlFile	 
  
void ViewerRecorder::writeStaticInfo(SimTime *simTime, Scenario *scenario) 
  {
   //Scrivo il preabolo del file xml
	outputFile<<"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"<<endl;
	outputFile<<"<TraceInfo>"<<endl;
	   
	//Scrivo le info sullo scenario
	scenario->toXml(outputFile);
	
   //Scrivo le info di base sui nodi
   writeBaseNode();	char *filePathVect=Utils::convertStrToCharVect(outputFilePath);
  }//Fine writeStaticInfo  
  
void ViewerRecorder::writeBaseNode() 
  {
   //Scrivo il tag di apertura	 
   outputFile<<" <Nodes num=\""<<nodes->size()<<"\">"<<endl;
   
   //Scrivo il tag di chiusura
   vector<Node*>::iterator i;
   for(i=nodes->begin();i!=nodes->end();i++)
    {outputFile<<"  <Node radius=\""<<(*i)->getRadius()<<"\"/>"<<endl;} 
   
   //Scrivo il tag di chiusura
   outputFile<<" </Nodes>"<<endl;
  }//Fine writeBaseNodeInfo  
 
 
 
void ViewerRecorder::record(SimTime *simTime)
 {
  //Scrivo il tag del tempo
  outputFile<<" <Time t=\""<<simTime->getTime()<<"\" loop=\""<<simTime->getLoop()<<"\">"<<endl;	 
    
  //Scrivo le info sui nodi
  writeNodeInfo();
	
  //Scrivo i tag di chiusura
  outputFile<<" </Time>"<<endl; 
 }//Fine record
 
 
/*void ViewerRecorder::writeNodeInfo() 
  {
   vector<Node*>::iterator i;
   for(i=nodes->begin();i!=nodes->end();i++)
    {
	 Point2D pos=(*i)->getPosition();
	 Color rgb=(*i)->getColor();
	 
	 //Scrivo le info nel file
	 outputFile<<"  <NT x=\""<<pos.x<<"\" y=\""<<pos.y
			   <<"\" ar=\""<<(*i)->getAntennaRadius()
			   <<"\" r=\""<<rgb.getRi()
			   <<"\" g=\""<<rgb.getGi()
			   <<"\" b=\""<<rgb.getBi()
			   <<"\"/>"<<endl;
	}		 
  }//Fine writeNodeInfo */
 
 
 void ViewerRecorder::writeNodeInfo() 
  {
    for(int i=0;i<nodes->size();i++)	     
    {    
      Node *node=nodes->at(i);	    
      Point2D pos=node->getPosition();
      Color rgb=node->getColor();
      
       //Scrivo le info nel file
       outputFile<<"  <NT x=\""<<pos.x<<"\" y=\""<<pos.y<<flush;
	    
	    
       LastInfo *lastInfo=lasts.at(i);	    
	    
	if(lastInfo->cfrAndSwap(node->getAntennaRadius())==true)
	 {
           outputFile<<"\" ar=\""<<node->getAntennaRadius()<<flush;
         }
	
			
	if(lastInfo->cfrAndSwap(rgb)==true)
	 {outputFile<<"\" r=\""<<rgb.getRi()<<"\" g=\""<<rgb.getGi()<<"\" b=\""<<rgb.getBi()<<flush;}    
	    
	 
       outputFile<<"\"/>"<<endl;
    }		 
  }//Fine writeNodeInfo */
 

void ViewerRecorder::close()
 {
  //Scrivo le info sul tempo
  simTime->dynamicTimetoXml(outputFile); 
	 
  //Chiudo il tag di root
  outputFile<<"</TraceInfo>";
	
	//Pulisco il buffer e chiudo il file xml
	outputFile.flush();
	outputFile.close();
	 
   //Comprimo l'ouput se richiesto	 
   if(compressOutput==true)	 
    {
      //rendo il path del file da comprimere	    
      char *filePathVect=Utils::convertStrToCharVect(outputFilePath); 
      //Scopro se esiste l'applicazione di compressione e comprimo
      FILE *fGzip=fopen("/bin/gzip","r");
	 
       if(fGzip!=NULL)
        {
	  fclose(fGzip);	
          execl("/bin/gzip","-f",filePathVect,0);
	} 	
     }    
	 
 }//Fine close	
 
 
 
 
 
 bool LastInfo::cfrAndSwap(float nowAr)
  {
   if(ar==-1)
    {
      ar=nowAr;
      return true;
    }  
	 
   if(ar==nowAr)
   { return false;}
  else
   {
     ar=nowAr;
     return true;
   }
  }//Fine cfrAndSwap 
 
bool LastInfo::cfrAndSwap(Color nowCol)
  {
    if((color.getRi()==-1)&&(color.getGi()==-1)&&(color.getBi()==-1))
    {
     color.setRi(nowCol.getRi());
     color.setGi(nowCol.getGi());	    
     color.setBi(nowCol.getBi());	    
     return true;
    }  	 
	 
   if((color.getRi()==nowCol.getRi())&&
       (color.getGi()==nowCol.getGi())&&
       (color.getBi()==nowCol.getBi()))
    { return false;}
   else
    {
     color.setRi(nowCol.getRi());
     color.setGi(nowCol.getGi());	    
     color.setBi(nowCol.getBi());
     return true;
    }
 }//Fine cfrAndSwap 
  
