#include "Ray2D.h"


//Costruttori
Ray2D::Ray2D():o(),d() {}
 
//Distruttore
Ray2D::~Ray2D()
 {
  //delete(&o);
  //delete(&d);	
 }//Fine distruttore
 

//Metodi statici
float Ray2D::pointOnRay(const Ray2D &ray,const Point2D &p)
 {
  Vector2D v=ray.d;
  Point2D orig=ray.o;
  
  if(v.x==0)
   {
	 if(orig.x!=p.x)
	  return -1;
	else
	 if(v.y==0)
		return -1;
	 else
	  return ((p.y-orig.y)/v.y);
	}
  
  if(v.y==0)
   {
    if(orig.y!=p.y)
	  return -1;
	 else
	  if(v.x==0)
		return -1;
	 else
	   return ((p.x-orig.x)/v.x);	    
   }  
  
  //Sia v.x che v.y sono diversi da 0 
  float tx=(p.x-orig.x)/v.x;
  float ty=(p.y-orig.y)/v.y;
 
  if(tx==ty)
	return tx;
  else
    return -1;	 
 }//Fine pointOnRay
 
 
//Overloading degli operatori   
ostream& operator<<(ostream& output,const Ray2D &r) 
  {
   output<<r.o<<"+"<<r.d;
   return output;
  }//Fine overloading I/O 
  
  
Ray2D& Ray2D::operator=(const Ray2D&  r)
  {
   this->o=r.o;
   this->d=r.d;
   return *this;
  }//Fine overloading =    
  