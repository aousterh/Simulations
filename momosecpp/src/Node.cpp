#include "Node.h"
#include "ScenCircle.h"

#include<cmath>

Node::Node(const Point2D &pos,const float &radius):pos(pos),v(0.0f,0.0f),color(0,0,255)
 {
  if(radius<0.01f)
   this->radius=0.01f;
  else
   this->radius=radius;
    	 
  antennaRadius=radius*10;
    
  collided=false;
 }//Fine costruttore
 
 Node::~Node(){} 
 
void Node::setAntennaRadius(const float &antennaRadius)
 {
  if(antennaRadius<0)
    this->antennaRadius=0;
   else
	this->antennaRadius=antennaRadius;
 }//Fine setAntennaRadius 
 
void Node::roundMM()
   {
    //Arrotondo alla terza cifra decimale (mm)  
    this->pos.x=(float)(floor((int)((pos.x*1000.0f)+0.5))/1000.0f);  
	this->pos.y=(float)(floor((int)((pos.y*1000.0f)+0.5))/1000.0f);  
   }//Fine roundMM 
   
void Node::setPosition(const Point2D &pos)
 {
  this->pos=pos;
  roundMM();
 }//Fine setPoisition    
   
void Node::setPosition(const Vector2D &v)
 {
  pos+=v;
  //Arrotondo alla terza cifra decimale (mm) 
  roundMM();
 }//fine setPosition  
 
//Overloading degli operatori
ostream& operator<<(ostream& output,const Node& node) 
 {
  output<<"pos="<<node.pos<<",v="<<node.v<<",r="<<node.radius
        <<",ar="<<node.antennaRadius
        <<",col="<<node.color;
  return output;
 }//Fine overloading I/O  
 
float Node::intersect(Node *node) 
  {
   //Creo il raggio dal nodo	
   Ray2D ray(node->getPosition(),node->getVelocity());
	  
  //Creo il cerchio per l'intersezione
  ScenCircle interCircle(pos,radius+node->getRadius());
  
  //Calcolo l'intersezione tra il raggio e il cerchio
  return interCircle.rayIntersect(ray);   
 }//Fine intersect 
 
float Node::rayIntersect(const Ray2D &ray)
 {
  //Creo il cerchio per l'intersezione
  ScenCircle interCircle(pos,radius);
  //Calcolo l'intersezione tra il raggio e il cerchio
  return interCircle.rayIntersect(ray); 	
 }//Fine rayIntersect	
 
bool Node::nodeOverlap(Node &node) 
   {
	float sumRadius=this->radius+node.getRadius();
	if(Point2D::distance(this->pos,node.getPosition())<=sumRadius)	 
	  return true;
	 else
	  return false;   	  
	}//Fine nodeOverlap 
 
bool Node::pointOverlap(const Point2D &point) 
 {
  if(Point2D::distance(this->pos,point)<=radius)
   return true;
  else
   return false;
 }//Fine pointOverlap 


