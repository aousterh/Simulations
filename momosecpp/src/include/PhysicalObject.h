#ifndef PHYSICALOBJECT_H_
#define PHYSICALOBJECT_H_

#include"Ray2D.h"
#include"Point2D.h"
#include"Node.h"

class Node;

class PhysicalObject
 {
  public:
   virtual float intersect(Node &node)=0;
   virtual float rayIntersect(const Ray2D &ray)=0;
   virtual bool nodeOverlap(Node &node)=0;
   virtual bool pointOverlap(const Point2D &point)=0;
 };//Fine interfaccia PhysicalObject

#endif /*PHYSICALOBJECT_H_*/
