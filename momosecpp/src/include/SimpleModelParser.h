#ifndef SIMPLEMODELPARSER_H_
#define SIMPLEMODELPARSER_H_

#include<iostream>
#include<string>

#include"ModelParser.h"

class SimpleModelParser: public ModelParser
 {
   public:
      static const int PAUSETIME=5;
      static const int VMIN=6;
      static const int VMAX=7;	   
   
   protected:
	float pauseTime;	
        float vMin;
        float vMax;    
   
   public:
      SimpleModelParser();
      virtual ~SimpleModelParser();
    
   
      float getVMax();
      float getVMin();
      float getPauseTime();
   
      
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);

};

#endif /*SIMPLEMODELPARSER_H_*/

