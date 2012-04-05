#ifndef BUILDING_H_
#define BUILDING_H_

#include<iostream>
#include<string>

//#include"PhysicalObject.h"
#include"ScenarioElement.h"
#include"Node.h"
#include"Point2D.h"
#include"Color.h"

class Building:public ScenarioElement,public PhysicalObject
 {
   private:
    float attenuation; 
    string name;  
    Color fill;
    Color border;	 
	 
  public:
	Building(const Point2D &pos);
	virtual ~Building();
	
    void setAttenuation(float attenuation);
    
    inline float getAttenuation()
     {return attenuation;}
     
    inline void setName(string name)
     {this->name=name;} 
     
    inline string getName()
     {return name;}  
     
    inline void setFillColor(const Color &color)
     {fill=color;}

   inline void setBorderColor(const Color &color)
    {border=color;}
 
   inline Color getFillColor()
    {return fill;}
  
  inline Color getBorderColor()
   {return border;} 
    
  };
  
#endif /*BUILDING_H_*/
