#include "ScenRectangle.h"

ScenRectangle::ScenRectangle(const Point2D &pos,float width,float height):Building(pos)
 {
   this->width=width;
   this->height=height;
   rotAngle=0;	 
 }//Fine costruttore

ScenRectangle::~ScenRectangle(){}

void ScenRectangle::toXml(ostream &out)
 {
   //Dati obbligatori	 
  out<<" <Rect x=\""<<p.x<<"\" y=\""<<p.y<<"\" width=\""<<width<<"\" height=\""<<height<<"\"";
  
  //Nome (dato Opzionale)
  if(getName()!="")	 
     out<<" name=\""<<getName()<<"\"";
  
  //Angolo di rotazione (dato Opzionale)
  if(rotAngle!=0)
    out<<" rotation=\""<<rotAngle<<"\"";	  
  
  
  //Colore riempimento (Dato opzionale)
   Color fillCol=getFillColor(); 
   if((fillCol.getRi()!=128)||
     (fillCol.getGi()!=128)||
     (fillCol.getBi()!=128))
     { 
	  out<<" fill=\""<<fillCol.getRi()<<","
	                       <<fillCol.getGi()<<","
	                       <<fillCol.getBi()<<"\""; 
     }
   
   //Colore bordo (Dato opzionale)
   Color borderCol=getBorderColor(); 
   if((borderCol.getRi()!=255)||
     (borderCol.getGi()!=0)||
     (borderCol.getBi()!=0))
     { 
	  out<<" border=\""<<borderCol.getRi()<<","
	                              <<borderCol.getGi()<<","
	                              <<borderCol.getBi()<<"\""; 
     }  
	
   out<<" attenuation=\""<<getAttenuation()<<"\"/>\n";	
 }//Fine toXml
 
float ScenRectangle::intersect(Node *node)
 {
   ////////////////
   if(nodeOverlap(*node))
    {
      Point2D pt(node->getPosition());
      //Trasformo il punto nelle coordinate locali del rettangolo
      pt.translate(-this->p.x,-this->p.y);
      pt.rotate(-rotAngle);
	
      Vector2D lv;
      lv=node->getVelocity();
      lv.rotate(-rotAngle);
		 
     Vector2D shift=calcShift(pt,lv,node->getRadius()+0.01f);
     node->setPosition(shift);
     //return 0;
   }	
  ////////////////////////////   	 
	 
	 
   float radius=node->getRadius();	
   ScenRectangle boundRect(Point2D(p.x-radius,p.y-radius),
		                              width+radius*2,height+radius*2);
  
  //Creo il raggio e lo trasformo nelle coodinate locali del rettangolo
  Ray2D ray=transformRay(Ray2D(node->getPosition(),node->getVelocity()));
  
  //Lo devo traslare perchè calcolo 
  //l'intersezione con un rettangolo allargato
  ray.o.x+=radius; ray.o.y+=radius;
  
  //Calcolo l'intersezione
  return boundRect.rayLocalIntersect(ray);
 }//Fine intersect
 
 
Ray2D ScenRectangle::transformRay(Ray2D ray)
 {
  Ray2D transfRay=ray;	
  
  //Traslo e ruoto il punto 
  transfRay.o.translate(-p.x,-p.y);
  transfRay.o.rotate(-rotAngle);
  
  //Ruoto il vettore velocità
  transfRay.d.rotate(-rotAngle);
  
  //Ritorno il raggio trasformato
  return transfRay;
 }//Fine transformRay 
 
 
float ScenRectangle::rayIntersect(const Ray2D &ray)
 {
   //Trasformo il raggio
  Ray2D rayTransf=transformRay(ray);	
  //Calcolo e ritorno l'intersezione tra il raggio 
  //trasformato e il rettangolo
  return rayLocalIntersect(rayTransf);
 }//Fine rayIntersect 

 
float ScenRectangle::rayLocalIntersect(const Ray2D &ray)
 {
  Point2D origin=ray.o;
  Vector2D direction=ray.d;
  
  /*Point2D bound0=getMinBound();
    bound0.set(0,0);	 
  Point2D bound1=getMaxBound();
  bound1.set(width,height);*/		 
  
  if(direction.x==0)
   {
     if(direction.y!=0)
	{	
	 if((0<=origin.x)&&(origin.x<=width))
	  {
	   if(direction.y>=0)	 	
	     return((0-origin.y)/direction.y);
	  else
	   return ((height-origin.y)/direction.y);
	  } 
	  else
	   return -1; 
	} 
	else
	  return -1;
   } 
   else
    {
	 if(direction.y==0)
	  {
	   if((0<=origin.y)&&(origin.y<=height))
		{
		 if(direction.x>=0)	 	
		  return((0-origin.x)/direction.x);
		 else
		  return ((width-origin.x)/direction.x);	 
		} 
		else
		 return -1;
	  } 
	 else
		return rayBoxInternalIntersect(ray);	 
    }  
 }//Fine rayIntersect
 
float ScenRectangle::rayBoxInternalIntersect(const Ray2D &r) 
 {
  float tmin,tmax,tymin,tymax;
  Point2D origin=r.o;
  Vector2D direction=r.d;  
	
  if(direction.x>=0)
   {
    tmin=(0-origin.x)/direction.x;
    tmax=(width-origin.x)/direction.x;	
   }
  else
   {
    tmin=(width-origin.x)/direction.x;
    tmax=(0-origin.x)/direction.x;	 	
   }
	 
  if(direction.y>=0)
	{
	 tymin=(0-origin.y)/direction.y;
	 tymax=(height-origin.y)/direction.y;	
    }
   else
    {
     tymin=(height-origin.y)/direction.y;
     tymax=(0-origin.y)/direction.y;	 	
    }
  
  if((tmin>tymax)||(tymin>tmax))
	return -1;  

  if(tymin>tmin)
	 tmin=tymin;
  if(tymax<tmax)
	 tmax=tymax;
	
  return tmin;  
 }//Fine rayBoxInternalIntersect
 
 
bool ScenRectangle::nodeOverlap(Node &node)
 {
   Point2D pCtrl;
   pCtrl=node.getPosition();
   //Trasformo il punto nelle coordinate locali del rettangolo
   pCtrl.translate(-p.x,-p.y);
   pCtrl.rotate(-rotAngle);
   
   /*Point2D pTest;
   pTest=node.getPosition(); 
   //Trasformo il punto nelle coordinate locali del rettangolo
   pTest.translate(-p.x,-p.y);
   pTest.rotate(-rotAngle);*/
	 
  //////////////////////
   if((pCtrl.x>=-node.getRadius())&&(pCtrl.x<=width+node.getRadius()))
    {
	 if((pCtrl.y>=-node.getRadius())&&(pCtrl.y<=height+node.getRadius()))
	 {return true;} 
    }  
   return false;
   ////////////////////////	 
	 
   
   /*float radius2=node.getRadius()*node.getRadius();
      
   if(pTest.x<0)
	pTest.x=0;
   if(pTest.x>width)
	pTest.x=width;
   
   if(pTest.y<0)
	pTest.y=0;
   if(pTest.y>height)
	pTest.y=height;	 
   
   return (((pCtrl.x-pTest.x)*(pCtrl.x-pTest.x)+
		  (pCtrl.y-pTest.y)*(pCtrl.y-pTest.y))<=radius2);*/
 }//Fine nodeOverlap
 
 
 
bool ScenRectangle::pointOverlap(const Point2D &point)
 {
   //Trasformo il punto nelle coordinate locali del rettangolo
   Point2D pt;
   pt=point;
   pt.translate(-p.x,-p.y);
   pt.rotate(-rotAngle);
	
   //Controllo se il punto si trova dentro il rettangolo axis-alined
   if((0<=pt.x)&&(pt.x<=width))
    {
     if((0<=pt.y)&&(pt.y<=height))
	return true;
     else
	 return false;  
    }
   else
    return false;
 }//Fine pointOverlap
 
 
  ///////////////
Vector2D ScenRectangle::calcShift(Point2D point,Vector2D lv,float radius) 
 {
   Vector2D shift;	
  
  if(point.x<=0)
   {  
     shift.x=-(radius);
     shift.y=0;
     shift.rotate(rotAngle);
   }
	  	 
  if(point.x>=width)
   {  
     shift.x=radius;
     shift.y=0;
     shift.rotate(rotAngle);
   }
    
  if(point.y<=0)
   {  
    shift.y=-radius;
    shift.x=0;
    shift.rotate(rotAngle);
   }
	  	 
 if(point.y>=height)
  {  
    shift.y=radius;
    shift.x=0;
    shift.rotate(rotAngle);
  }
  return shift;
 }//Fine calcShift
///////////////// 
  
