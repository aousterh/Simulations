#ifndef SCENTEXT_H_
#define SCENTEXT_H_

#include<iostream>
#include<string>

#include"ScenarioElement.h"
#include"Color.h"

class ScenText: public ScenarioElement
 {
   protected:
      string str;
      Color pointColor;
      Color strColor;
      float rot;
      float scale;
      	 
  public:
	ScenText(const Point2D &pos,string str);
	virtual ~ScenText();
  
       void toXml(ostream &out);
  
       inline void setStr(string text)
        {this->str=text;}
  
      inline string getStr()
       {return str;}
  
     inline void setStrColor(Color color)
      {this->strColor=color;}

     inline Color getStrColor()
      {return strColor;}
 
     inline void setPointColor(Color color)
      {this->pointColor=color;}

     inline Color getPointColor()
      {return pointColor;}
  
     inline void setRotation(float angle)
      {this->rot=angle;}
 
     inline float getRotation()
      {return rot;}
      
     inline void setScale(float scale)
      {this->scale=scale;}
 
     inline float getScale()
      {return scale;} 
  
 };
  
#endif /*SCENTEXT_H_*/
