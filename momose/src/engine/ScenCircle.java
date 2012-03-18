package engine;


import java.awt.Color;

import javax.media.opengl.GL;

import viewer.SimArenaGL;

import math.Point2d;
import math.Ray2d;
import math.Vector2d;

import models.Node;



public class ScenCircle extends Building 
 {
  private float radius;
  
  public ScenCircle(Point2d pos,float radius)
  {
   super(pos);
   this.radius=radius;
  }//Fine costruttore
  
  public Point2d getPosition()
   {return pos;}
  
  public float getRadius()
   {return radius;}
  
  public String toXml() 
  {	
   String xmlStr=new String(" <Circle x=\""+pos.x+"\" y=\""+pos.y+"\" radius=\""+radius+"\"");
   
   //nome (Dato opzionale)
   if(getName()!=null)
    { xmlStr+=" name=\""+getName()+"\""; } 
   
   //Colore riempimento (Dato opzionale)
   Color fillCol=getFillColor(); 
   if((fillCol.getRed()!=128)||
     (fillCol.getGreen()!=128)||
     (fillCol.getBlue()!=128))
     { 
	  xmlStr+=" fill=\""+fillCol.getRed()+","+
	                     fillCol.getGreen()+","+
	                     fillCol.getBlue()+"\""; 
	 }
   
   //Colore bordo (Dato opzionale)
   Color borderCol=getBorderColor(); 
   if((borderCol.getRed()!=255)||
     (borderCol.getGreen()!=0)||
     (borderCol.getBlue()!=0))
     { 
	  xmlStr+=" border=\""+borderCol.getRed()+","+
	                       borderCol.getGreen()+","+
	                       borderCol.getBlue()+"\""; 
	 }  
   
   //Atteanuzione (obbligatorio)
   xmlStr+=" attenuation=\""+getAttenuation()+"\"/>\n";
   
   return xmlStr; 	
  }//Fine toXml

  
  
  public void draw(GL gl, SimArenaGL arena) 
   {
    //Salvo la matrice corrente
    gl.glPushMatrix();
	//Sposto il sistema di coordinate
	gl.glTranslatef(pos.x,pos.y,0);
	  
	//Disegno il cerchio interno
	arena.setActColor(gl,fill);
	gl.glScalef(radius,radius,0);
	//Disegno il cerchio memorizzato nella displayLIst
	gl.glCallList(1);
	  
	//Disegno il bordo
	arena.setActColor(gl,border);
	//Disegno il cerchio memorizzato nella displayLIst
	gl.glCallList(2);
		  
	//Riprendo la matrice corrente
	gl.glPopMatrix();  		
   }//Fine draw  
  

public float intersect(Node node) 
 {
  //Controllo se il nodo è dentro il cerchio
  //se si lo faccio uscire	
  if(nodeOverlap(node))
   {
	Vector2d shift=new Vector2d(pos,node.getPosition());
	shift.normalize();
	shift.dotMul(node.getRadius()+0.01f);
	node.setPosition(shift);
	//return 0;
   }	 

  //Creo il raggio dal nodo	
  Ray2d ray=new Ray2d(node.getPosition(),node.getVelocity());
  
  //Creo il cerchio per l'intersezione
  ScenCircle interCircle=new ScenCircle(getPosition(),getRadius()+node.getRadius());
  
  //Calcolo l'intersezione tra il raggio e il cerchio
  return interCircle.rayIntersect(ray);
 }//Fine intersect

public float rayIntersect(Ray2d ray) 
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
  Vector2d v=new Vector2d(ray.d);
  
  //Il vettore e' nullo
  if(v.isZero())
	return -1;   
  
  //Creo il vettore tra il centro del cerchio e l'origine del raggio (D=O-C)
  Vector2d d=Point2d.getVector2d(this.pos,ray.o);
  
  //Calcolo il quadrato di D 
  float dd=d.scalarMul(d);
  //Calcolo il prodotto scalare tra V e D
  float vd=v.scalarMul(d);
  
  //Mi sto allontanando dal cerchio
  if(vd>0)
   return -1;
  
  //Calcolo il quadrato di r
  float rr=radius*radius;
  
  //L'origine del raggio è dentro il cerchio
    //if(dd<rr)
     //return -1;
  
  //Calcolo i coefficienti a b e c dell'equazione finale
  float a=v.scalarMul(v);
  float b=2*vd;
  float c=dd-rr;
  float delta=b*b-4*a*c;
  
  //Il raggio non tocca il cerchio
  if(delta<0)
	return -1;
  
  //Risolvo l'equazione calcolando
  //il valore inferiore di t e lo restituisco
  return (float)Math.abs(((-b-Math.sqrt(delta))/(2*a)));
 }//Fine rayIntersect

 public boolean pointOverlap(Point2d point) 
  {
   if(Point2d.distance(this.pos,point)<=radius)
	return true;
   else
	return false;
  }//Fine pointOverlap 

 public boolean nodeOverlap(Node node) 
  {
   float sumRadius=this.radius+node.getRadius();
   //System.out.println("Distanza="+Point2d.distance(this.pos,node.getPosition()));
   if(Point2d.distance(this.pos,node.getPosition())<=sumRadius)	 
    return true;
   else
	return false;   
  }//Fine nodeOverlap
}//Fine ScenCircle