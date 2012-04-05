#include "Point2D.h"

Point2D::~Point2D(){}
 
 //Overloading degli operatori
 ostream& operator<<(ostream& output,const Point2D& p) 
  {
   output<< "["<<p.x<<","<<p.y<< "]";
   return output;
  }//Fine overloading I/O
  
Point2D& Point2D::operator=(const Point2D&  p)
  {
   this->x=p.x;
   this->y=p.y;
   return *this;
  }//Fine overloading =  
  
Point2D& Point2D::operator+=(const Vector2D& v)
   {
   	 this->x+=v.x;
     this->y+=v.y ;
	 return *this ; 
   }//Fine overloading +=   
   
bool Point2D::operator==(const Point2D &p)
  {
   if((this->x==p.x)&&(this->y==p.y))
    return true;
   else
    return false; 
  }//Fine overloading == 
  
bool Point2D::operator!=(const Point2D &p)
  {
   if((this->x!=p.x)||(this->y!=p.y))
    return true;
   else
    return false; 
  }//Fine overloading !=    


void Point2D::rotate(float angle)  
   {
     float radAngle=angle*(M_PI / 180.0);	

     float oldX=x;   
     x=(float)(x*cos(radAngle)-y*sin(radAngle));
     y=(float)(oldX*sin(radAngle)+y*cos(radAngle));	  	   
  }//Fine rotate  
