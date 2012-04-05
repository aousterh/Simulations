#ifndef POINT2D_H_
#define POINT2D_H_

#include<iostream>
#include<cmath>

using namespace std;

#include"Vector2D.h"

class Vector2D;

class Point2D
 {
  //overloading dell'I/O	
  friend ostream& operator<<(ostream& output,const Point2D& p); 
     
  public:
    float x;
    float y;
 
	inline Point2D()
	 {x=0; y=0;}
	 
	inline Point2D(const float x,const float y)
	 { this->x=x; this->y=y; }
	 
	inline Point2D(const Point2D &p)
	 { this->x=p.x; this->y=p.y; } 
	
        inline Point2D(const Point2D &p,const Vector2D *v)
	  { this->x=p.x+v->x; this->y=p.y+v->y; } 
	  
	
	//Distruttore
	virtual ~Point2D();
	
	  
	void rotate(float angle); 
	
	//Metodi inline
	inline void set(const float x,const float y)
	 { this->x=x; this->y=y; }
	 
	inline Point2D clone()
	 {return Point2D(x,y);}
	 
	inline float distance(const Point2D &p)
         {return (float)sqrt((p.x-this->x)*(p.x-this->x)+(p.y-this->y)*(p.y-this->y)); }
	     
	inline void translate(float x,float y)
         {this->x+=x; this->y+=y;}      
     
     
    //Overlaoding degli operatori 
	Point2D& operator=(const Point2D& p);
	Point2D& operator+=(const Vector2D& v);
	bool operator==(const Point2D &p);
	bool operator!=(const Point2D &p);
	
	inline Point2D operator+(const Vector2D &v)
      { return Point2D(this->x+v.x, this->y+v.y); }
      
    inline Vector2D operator-(const Point2D &p)
      { Vector2D vOut(this->x-p.x, this->y-p.y); return vOut; }  
      
      
   //Metodi statici
   static inline float distance(const Point2D &p1,const Point2D &p2)
     {return (float)sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y)); }
     
};


#endif /*POINT2D_H_*/
