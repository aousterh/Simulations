#ifndef PURSUEMODELPARSER_H_
#define PURSUEMODELPARSER_H_

#include<iostream>
#include<string>

#include"ModelParser.h"

class PursueModelParser: public ModelParser
 {
   public:
      static const int ALPHA=5;
      static const int VMIN=6;
      static const int VMAX=7;
      static const int TARGET_RADIUS=8;	   
   
   protected:
      float alpha;	
      float vMin;
      float vMax;
      float targetRadius; 
   
   public:
      PursueModelParser();
      virtual ~PursueModelParser();
    
   
      float getVMax();
      float getVMin();
      float getAlpha();
      float getTargetRadius();
   
      
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);

};

#endif /*PURSUEMODELPARSER_H_*/
