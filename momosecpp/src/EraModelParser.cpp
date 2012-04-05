#include "EraModelParser.h"


EraModelParser::EraModelParser():ModelParser()
  { 
   vMin=0;
   vMax=5;
   pauseTime=1;		
   colInterval=0;  
  }//Fine costruttore

EraModelParser::~EraModelParser(){}
	
	
float EraModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float EraModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float EraModelParser::getPauseTime()
   {
     if(pauseTime<0)  
	 return 0;
    return pauseTime;		
   }//Fine getPauseTime   

float EraModelParser::getColInterval()
   {
    if(colInterval<0)  
     return 0;
    else
     return colInterval;		
   }//Fine getColInterval  
   
 void EraModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo metodo del padre
    ModelParser::OnStartElement(name,attrs);	  
	  
    std::string str=xmlCharVectToString(name);	     
    	  
   //Intervallo di tempo <ColInterval>value<ColInterval>
   if(str=="colInterval") 
    { actTag=COL_INTERVAL; }
   
   //Velocita' minima <vMin>value</vMin>
   if(str=="vMin") 
    { actTag=VMIN; }
	    //Velocita' massima <vMax>value</vMax>
   if(str=="vMax") 
    { actTag=VMAX; }
   
   //Intervallo di tempo <pauseTime>value<pauseTime>
   if(str=="pauseTime") 
   { actTag=PAUSE_TIME; }   
  
  }//Fine OnStartElement	
  
  
void EraModelParser::OnCharacterData(const XML_Char* data, int len)
  {
    //Richiamo metodo del padre
    ModelParser::OnCharacterData(data,len);
	  
    std::string str=xmlCharVectToString(data,len);	
  
     //Intervallo di colorazione
   if(actTag==COL_INTERVAL) 
    { 
     colInterval=stringToFloat(str);
     actTag=-1;
    }
   //Velocita' minima
   if(actTag==VMIN) 
    {
     vMin=stringToFloat(str);
     actTag=-1;
    }
  
   //Velocita'  massima
   if(actTag==VMAX) 
    { 
     vMax=stringToFloat(str);
     actTag=-1;
    }
   
   //Raggio del target
   if(actTag==PAUSE_TIME) 
     { 
      pauseTime=stringToFloat(str);
      actTag=-1;
     }
    
 }//Fine  OnCharacterData
  
  
void EraModelParser::OnEndElement(const XML_Char* name){}  
