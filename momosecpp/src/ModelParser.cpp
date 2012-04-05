#include "ModelParser.h"

//Costruttore e distruttore
ModelParser::ModelParser()
  { 
    numNodes=0;
    nodeRadius=1;
    antennaRadius=10;
    isPhysical=false;
    isThinker=false;
	
    actTag=-1;	 
  }//Fine costruttore

ModelParser::~ModelParser(){}
	
	
void ModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    std::string str=xmlCharVectToString(name);	     
	  
    //Numero nodi 
    if(str=="nn") 
      { actTag=ModelParser::NUM_NODES;  }  
		
     //Raggio dei nodi 
     if(str=="nr")
	{ actTag=ModelParser::NODE_RADIUS; }
		
     //Raggio antenna
     if(str=="ar") 
	 { actTag=ModelParser::ANTENNA_RADIUS; }
		
     //Nodi fisici
     if(str=="isPhy") 
	 { actTag=ModelParser::IS_PHYSICAL; }
		
     //Modello "pensante"
     if(str=="isThink") 
	{ actTag=ModelParser::IS_THINKER; }
  }//Fine OnStartElement	
  
  
void ModelParser::OnCharacterData(const XML_Char* data, int len)
  { 
    std::string str=xmlCharVectToString(data,len);	
    
    //Numero nodi
    if(actTag==ModelParser::NUM_NODES)
      {
	 
        numNodes=stringToInt(str);
        actTag=-1;
     }  
		
     //Raggio dei nodi 
     if(actTag==ModelParser::NODE_RADIUS)
	 { 
	  nodeRadius=stringToFloat(str);
	  actTag=-1;
	 }
		
     //Raggio antenna
     if(actTag==ModelParser::ANTENNA_RADIUS) 
	 { 
	  antennaRadius=stringToFloat(str);
	  actTag=-1;
	 }
		
     //Nodi fisici
     if(actTag==ModelParser::IS_PHYSICAL) 
	 { 
	  
	   isPhysical=stringToBool(str);
	   actTag=-1;
	 }
		
     //Modello "pensante"
     if(actTag==ModelParser::IS_THINKER)
	{ 
	  isThinker=stringToBool(str);
	  
	  actTag=-1;
	 } 
  }//Fine  OnCharacterData
  
  
void ModelParser::OnEndElement(const XML_Char* name){}  