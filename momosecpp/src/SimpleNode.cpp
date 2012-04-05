#include "SimpleNode.h"

SimpleNode::SimpleNode(Point2D pos,const float &radius): Node(pos,radius) 
   {
	vMax=5;
	vMin=0;
	pauseTime=1.0f;
  }//Fine costruttore

SimpleNode::~SimpleNode(){}
