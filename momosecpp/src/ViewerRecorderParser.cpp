#include "ViewerRecorderParser.h"


ViewerRecorderParser::ViewerRecorderParser():BaseParser()
  { 
    filePath="./viewerRecorderOutput.xml"; 
    compressOutput=false;	  
    actTag=-1;
  }//Fine costruttore

ViewerRecorderParser::~ViewerRecorderParser(){}
	

void ViewerRecorderParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    std::string str=xmlCharVectToString(name);
    if(str=="filePath")
	{ actTag=ViewerRecorderParser::FILE_PATH;}    
 
    if(str=="compressOutput")
	{ actTag=ViewerRecorderParser::COMPRESS_OUTPUT;}    	
	
   }//Fine OnStartElement	
  
  
void ViewerRecorderParser::OnCharacterData(const XML_Char* data, int len)
  {
    std::string str=xmlCharVectToString(data,len);	  
	  
    if(actTag==ViewerRecorderParser::FILE_PATH)
    {
      filePath=str;
      actTag=-1;
     }  
     
     if(actTag==ViewerRecorderParser::COMPRESS_OUTPUT)
     {
       compressOutput=stringToBool(str);
       actTag=-1;
     }  
  }//Fine  OnCharacterData
  
  
void ViewerRecorderParser::OnEndElement(const XML_Char* name){}  