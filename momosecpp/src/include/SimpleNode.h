#ifndef SIMPLENODE_H_
#define SIMPLENODE_H_

#include"Node.h"
#include"Point2D.h"


class SimpleNode: public Node
{
 protected:
   float vMax;
   float vMin;
   float pauseTime;	
	
 public:
	SimpleNode(Point2D pos,const float &radius);
	virtual ~SimpleNode();
	
	//Metodi inline
	inline void setSpeedBounds(const float &vMin,const float &vMax)
     { this->vMin=vMin; this->vMax=vMax; }
  
    inline void setPauseTime(const float &pauseTime)
     { this->pauseTime=pauseTime; }
};

#endif /*SIMPLENODE_H_*/
