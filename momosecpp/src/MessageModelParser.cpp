#include<stdio.h>

#include "MessageModelParser.h"


MessageModelParser::MessageModelParser():ModelParser()
  { 
    /*    pauseTime=0;
    vMin=0;
    vMax=5;
    probability=0.5;  // arbitrary defaults
    node_trust_distance=100;*/
  }//Fine costruttore

MessageModelParser::~MessageModelParser(){}
	
	
float MessageModelParser::getVMax()
   {
     if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax	
   
float MessageModelParser::getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax  

float MessageModelParser::getPauseTime()
   {
     if(pauseTime<0)  
	 return 0;
	else
     return pauseTime;		
   }//Fine getInterval    
   

float MessageModelParser::getProbability()
   {
     if(probability < 0)  
       return 0;
     else if (probability > 1)
       return 0;
     else
       return probability;		
   } // end getProbabilty 
   
int MessageModelParser::getNodeTrustDistance()
{
     if(node_trust_distance < 0)  
	 return 0;
     else
       return node_trust_distance;		
} // end getNodeTrustDistance 
   

int MessageModelParser::getMsgTrustDistance()
{
     if(msg_trust_distance < 0)  
	 return 0;
     else
       return msg_trust_distance;		
} // end getMsgTrustDistance 

int MessageModelParser::getNodeExchangeNum()
{
     if(node_exchange_num < 1)  
	 return 1;
     else
       return node_exchange_num;		
} // end getNodeExchangeNum 


int MessageModelParser::getMsgExchangeNum()
{
     if(msg_exchange_num < 1)  
	 return 1;
     else
       return msg_exchange_num;		
} // end getMsgExchangeNum 

float MessageModelParser::getPercentAdversaries()
   {
     if(percent_adversaries < 0)  
       return 0;
     else if (percent_adversaries > 1)
       return 1;
     else
       return percent_adversaries;		
   } // end getPercentAdversaries 

float MessageModelParser::getAdversaryProbability()
   {
     if(adversary_probability < 0)  
       return 0;
     else if (adversary_probability > 1)
       return 1;
     else
       return adversary_probability;		
   } // end getAdversaryProbability

float MessageModelParser::getAdversaryMsgCreationProbability()
   {
     if(adversary_msg_creation_probability < 0)  
       return 0;
     else if (adversary_msg_creation_probability > 1)
       return 1;
     else
       return adversary_msg_creation_probability;		
   } // end getAdversaryMsgCreationProbability

float MessageModelParser::getCollaboratorMsgCreationProbability()
   {
     if(collaborator_msg_creation_probability < 0)  
       return 0;
     else if (collaborator_msg_creation_probability > 1)
       return 1;
     else
       return collaborator_msg_creation_probability;		
   } // end getCollaboratorMsgCreationProbability

	
void MessageModelParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
  {
    //Richiamo metodo del padre
    ModelParser::OnStartElement(name,attrs);	  
	  
    std::string str=xmlCharVectToString(name);	     
    	  
     //Intervallo di tempo 
    if(str=="pauseTime")
	 { actTag=MessageModelParser::PAUSETIME; }
    
    //Velocita' minima 
    if(str=="vMin")
	 { actTag=MessageModelParser::VMIN; }

    //Velocita' massima 
    if(str=="vMax")
	 { actTag=MessageModelParser::VMAX; } 		  
   
     if(str=="prob")
	 { actTag=MessageModelParser::PROBABILITY; } 		  
   
      if(str=="nodetd")
	 { actTag=MessageModelParser::NODETRUSTD; } 		  

      if(str=="msgtd")
	 { actTag=MessageModelParser::MSGTRUSTD; } 		  

      if(str=="nodeexnum")
	 { actTag=MessageModelParser::NODEEXCHANGENUM; } 		    

      if(str=="msgexnum")
	 { actTag=MessageModelParser::MSGEXCHANGENUM; } 		    
            
      if(str=="perc_adversaries")
	 { actTag=MessageModelParser::PERCENTADVERSARIES; } 		    

      if(str=="adversary_prob")
	 { actTag=MessageModelParser::ADVERSARYPROBABILITY; } 		    

      if(str=="advers_msg_prob")
	 { actTag=MessageModelParser::ADVERSARYMSGCREATIONPROBABILITY; } 		    

      if(str=="collab_msg_prob")
	 { actTag=MessageModelParser::COLLABORATORMSGCREATIONPROBABILITY; } 		    


  }//Fine OnStartElement	
  
  
void MessageModelParser::OnCharacterData(const XML_Char* data, int len)
  {
    //Richiamo metodo del padre
    ModelParser::OnCharacterData(data,len);
	  
    std::string str=xmlCharVectToString(data,len);	
  
     //Intervallo
    if(actTag==MessageModelParser::PAUSETIME) 
	{ 
          pauseTime=stringToFloat(str);
          actTag=-1;
        }

    //Velocita' minima
    if(actTag==MessageModelParser::VMIN) 
      {
	vMin=stringToFloat(str);
	actTag=-1;
      }
    
    //Velocità massima
    if(actTag==MessageModelParser::VMAX) 
      { 
	vMax=stringToFloat(str);
	actTag=-1;
      }
    
    if(actTag==MessageModelParser::PROBABILITY) 
      { 
	probability=stringToFloat(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::NODETRUSTD) 
      { 
	node_trust_distance=stringToInt(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::MSGTRUSTD) 
      { 
	msg_trust_distance=stringToInt(str);
	actTag=-1;
      }
 
    if(actTag==MessageModelParser::NODEEXCHANGENUM) 
      { 
	node_exchange_num=stringToInt(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::MSGEXCHANGENUM) 
      { 
	msg_exchange_num=stringToInt(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::PERCENTADVERSARIES) 
      { 
	percent_adversaries=stringToFloat(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::ADVERSARYPROBABILITY) 
      { 
	adversary_probability=stringToFloat(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::ADVERSARYMSGCREATIONPROBABILITY) 
      { 
	adversary_msg_creation_probability=stringToFloat(str);
	actTag=-1;
      }

    if(actTag==MessageModelParser::COLLABORATORMSGCREATIONPROBABILITY) 
      { 
	collaborator_msg_creation_probability=stringToFloat(str);
	actTag=-1;
      }


}//Fine  OnCharacterDatax
  
  
void MessageModelParser::OnEndElement(const XML_Char* name){}  
	
