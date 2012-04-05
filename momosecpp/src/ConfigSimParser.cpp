#include "ConfigSimParser.h"

#include <string>

ConfigSimParser::ConfigSimParser():TimeScenarioParser()
 {
   configFile=false;	 
 }//Fine costruttore

ConfigSimParser::~ConfigSimParser(){}


void ConfigSimParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo il metodo del genitore per gestire il tempo e lo scenario  
    TimeScenarioParser::OnStartElement(name,attrs);	
	
     string str=xmlCharVectToString(name);	     
	  
    //E' un file di configurazione??
    if(str=="ConfigInfo")
	 { configFile=true; }  
    
	//Nome di un model builder
    if(str=="ModBuilder")
      { addModBuildName(attrs); }  
	
     //Nome du un recorderBuilder
     if(str=="RecBuilder")
	{ addRecBuildName(attrs); }  
  }//Fine OnStartElement
  
  
void ConfigSimParser::addModBuildName(const XML_Char** attrs) 
  {
     string name=getAttrFromName(attrs,"name");  
     if(name!="")
	 modBuildNames.push_back(name);  
  }//Fine addModBuildName 

void ConfigSimParser::addRecBuildName(const XML_Char** attrs) 
  {
    string name=getAttrFromName(attrs,"name");
    if(name!="")
	 recBuildNames.push_back(name);  
  }//Fine addRecBuildName  
 
 
void ConfigSimParser::OnCharacterData(const XML_Char* data, int len){}
void ConfigSimParser::OnEndElement(const XML_Char* name){}