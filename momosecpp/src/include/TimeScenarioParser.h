#ifndef TIMESCENARIOPARSER_H_
#define TIMESCENARIOPARSER_H_

#include <iostream>

#include "ScenarioParser.h"
#include "SimTime.h"

class TimeScenarioParser: public ScenarioParser
{
  protected:
       SimTime *simTime;	
  
  public:
	TimeScenarioParser();
	virtual ~TimeScenarioParser();
	
	//Metodi virtuali riscritti
	 void OnStartElement(const XML_Char* name, const XML_Char** attrs);
	 void OnEndElement(const XML_Char* name);
         void OnCharacterData(const XML_Char* data, int len);
  
        //Metodi inline
        inline SimTime* getSimTime()
         { return simTime; }
       
   private:
         void createSimTime(const XML_Char** attrs); 		
        
  
};

#endif /*TIMESCENARIOPARSER_H_*/
