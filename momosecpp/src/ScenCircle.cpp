#include<cmath>

#include "ScenCircle.h"


ScenCircle::ScenCircle(const Point2D &pos,float radius):Building(pos)
 {
  this->radius=radius;
 }//Fine costruttore
 
float ScenCircle::intersect(Node *node)
 {
   /////////////////
  if(nodeOverlap(*node))
   {
     Vector2D shift(this->p,node->getPosition());
     shift.normalize();
      shift*=(node->getRadius()+0.01f);
      node->setPosition(shift);
      //return 0;
   }	 
  ////////////////////////	 
	 
	 
  //Creo il raggio dal nodo	
  Ray2D ray(node->getPosition(),node->getVelocity());
  
  //Creo il cerchio per l'intersezione
  ScenCircle interCircle(getPoint(),getRadius()+node->getRadius());
  
  //Calcolo l'intersezione tra il raggio e il cerchio
  return interCircle.rayIntersect(ray);
 }//Fine intersect 
 
float ScenCircle::rayIntersect(const Ray2D &ray)
 {
  /*Metto ad equazione la funzione del raggio paramentrizzato (O+Vt)
   * e del cerchio: (C+r) -> O+Vt=C+r da cui Vt+D=r (D=O-C) 
   * Poi esplicito la funzione per t
   *  V^2*t^2+2VDt+D^2-r^2=0
   *  
   *  facendo le seguneti sostituzioni:
   *  a=v^2  b=2VD c=D^2-r^2
   *  ottengo l'equazione at^2+bt+c=0 
   *  
   *  Risolvendo l'ultima equazione e prendendo 
   *  il valore più piccolo ho il valore del parameto t 
   *  per la prima intesezione con il cerchio 
   *  
   */ 	
  
  //Creo il vettore direzione del raggio	
  Vector2D v(ray.d);
  
  //Il vettore e' un punto
  if(v.isZero()==true)
   return -1;	  
  
  //Creo il vettore tra il centro del cerchio e l'origine del raggio (D=O-C)
  Vector2D d(this->p,ray.o);
   
  //Calcolo il quadrato di D 
  //float dd=d.scalarMul(d);
  float dd=d*d;
  //Calcolo il prodotto scalare tra V e D
  //float vd=v.scalarMul(d);
  float vd=v*d;
  //Mi sto allontanando dal cerchio
  if(vd>0)
   return -1;
  
  //Calcolo il quadrato di r
  float rr=radius*radius;
  
  //L'origine del raggio è dentro il cerchio
    //if(dd<rr)
     //return -1;
  
  //Calcolo i coefficienti a b e c dell'equazione finale
  //float a=v.scalarMul(v);
  float a=v*v;
  float b=2*vd;
  float c=dd-rr;
  float delta=b*b-4*a*c;
  
  //Il raggio non tocca il cerchio
  if(delta<0)
	return -1;
  
  //Risolvo l'equazione calcolando
  //il valore inferiore di t e lo restituisco
  return (float)fabs(((-b-sqrt(delta))/(2*a)));
 }//Fine rayIntersect 

bool ScenCircle::nodeOverlap(Node &node)
 {
  float sumRadius=this->radius+node.getRadius();
  if(Point2D::distance(this->p,node.getPosition())<=sumRadius)	 
    return true;
   else
	return false;  
 }//Fine nodeOverlap

bool ScenCircle::pointOverlap(const Point2D &point)
 {
  if(Point2D::distance(this->p,point)<=radius)
	return true;
   else
	return false;
 }//Fine pointOverlap
  
 
void ScenCircle::toXml(ostream &out)
 {
  //Dati obbligatori	 
  out<<" <Circle x=\""<<p.x<<"\" y=\""<<p.y<<"\" radius=\""<<radius<<"\"";
	
  //Nome (dato opzionale)	 
  if(getName()!="")	 
     out<<" name=\""<<getName()<<"\""; 
  
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
  
   //Attenuazione (obbligatorio)
   out<<" attenuation=\""<<getAttenuation()<<"\"/>\n";	
 }//Fine toXml 

ScenCircle::~ScenCircle(){}
