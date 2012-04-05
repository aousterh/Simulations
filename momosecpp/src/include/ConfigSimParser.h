#ifndef CONFIGSIMPARSER_H_
#define CONFIGSIMPARSER_H_

#include <iostream>
#include <string>
#include <vector>

#include "TimeScenarioParser.h"


class ConfigSimParser: public TimeScenarioParser
{
  private:
 	  vector<string> modBuildNames;
          vector<string> recBuildNames;	
          bool configFile;
       
	
   public:
	ConfigSimParser();
	virtual ~ConfigSimParser();
   
        
  	
	//Metodi virtuali riscritti
	 void OnStartElement(const XML_Char* name, const XML_Char** attrs);
	 void OnEndElement(const XML_Char* name);
         void OnCharacterData(const XML_Char* data, int len);
   
        //Metodi inline
        inline vector<string> getModBuildNames()
         {return modBuildNames;}
  
       inline vector<string> getRecBuildNames()
        {return recBuildNames;}
  
        inline bool isConfigFile()
         {return configFile;}
	 
	 private:
	       void addModBuildName(const XML_Char** attrs) ; 
               void addRecBuildName(const XML_Char** attrs); 	  

};

#endif /*CONFIGSIMPARSER_H_*/
