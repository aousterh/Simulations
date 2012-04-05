#include "BaseParser.h"

#include<iostream>

using namespace std;

BaseParser::BaseParser(){}

BaseParser::~BaseParser(){}


std::string BaseParser::xmlCharVectToString(const XML_Char* charVect)
 {
  string str;
  const XML_Char *iChar=charVect;
  while(*iChar!='\0')	
   {
    str+=(char)*iChar;
   	iChar++;
   }
  return str;
 }//Fine xmlCharVectToString
 
 
std::string BaseParser::xmlCharVectToString(const XML_Char* charVect,int len)
 {
  string str;
  const XML_Char *iChar=charVect;
  int i=0;
  while(i<len)	
   {
    str+=(char)*iChar;
   	iChar++;
   	i++;
   }
  return str;
 }//Fine xmlCharVectToString 
 
 
int BaseParser::stringToInt(std::string str)
 {
  int i;
  if(fromString<int>(i,str,std::dec))
   return i;
  else
   {
    std::cerr<<"\nERROR: Wrong conversion to int value during parsing xml file!!!"<<endl;
    return -1;
   }  
 }//Fine stringToInt
 
float BaseParser::stringToFloat(std::string str)
 {
  float f;
  if(fromString<float>(f,str,std::dec))
   return f;
  else
   {
    std::cerr<<"\nERROR: Wrong conversion to float value during parsing xml file!!!"<<endl;
    return -1; 
   }  
 }//Fine stringToFloat 
 
template<class T> bool BaseParser::fromString(T& t, const std::string& s, std::ios_base& (*f)(std::ios_base&))
 {
  std::istringstream iss(s);
  return !(iss >> f >> t).fail();
 }//Fine fromString
 
bool BaseParser::stringToBool(std::string str)
 {
  if((str=="true")||(str=="TRUE"))
    {return true;}
   else
    {
     if((str=="false")||(str=="FALSE"))
      {return false;}
     else
      {
       std::cerr<<"\nERROR: Wrong conversion to boolean value during parsing xml file!!!"<<endl;
       return false; 
      } 
    }
  }//Fine stringToBool 
  
  
std::string BaseParser::getAttrFromName(const XML_Char** attrs,std::string name)
 {
   string str;	  
   for (int i=0; attrs[i];i+=2)  
    {
      if(attrs[i]==name)
         return string(attrs[i+1]);	      
    } 	 	 
 	 
   return "";	 
 }//Fine getAttrFromName  
 
std::string BaseParser::getAttrFromID(const XML_Char** attrs,const int &id)
 { 
   int attrsCount=getAttrCount(attrs);	 
   
   if((id>=0)&&(id<attrsCount))
       return string(attrs[(id*2)+1]);	   
    else	   
      return "";	 
 }//Fine getAttrFromID 
 
std::string BaseParser::getAttrNameFromID(const XML_Char** attrs,const int &id)
 {
   int attrsCount=getAttrCount(attrs);	 
   
   if((id>=0)&&(id<attrsCount))
       return string(attrs[id*2]);	   
    else	   
      return "";		 
 }//Fine getAttrNameFromID 
 
int BaseParser::getAttrCount(const XML_Char** attrs)
 {
   int count=0;
   for(int i=0; attrs[i];i+=2)
     count++;
   return count;   
 } 
  