#ifndef NOMADICMODELPARSER_H_
#define NOMADICMODELPARSER_H_

#include<iostream>
#include<string>

#include"ModelParser.h"

class NomadicModelParser: public ModelParser
 {
   public:
      static const int ALPHA=5;
      static const int VMIN=6;
      static const int VMAX=7;
      static const int PAUSE_TIME=8;	   
   
   protected:
      float alpha;	
      float vMin;
      float vMax;
      float pauseTime; 
   
   public:
      NomadicModelParser();
      virtual ~NomadicModelParser();
    
   
      float getVMax();
      float getVMin();
      float getAlpha();
      float getPauseTime();
   
      
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);

};

#endif /*NOMADICMODELPARSER_H_*/
