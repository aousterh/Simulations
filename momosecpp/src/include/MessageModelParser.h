#ifndef MESSAGEMODELPARSER_H_
#define MESSAGEMODELPARSER_H_

#include<iostream>
#include<string>

#include"ModelParser.h"

class MessageModelParser: public ModelParser
 {
   public:
      static const int PAUSETIME=5;
      static const int VMIN=6;
      static const int VMAX=7;	   
      static const int PROBABILITY=8;
      static const int NODETRUSTD=9;  // trust distance used for determining nodes to exchange with
      static const int MSGTRUSTD=10;  // trust distance used for determining msgs to exchange
      static const int NODEEXCHANGENUM=11;
      static const int MSGEXCHANGENUM=12;

   protected:
	float pauseTime;	
        float vMin;
        float vMax;    
	float probability;
	int node_trust_distance;
	int msg_trust_distance;
	int node_exchange_num;  // max number of nodes to exchange with per time step
	int msg_exchange_num;  // max number of messages to exchange in one time step

   public:
      MessageModelParser();
      virtual ~MessageModelParser();
    
   
      float getVMax();
      float getVMin();
      float getPauseTime();
      float getProbability();
      int getNodeTrustDistance();
      int getMsgTrustDistance();
      int getNodeExchangeNum();
      int getMsgExchangeNum();
   
      
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);

};

#endif /*MESSAGEMODELPARSER_H_*/

