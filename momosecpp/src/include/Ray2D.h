#ifndef RAY2D_H_
#define RAY2D_H_

#include"Point2D.h"
#include"Vector2D.h"

class Ray2D
{
 //overloading dell'I/O	
 friend ostream& operator<<(ostream &output,const Ray2D &r);	
	
 public:
    Point2D o;
    Vector2D d;
  
    //Costruttori  
	Ray2D();
	inline Ray2D(const Ray2D &r):o(r.o),d(r.d) {}
        inline Ray2D(const Point2D &o,const Vector2D &d):o(o),d(d) {}
	
	//Distruttore
	virtual ~Ray2D();
	
	//Metodi statici
	static float pointOnRay(const Ray2D &ray,const Point2D &p);
		
	//Overloading degli operatori
        Ray2D& operator=(const Ray2D& r);		
	
};

#endif /*RAY2D_H_*/
