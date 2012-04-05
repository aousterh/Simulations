#include "NomadicModelParser.h"


NomadicModelParser::NomadicModelParser():ModelParser()
  { 
   alpha=0.5f;
   vMin=0;
   vMax=5;
   pauseTime=1;		  
  }//Fine costruttore

NomadicModelParser::~NomadicModelParser(){}
	
	
float NomadicModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float NomadicModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float NomadicModelParser::getAlpha()
   {
     if(alpha<0)  
	 return 0;
     if(alpha>1)
       return 1;

     return alpha;		
   }//Fine getAlpha   

float NomadicModelParser::getPauseTime()
   {
     if(pauseTime<0)  
	 return 0;
    return pauseTime;		
   }//Fine getPauseTime   

   
 void NomadicModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
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
   
   if(str=="pauseTime") 
   { actTag=PAUSE_TIME; }   
  
  }//Fine OnStartElement	
  
  
void NomadicModelParser::OnCharacterData(const XML_Char* data, int len)
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
   if(actTag==PAUSE_TIME) 
     { 
      pauseTime=stringToFloat(str);
      actTag=-1;
     }
    
 }//Fine  OnCharacterData
  
  
void NomadicModelParser::OnEndElement(const XML_Char* name){}  
