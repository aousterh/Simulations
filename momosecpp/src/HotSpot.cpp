#include "HotSpot.h"

HotSpot::HotSpot(const Point2D &pos,const float &radius,const int &ID):ScenarioElement(pos)
 {
  this->radius=radius;
  this->ID=ID;
 }//Fine Costruttore

HotSpot::~HotSpot(){}

void HotSpot::toXml(std::ostream &out)
 {
   out<<" <HotSpot ID=\""<<ID<<"\" x=\""<<p.x<<"\" y=\""<<p.y<<"\" radius=\""<<radius<<"\"/>\n";
 }//Fine toXml


///Metodi della classe Connection///
Connection::Connection(HotSpot *startSpot,HotSpot *endSpot)
 {
  this->startSpot=startSpot;  
  this->endSpot=endSpot;
  weight=0;	
 }//Fine costruttore
 
Connection::Connection(HotSpot *startSpot,HotSpot *endSpot,float weight)
 {
  this->startSpot=startSpot;  
  this->endSpot=endSpot;
  this->weight=weight;
 }//Fine costruttore

Connection::~Connection(){}


void HotSpot::addConnections(vector<Connection*> *newConns)
 {
  vector<Connection*>::iterator iConns;
  for(iConns=newConns->begin();iConns!=newConns->end();iConns++)
   {connections.push_back(*iConns);}
 }//Fine addConnections
 
void Connection::toXml(std::ostream &out)
 {
   out<<" <Connection startSpot=\""<<startSpot->getID()
      <<"\" endSpot=\""<<endSpot->getID()
	  <<"\" weight=\""<<weight<<"\"/>\n";
 }//Fine toXml
   



