#ifndef SCENARIOPARSER_H_
#define SCENARIOPARSER_H_

#include <iostream>
#include <string>

#include "BaseParser.h"
#include "Scenario.h"
#include "Color.h"


class ScenarioParser: public BaseParser
{
  protected:
	Scenario *scenario;
        bool scenarioFile;	
	
  public:
	ScenarioParser();
	virtual ~ScenarioParser();
	
	//Metodi virtuali riscritti
	void OnStartElement(const XML_Char* name, const XML_Char** attrs);
	void OnEndElement(const XML_Char* name);
        void OnCharacterData(const XML_Char* data, int len);
       
        //Metodi inline
        inline Scenario* getScenario()
          { return scenario; }
  
        inline bool isScenarioFile()
         { return scenarioFile; }
	 
   private:
        void createScenario(const XML_Char** attrs); 
        void createScenRectangle(const XML_Char** attrs);   
        void createScenCircle(const XML_Char** attrs); 
        void createScenText(const XML_Char** attrs);
        void createHotSpot(const XML_Char** attrs); 
        void createConnection(const XML_Char** attrs); 
        Color getColor(string str,Color basicColor);
        string extractValue(string str,int startIndex);
  
};

#endif /*SCENARIOPARSER_H_*/
