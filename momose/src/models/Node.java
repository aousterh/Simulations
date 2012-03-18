package models;

import math.Point2d;
import math.Ray2d;
import math.Vector2d;

import engine.PhysicalObject;
import engine.ScenCircle;
import engine.ThinkerObject;

import viewer.GraphNode;

public abstract class Node extends GraphNode implements ThinkerObject,PhysicalObject  
 {
  //Variabili di simulazione	
  protected Vector2d v;
  protected boolean collided;
  
  public Node(Point2d pos,float radius)
   {
    super(pos,radius); 
    
    v=new Vector2d(0f,0f);
    collided=false;
   }//Fine costruttore
  
  public Vector2d getVelocity()
   {return v;}
  
  public void setVelocity(Vector2d v)
   {this.v=v;}
  
  public void setPosition(Point2d pos)
   {
	this.pos=pos;
	roundMM();
   }//Fine setPosition 
  
  public void setCollision(boolean collided)
   {this.collided=collided;} 
  
 public void setPosition(Vector2d v)
   {
	pos.addVector2d(v);
    //Arrotondo alla terza cifra decimale (mm) 
	roundMM();
   }//fine setPosition
  
  
  
 public String toString()
  {return new String("NODE-> pos="+pos+" v="+getVelocity()+
		             " radius="+radius+" antennaRadius="+antennaRadius);}
  
 private void roundMM()
   {
    //Arrotondo alla terza cifra decimale (mm)  
    this.pos.x=(float)(Math.round(pos.x*1000.0f)/1000.0f);  
	this.pos.y=(float)(Math.round(pos.y*1000.0f)/1000.0f);  
   }//Fine roundMM
  
 public final float intersect(Node node) 
  {
   //Creo il raggio dal nodo	
   Ray2d ray=new Ray2d(node.getPosition(),node.getVelocity());
	  
  //Creo il cerchio per l'intersezione
  ScenCircle interCircle=new ScenCircle(pos,radius+node.getRadius());
  
  //Calcolo l'intersezione tra il raggio e il cerchio
  return interCircle.rayIntersect(ray);   
 }//Fine intersect

  public final boolean nodeOverlap(Node node) 
   {
	float sumRadius=this.radius+node.getRadius();
	System.out.println("Distanza="+Point2d.distance(pos,node.getPosition()));
	if(Point2d.distance(pos,node.getPosition())<=sumRadius)	 
	  return true;
	 else
	  return false;   	  
	}//Fine nodeOverlap

  public final boolean pointOverlap(Point2d point) 
   {
	System.out.println("dist="+Point2d.distance(pos,point));  
	if(Point2d.distance(pos,point)<=radius)
	 return true;
	else
	 return false;
   }//Fine pointOverlap

  public final float rayIntersect(Ray2d ray) 
   {
    //Creo il vettore direzione del raggio	
	Vector2d v=new Vector2d(ray.d);
	
	//il vettore e' nullo
	if(v.isZero())
	 return -1;	
		
	//Creo il vettore tra il centro del cerchio e l'origine del raggio (D=O-C)
	Vector2d d=Point2d.getVector2d(pos,ray.o);
		  
	//Calcolo il quadrato di D 
	float dd=d.scalarMul(d);
	//Calcolo il prodotto scalare tra V e D
	float vd=v.scalarMul(d);
		  
    //Mi sto allontanando dal nodo
	 if(vd>0)
	   return -1;
	
	//Calcolo il quadrato di r
	float rr=radius*radius;
		  
	//Calcolo i coefficienti a b e c dell'equazione finale
	float a=v.scalarMul(v);
	float b=2*vd;
	float c=dd-rr;
	float delta=b*b-4*a*c;
		  
	//Risolvo l'equazione calcolando
	//il valore inferiore di t e lo restituisco
	return (float)Math.abs(((-b-Math.sqrt(delta))/(2*a)));	
   }//Fine rayIntersect  
 }//Fine node
