#ifndef THINKEROBJECT_H_
#define THINKEROBJECT_H_

#include"SimTime.h"

class ThinkerObject
 {
  public:	
   virtual void think(SimTime *simTime)=0;
 };

#endif /*THINKEROBJECT_H_*/
