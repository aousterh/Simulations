#include "SimTime.h"

SimTime::SimTime(const float &T,const float &dt,const int &N)
 {
  this->T=T;
  this->dt=dt;
  this->N=N;
  
  this->time=0;
  this->loop=0;
 }//Fine costruttore

SimTime::~SimTime(){}


void SimTime::upTime()
  {
   setTime(time+dt);
   loop++;
  }//Fine upTime
 
void SimTime::downTime()
  {
   setTime(time-dt);
   loop--;
  }//Fine downTime
  
//Overloading degli operatori
ostream& operator<<(ostream& output,const SimTime& simTime) 
 {
  output<<"T="<<simTime.T<<" dt="<<simTime.dt<<" N="<<simTime.N;
  return output;
 }//Fine overloading I/O   
 
ostream& SimTime::dynamicTimetoXml(ostream& out)
  {
   out<<" <SimTime Time=\""<<time<<"\" dt=\""<<dt<<"\" Loops=\""<<loop<<"\"/>\n";	 
   return out;
  }//Fine dynamicTimetoXml

ostream& SimTime::staticTimetoXml(ostream& out)
  {
   out<<" <SimTime Time=\""<<T<<"\" dt=\""<<dt<<"\" Loops=\""<<N<<"\"/>\n";	 
   return out;
  }//Fine staticTimeToXml
  

  
