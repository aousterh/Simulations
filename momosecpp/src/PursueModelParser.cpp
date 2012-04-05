#include "PursueModelParser.h"


PursueModelParser::PursueModelParser():ModelParser()
  { 
   alpha=0.5f;
   vMin=0;
   vMax=5;
   targetRadius=0.5f;		  
  }//Fine costruttore

PursueModelParser::~PursueModelParser(){}
	
	
float PursueModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float PursueModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float PursueModelParser::getAlpha()
   {
     if(alpha<0)  
	 return 0;
     if(alpha>1)
       return 1;

     return alpha;		
   }//Fine getAlpha   

float PursueModelParser::getTargetRadius()
   {
     if(targetRadius<0.01)  
	 return 0.01;
    return targetRadius;		
   }//Fine gettargetRadius   

   
 void PursueModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo metodo del padre
    ModelParser::OnStartElement(name,attrs);	  
	  
    std::string str=xmlCharVectToString(name);	     
    	  
    //Intervallo di tempo <pauseTime>value<pauseTime>
   if(str=="alpha") 
    { actTag=ALPHA; }
   
   //Velocita' minima <vMin>value</vMin>
   if(str=="vMin") 
    { actTag=VMIN; }
	    //Velocita' massima <vMax>value</vMax>
   if(str=="vMax") 
    { actTag=VMAX; }
   
   if(str=="targetRadius") 
   { actTag=TARGET_RADIUS; }   
  
  }//Fine OnStartElement	
  
  
void PursueModelParser::OnCharacterData(const XML_Char* data, int len)
  {
    //Richiamo metodo del padre
    ModelParser::OnCharacterData(data,len);
	  
    std::string str=xmlCharVectToString(data,len);	
  
     //Intervallo
   if(actTag==ALPHA) 
    { 
     alpha=stringToFloat(str);
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
   if(actTag==TARGET_RADIUS) 
     { 
      targetRadius=stringToFloat(str);
      actTag=-1;
     }
    
 }//Fine  OnCharacterData
  
  
void PursueModelParser::OnEndElement(const XML_Char* name){}  
