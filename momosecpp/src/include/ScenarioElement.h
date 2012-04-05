#ifndef SCENARIOELEMENT_H_
#define SCENARIOELEMENT_H_

#include<iostream>
#include<string>

#include"Point2D.h"

class ScenarioElement
 {
   protected:
      Point2D p;	   
	 
  public:
	ScenarioElement(const Point2D &pos);
	virtual ~ScenarioElement();
  
        inline Point2D getPoint()
         {return p;}
  
      //Metodi astratti
      virtual void toXml(ostream &out)=0;
  
 };
  
#endif /*SCENARIOELEMENT_H_*/
