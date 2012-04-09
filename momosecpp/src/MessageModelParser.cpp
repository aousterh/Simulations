#include<stdio.h>

#include "MessageModelParser.h"


MessageModelParser::MessageModelParser():ModelParser()
  { 
    pauseTime=0;
    vMin=0;
    vMax=5;
    probability=0.5;  // arbitrary defaults
    max_trust_distance=100;
  }//Fine costruttore

MessageModelParser::~MessageModelParser(){}
	
	
float MessageModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float MessageModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float MessageModelParser::getPauseTime()
   {
     if(pauseTime<0)  
	 return 0;
	else
     return pauseTime;		
   }//Fine getInterval    
   

float MessageModelParser::getProbability()
   {
     if(probability < 0)  
       return 0;
     else if (probability > 1)
       return 0;
     else
       return probability;		
   } // end getProbabilty 
   
int MessageModelParser::getMaxTrustDistance()
{
     if(max_trust_distance < 0)  
	 return 0;
     else
       return max_trust_distance;		
   } // end getMaxTrustDistance 
   
	
	
void MessageModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo metodo del padre
    ModelParser::OnStartElement(name,attrs);	  
	  
    std::string str=xmlCharVectToString(name);	     
    	  
     //Intervallo di tempo 
    if(str=="pauseTime")
	 { actTag=MessageModelParser::PAUSETIME; }
    
    //Velocita' minima 
    if(str=="vMin")
	 { actTag=MessageModelParser::VMIN; }

    //Velocita' massima 
    if(str=="vMax")
	 { actTag=MessageModelParser::VMAX; } 		  
   
     if(str=="prob")
	 { actTag=MessageModelParser::PROBABILITY; } 		  
   
      if(str=="maxtd")
	 { actTag=MessageModelParser::MAXTRUSTD; } 		  
 
  }//Fine OnStartElement	
  
  
void MessageModelParser::OnCharacterData(const XML_Char* data, int len)
  {
    //Richiamo metodo del padre
    ModelParser::OnCharacterData(data,len);
	  
    std::string str=xmlCharVectToString(data,len);	
  
     //Intervallo
    if(actTag==MessageModelParser::PAUSETIME) 
	{ 
          pauseTime=stringToFloat(str);
          actTag=-1;
        }

    //Velocita' minima
    if(actTag==MessageModelParser::VMIN) 
      {
	vMin=stringToFloat(str);
	actTag=-1;
      }
    
    //Velocità massima
    if(actTag==MessageModelParser::VMAX) 
      { 
	vMax=stringToFloat(str);
	actTag=-1;
      }
    
    if(actTag==MessageModelParser::PROBABILITY) 
      { 
	probability=stringToFloat(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::MAXTRUSTD) 
      { 
	max_trust_distance=stringToInt(str);
	actTag=-1;
      }
 

}//Fine  OnCharacterData
  
  
void MessageModelParser::OnEndElement(const XML_Char* name){}  
	
