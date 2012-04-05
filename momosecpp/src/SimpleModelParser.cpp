#include "SimpleModelParser.h"


SimpleModelParser::SimpleModelParser():ModelParser()
  { 
    pauseTime=0;
    vMin=0;
    vMax=5;	  
  }//Fine costruttore

SimpleModelParser::~SimpleModelParser(){}
	
	
float SimpleModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float SimpleModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float SimpleModelParser::getPauseTime()
   {
     if(pauseTime<0)  
	 return 0;
	else
     return pauseTime;		
   }//Fine getInterval    
   
	
	
void SimpleModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo metodo del padre
    ModelParser::OnStartElement(name,attrs);	  
	  
    std::string str=xmlCharVectToString(name);	     
    	  
     //Intervallo di tempo 
    if(str=="pauseTime")
	 { actTag=SimpleModelParser::PAUSETIME; }
    
    //Velocita' minima 
    if(str=="vMin")
	 { actTag=SimpleModelParser::VMIN; }

    //Velocita' massima 
    if(str=="vMax")
	 { actTag=SimpleModelParser::VMAX; } 		  
    
  }//Fine OnStartElement	
  
  
void SimpleModelParser::OnCharacterData(const XML_Char* data, int len)
  {
    //Richiamo metodo del padre
    ModelParser::OnCharacterData(data,len);
	  
    std::string str=xmlCharVectToString(data,len);	
  
     //Intervallo
    if(actTag==SimpleModelParser::PAUSETIME) 
	{ 
          pauseTime=stringToFloat(str);
          actTag=-1;
        }

    //Velocita' minima
    if(actTag==SimpleModelParser::VMIN) 
	{
         vMin=stringToFloat(str);
         actTag=-1;
       }
   
    //Velocità massima
    if(actTag==SimpleModelParser::VMAX) 
	{ 
         vMax=stringToFloat(str);
         actTag=-1;
     }
 }//Fine  OnCharacterData
  
  
void SimpleModelParser::OnEndElement(const XML_Char* name){}  
	
