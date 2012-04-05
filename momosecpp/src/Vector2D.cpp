#include "Point2D.h"
#include "Vector2D.h"

//Costruttori
Vector2D::Vector2D(const Point2D &tail,const Point2D &head)
   {
    this->x=head.x-tail.x; 
    this->y=head.y-tail.y; 
   }//Fine costruttore

Vector2D::~Vector2D(){}   
   
//Metodi   
void Vector2D::normalize()
   {
	float len=getLength();
	if(len!=0)
	 {x=x/len;y=y/len;} 
   }//Fine normalize   
   
Vector2D Vector2D::unitVector()
  {
   float norm=this->norm();
   Vector2D vOut;
   if(norm!=0)
    {
     vOut.set((float)(x/norm),(float)(y/norm));	
     return vOut;
    }
   else
    {
     vOut.set(0.0f,0.0f);		
	 return vOut;  
    }
  }//Fine unitvector   
  
 bool Vector2D::isZero()
  {
   if((x==0.0)&&(y==0.0))
     return true;	
    else
     return false; 
  }//Fine isZero
   
   
//Overloading degli operatori   
 ostream& operator<<(ostream& output,const Vector2D &v) 
  {
   output<< "("<<v.x<<","<<v.y<< ")";
   return output;
  }//Fine overloading I/O
  
Vector2D& Vector2D::operator=(const Vector2D &v)
  {
   this->x=v.x;
   this->y=v.y;
   return *this;
  }//Fine overloading = 
  
Vector2D& Vector2D::operator+=(const Vector2D &v)
 {
  this->x+=v.x;
  this->y+=v.y;
  return *this; 
 }//Fine overloading +=   

Vector2D& Vector2D::operator-=(const Vector2D &v)
 {
  this->x-=v.x;
  this->y-=v.y;
  return *this; 
 }//Fine overloading -=  
 
Vector2D& Vector2D::operator*=(const float &t)
 {
  this->x*=t;
  this->y*=t;
  return *this; 
 }//Fine overloading *=   
 
bool Vector2D::operator==(const Vector2D &v)
  {
   if((this->x==v.x)&&(this->y==v.y))
    return true;
   else
    return false; 
  }//Fine overloading == 
  
bool Vector2D::operator!=(const Vector2D &v)
  {
   if((this->x!=v.x)||(this->y!=v.y))
    return true;
   else
    return false; 
  }//Fine overloading !=   
  
 
void Vector2D::rotate(float angle)  
   {
     float radAngle=angle*(M_PI / 180.0);	

    float oldX=x;   
    x=(float)(x*cos(radAngle)-y*sin(radAngle));
    y=(float)(oldX*sin(radAngle)+y*cos(radAngle));	  	   
  }//Fine rotate
 


