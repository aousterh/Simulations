#ifndef SCENCIRCLE_H_
#define SCENCIRCLE_H_

#include<iostream>

#include"Point2D.h"
#include"Building.h"

class ScenCircle:public Building
{
 private:
    float radius;	
	
 public:
	ScenCircle(const Point2D &pos,float radius);
	virtual ~ScenCircle();
	
    inline float getRadius()
     {return radius;}
     
    //Metodi virtuali ereditati
	void toXml(ostream &out);
	float intersect(Node *node);
	float rayIntersect(const Ray2D &ray);
	bool nodeOverlap(Node &node);
	bool pointOverlap(const Point2D &point); 
};

#endif /*SCENCIRCLE_H_*/
