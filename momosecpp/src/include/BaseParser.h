#ifndef BASEPARSER_H_
#define BASEPARSER_H_

#include <iostream>
#include <string>
#include <sstream>


#include "XMLParser.h"

class BaseParser:public XMLEvtHandler
{
 public:
	BaseParser();
	virtual ~BaseParser();
	
	std::string xmlCharVectToString(const XML_Char* charVect);
	std::string xmlCharVectToString(const XML_Char* charVect,int len);
	int stringToInt(std::string str);
	float stringToFloat(std::string str);
	bool stringToBool(std::string str);
        std::string getAttrFromName(const XML_Char** attrs,std::string name);
        std::string getAttrFromID(const XML_Char** attrs,const int &id);
        std::string getAttrNameFromID(const XML_Char** attrs,const int &id);
        int getAttrCount(const XML_Char** attrs);
     
 
        
	
 private:	
	template<class T> bool fromString(T& t,const std::string& s,
	                                  std::ios_base& (*f)(std::ios_base&));
	                                   
 public:
    inline int xmlCharVectToInt(const XML_Char* charVect,int len)
     { return stringToInt(xmlCharVectToString(charVect,len)); }
    
    inline float xmlCharVectToFloat(const XML_Char* charVect,int len)
     {return stringToFloat(xmlCharVectToString(charVect,len)); } 
     
    inline bool xmlCharVectToBool(const XML_Char* charVect,int len)
     {return stringToBool(xmlCharVectToString(charVect,len)); } 
    
	
};

#endif /*BASEPARSER_H_*/
