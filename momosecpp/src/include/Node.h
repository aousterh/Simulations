#ifndef NODE_H_
#define NODE_H_


#include "ThinkerObject.h"
#include "Point2D.h"
#include "Vector2D.h"
#include "Color.h"
#include "Ray2D.h"

class Node;

class PhysicalObject
 {
  public:
   virtual float intersect(Node *node)=0;
   virtual float rayIntersect(const Ray2D &ray)=0;
   virtual bool nodeOverlap(Node &node)=0;
   virtual bool pointOverlap(const Point2D &point)=0;
 };//Fine interfaccia PhysicalObject




class Node: public ThinkerObject,public PhysicalObject
{
 //overloading dell'I/O	
 friend ostream& operator<<(ostream& output,const Node& node); 	
	
 protected:
   Point2D pos;
   Vector2D v;
   float radius;
   float antennaRadius;
   bool collided;	
   Color color;
	
 public:
	Node(const Point2D &pos,const float &radius);
	virtual ~Node();
	
	void setAntennaRadius(const float &antennaRadius);
	void setPosition(const Point2D &pos);
	void setPosition(const Vector2D &v);
	
	//Metodi ereditati
	float intersect(Node *node);
	float rayIntersect(const Ray2D &ray);
	bool nodeOverlap(Node &node);
	bool pointOverlap(const Point2D &point);
	
	
 private: 	
	void roundMM();

 public:	
	//Metodi inline
	inline Point2D getPosition()
     {return pos;}
  
    inline float getRadius()
     {return radius;}
   
    inline Vector2D getVelocity()
     {return v;}
  
    inline Color getColor()
     { return color; }
     
    inline float getAntennaRadius()
     {return antennaRadius;}
  
    inline void setVelocity(const Vector2D &v)
     {this->v=v;}
     
    inline void setCollision(const bool &collided)
     {this->collided=collided;} 
     
    inline void setColor(const Color &color)
     { this->color=color; }  
     
};

#endif /*NODE_H_*/
