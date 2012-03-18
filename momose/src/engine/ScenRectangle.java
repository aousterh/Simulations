package engine;


import java.awt.Color;


import javax.media.opengl.GL;

import viewer.SimArenaGL;

import math.Point2d;
import math.Ray2d;
import math.Vector2d;
import models.Node;


public class ScenRectangle extends Building 
 {
  private float width;
  private float height;
  private float rotAngle;
	
  public ScenRectangle(Point2d pos,float width,float height)
   {
    super(pos);
    this.width=width;
    this.height=height;
    rotAngle=0;
   }//Fine costruttore
  
  public Point2d getPosition()
   {return pos;}
  
  public float getWidth()
   {return width;}
  
  public float getHeight()
   {return height;}
  
  public void setRotAngle(float angle)
   {this.rotAngle=angle;}
  
  public float getRotAngle()
   {return rotAngle;}
  
  public Point2d getMinBound()
   {return new Point2d(pos);}
  
  public Point2d getMaxBound()
  {return new Point2d(pos.x+width,pos.y+height);}

  
  public void draw(GL gl, SimArenaGL arena) 
   {
    //Salvo la matrice corrente
	gl.glPushMatrix();
	  //Sposto il sistema di coordinate
	  gl.glTranslatef(pos.x,pos.y,0);
	  //Ruoto il rettangolo
	  gl.glRotatef(rotAngle,0,0,1);
		  
	  //Disegno il rettangolo interno 
	 arena.setActColor(gl,fill);
	  gl.glBegin(gl.GL_POLYGON);
	  gl.glVertex2f(0,0);
	  gl.glVertex2f(0,height);
	  gl.glVertex2f(width,height);
	  gl.glVertex2f(width,0);
	 gl.glEnd();
		  
	  // Disegno il rettangolo di bordo 
	  arena.setActColor(gl,border);
	  gl.glBegin(gl.GL_LINE_LOOP);
	   gl.glVertex2f(0,0);
	   gl.glVertex2f(0,height);
	   gl.glVertex2f(width,height);
	   gl.glVertex2f(width,0);
	  gl.glEnd();
		  
	   //Riprendo la matrice corrente
	   gl.glPopMatrix();	 		
   }//Fine draw  

public float intersect(Node node) 
 {
  //Controllo se il nodo è dentro il rettangolo
  //Se si lo faccio uscire	
  if(nodeOverlap(node))
   {
	Point2d pt=new Point2d(node.getPosition());
	//Trasformo il punto nelle coordinate locali del rettangolo
	pt.translate(-pos.x,-pos.y);
	pt.rotate(-rotAngle);
	
	Vector2d lv=node.getVelocity();
	lv.rotate(-rotAngle);
		 
	Vector2d shift=calcShift(pt,lv,node.getRadius()+0.01f);
	node.setPosition(shift);
	//return 0;
   }		
	
	
  float radius=node.getRadius();	
  ScenRectangle boundRect=new ScenRectangle(new Point2d(pos.x-radius,pos.y-radius),
		                                    width+radius*2,height+radius*2);
  
  //Creo il raggio e lo trasformo nelle coodinate locali del rettangolo
  Ray2d ray=transformRay(new Ray2d(node.getPosition(),node.getVelocity()));
  
  //Lo devo traslare perchè calcolo 
  //l'intersezione con un rettangolo allargato
  ray.o.x+=radius; ray.o.y+=radius;
  
  //Calcolo l'intersezione
  return boundRect.rayLocalIntersect(ray);
 }//Fine intresect

private Ray2d transformRay(Ray2d ray)
 {
  Ray2d transfRay=new Ray2d(ray);	
  
  //Traslo e ruoto il punto 
  transfRay.o.translate(-getPosition().x,-getPosition().y);
  transfRay.o.rotate(-rotAngle);
  
  //Ruoto il vettore velocità
  transfRay.d.rotate(-rotAngle);
  
  //Ritorno il aggio trasformato
  return transfRay;
 }//Fine transformRay

public float rayIntersect(Ray2d ray)
 {
  //Trasformo il raggio
  Ray2d rayTransf=transformRay(ray);	
  //Calcolo e ritorno l'intersezione tra il raggio 
  //trasformato e il rettangolo
  return rayLocalIntersect(rayTransf);
 }//Fine rayIntersect

public float rayLocalIntersect(Ray2d ray)
 {
  Point2d origin=ray.o;
  Vector2d direction=ray.d;
  
 /* Point2d bound0=getMinBound();
  bound0.set(0,0);
  Point2d bound1=getMaxBound();
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
 }//Fine rayLocalIntersect

private float rayBoxInternalIntersect(Ray2d r)
 {
	float tmin,tmax,tymin,tymax;
	Point2d origin=r.o;
	Vector2d direction=r.d;  
	
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
}//Fine rayIntersect

 public boolean pointOverlap(Point2d point) 
  {
   //Trasformo il punto nelle coordinate locali del rettangolo
   Point2d pt=new Point2d(point);
   pt.translate(-pos.x,-pos.y);
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

 public boolean nodeOverlap(Node node) 
  {
   Point2d pCtrl=new Point2d(node.getPosition());
   //Trasformo il punto nelle coordinate locali del rettangolo
   pCtrl.translate(-pos.x,-pos.y);
   pCtrl.rotate(-rotAngle);
   
  /* Point2d pTest=new Point2d(node.getPosition()); 
   //Trasformo il punto nelle coordinate locali del rettangolo
   pTest.translate(-pos.x,-pos.y);
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
		  (pCtrl.y-pTest.y)*(pCtrl.y-pTest.y))<=radius2);/*/
  }//Fine nodeOverlap

 
/////////////////AGGIUNTO
 ///////////////
private Vector2d calcShift(Point2d point,Vector2d lv,float radius) 
{
 Vector2d shift=new Vector2d();	
 
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
 
 
 public String toXml() 
  {
   String xmlStr=new String(" <Rect x=\""+pos.x+"\" y=\""+pos.y+"\" width=\""+width+"\" height=\""+height+"\"");
             
   
   //Rotazione (dato opzionale)
   if(rotAngle!=0)
    { xmlStr+=" rotation=\""+rotAngle+"\""; }
   
   //Nome (dato opzionale)
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
   
   //attenuazione (dato obbligatorio)
   xmlStr+=" attenuation=\""+getAttenuation()+"\"/>\n";
   
   return xmlStr;
  }//Fine toXml
}//Fine ScenRectangle
