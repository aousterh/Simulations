#ifndef SCENRECTANGLE_H_
#define SCENRECTANGLE_H_

#include<iostream>

#include"Point2D.h"
#include"Node.h"
#include"Building.h"


class ScenRectangle:public Building
{
 private:
     float width;
     float height;	
     float rotAngle;
     
 public:
	ScenRectangle(const Point2D &pos,float width,float height);
	virtual ~ScenRectangle();
	
	//Metodi virtuali ereditati
	void toXml(ostream &out);
	float intersect(Node *node);
	float rayIntersect(const Ray2D &ray);
	bool nodeOverlap(Node &node);
	bool pointOverlap(const Point2D &point);
	
     
       //Metodi
  private:   
       Ray2D transformRay(Ray2D ray);
       float rayLocalIntersect(const Ray2D &ray);
       float rayBoxInternalIntersect(const Ray2D &r);
       Vector2D calcShift(Point2D point,Vector2D lv,float radius); 
 
 
	//Metodi inline
  public:
 
   inline float getWidth()
     {return width;}
  
    inline float getHeight()
     {return height;}
  
    inline Point2D getMinBound()
     {return p;}
  
    inline Point2D getMaxBound()
     {return Point2D(p.x+width,p.y+height);}
     
     inline void setRotAngle(float angle)
      {this->rotAngle=angle;}
     
     inline float getRotAngle()
      {return rotAngle;}
	
};

#endif /*SCENRECTANGLE_H_*/
