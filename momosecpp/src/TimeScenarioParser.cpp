#include "TimeScenarioParser.h"

#include <string>

TimeScenarioParser::TimeScenarioParser():ScenarioParser()
 {simTime=NULL;}

TimeScenarioParser::~TimeScenarioParser(){}


void TimeScenarioParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
 {
   ScenarioParser::OnStartElement(name,attrs);	 
    //Creo il tempo
    string str=xmlCharVectToString(name);	 	 
    if(str=="SimTime")
     { createSimTime(attrs);  } 
 }//Fine OnStartElement
 
 
void TimeScenarioParser::createSimTime(const XML_Char** attrs) 
   {
     //Estraggo gli elementi necessari	 
     string 	 totalStr=getAttrFromName(attrs,"Time");  
     float totalTime=stringToFloat(totalStr);
	   
     string 	 dtStr=getAttrFromName(attrs,"dt");  	   
     float dt=stringToFloat(dtStr);
	   
     string 	 loopsStr=getAttrFromName(attrs,"Loops");	   
     int loops=stringToInt(loopsStr);

    //Creo l'oggetto ScenRectangle
    simTime=new SimTime(totalTime,dt,loops);  
   }//Fine create simTime
 
 
void TimeScenarioParser::OnCharacterData(const XML_Char* data, int len){}
void TimeScenarioParser::OnEndElement(const XML_Char* name){}