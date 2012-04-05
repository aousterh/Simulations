#ifndef VECTOR2D_H_
#define VECTOR2D_H_

#include<iostream>
#include<cmath>

#include"Point2D.h"

class Point2D;

class Vector2D
{
 //overloading dell'I/O	
 friend ostream& operator<<(ostream& output,const Vector2D& p);
 	
 public:
    float x;
    float y; 
    
    //Prototipi dei costruttori
	Vector2D(const Point2D &tail,const Point2D &head);
    
    //Costruttori inline 
	inline Vector2D()
	 { x=0; y=1; }
	 
	inline Vector2D(float x,float y)
	 { this->x=x; this->y=y; }
	 
	inline Vector2D(const Vector2D &v)
	 { this->x=v.x; this->y=v.y; }
	
	
	//Metodi inline
	inline void set(const float &x,const float &y)
	 { this->x=x; this->y=y; }
	
	inline float getLength() 
     { return (float)(sqrt(pow(this->x,2)+pow(this->y,2)));}
    
    inline float norm()
     {return (float)sqrt(x*x+y*y); }
    
     
	
	//Metodi
	void normalize();
	Vector2D unitVector();
	bool isZero();
        void rotate(float angle);
	
	//Overlaoding degli operatori 
	Vector2D& operator=(const Vector2D& v);
	Vector2D& operator+=(const Vector2D& v);
	Vector2D& operator-=(const Vector2D& v);
	Vector2D& operator*=(const float &t);
	bool operator==(const Vector2D &v);
	bool operator!=(const Vector2D &v);
	 
    inline Vector2D operator+(const Vector2D &v)
     { Vector2D vOut(this->x+v.x,this->y+v.y); return  vOut; } 
     
    inline Vector2D operator-(const Vector2D &v)
     { Vector2D vOut(this->x-v.x,this->y-v.y); return vOut; }
     
    inline Vector2D operator*(const float &t)
     { Vector2D vOut(this->x*t,this->y*t); return vOut; } 
     
    inline float operator*(const Vector2D &v)
     { return (this->x*v.x+this->y*v.y); } 
     
    
	//Distruttore
	virtual ~Vector2D();
};

#endif /*VECTOR2D_H_*/
