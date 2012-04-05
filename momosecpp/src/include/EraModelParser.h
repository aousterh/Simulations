#ifndef ERAMODELPARSER_H_
#define ERAMODELPARSER_H_

#include<iostream>
#include<string>

#include"ModelParser.h"

class EraModelParser: public ModelParser
 {
   public:
      static const int COL_INTERVAL=5;
      static const int VMIN=6;
      static const int VMAX=7;
      static const int PAUSE_TIME=8;	   
   
   protected:
      float alpha;	
      float vMin;
      float vMax;
      float pauseTime; 
      float colInterval;
   
   public:
      EraModelParser();
      virtual ~EraModelParser();
    
   
      float getVMax();
      float getVMin();
      float getAlpha();
      float getPauseTime();
      float getColInterval();
   
      
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);

};

#endif /*ERAMODELPARSER_H_*/
