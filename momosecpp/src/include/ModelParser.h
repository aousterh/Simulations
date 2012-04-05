#ifndef MODELPARSER_H_
#define MODELPARSER_H_

#include<iostream>
#include<string>

#include"BaseParser.h"

class ModelParser: public BaseParser
 {
   public:
     static const int NUM_NODES=0;	
     static const int NODE_RADIUS=1;
     static const int ANTENNA_RADIUS=2;
     static const int IS_PHYSICAL=3;
     static const int IS_THINKER=4;
   
    protected:
     int numNodes;
     float nodeRadius;
     float antennaRadius;
     bool isPhysical;
     bool isThinker;
   
     int actTag; 
	 
    public:
      ModelParser();
      virtual ~ModelParser();
    
      
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);
     
    
      //Metodi inline
      inline int getNumNodes()
       { return numNodes; } 
  
      inline float getNodeRadius()
       { return nodeRadius; }		  
  
      inline float getAntennaRadius()
       { return antennaRadius; } 
  
      inline bool getThinkerProp()
       { return isThinker; }
  
      inline bool getPhysicalProp()
       { return isPhysical; } 
 
};

#endif /*MODELPARSER_H_*/
