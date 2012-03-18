package viewer;

import java.awt.Color;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import math.Point2d;

public abstract class GraphNode 
 {
  protected Point2d pos;
  protected float radius;
  protected float antennaRadius;
  protected Color color;
  protected String id;
	
  public GraphNode(Point2d pos,float radius)
   {
    this.pos=pos;	 	  
    
    if(radius<0.01f)
	  this.radius=0.01f;
	 else
	  this.radius=radius;
		    	 
    antennaRadius=radius*10;
    color=Color.BLUE;
    id="0";
   }//Fine costruttore
	
  public Point2d getPosition()
   {return pos;}
 
  public float getRadius()
   {return radius;}
  
  public Color getColor()
   { return color; }
  
  public void setColor(Color color)
   { this.color=color; }
 
  public void setAntennaRadius(float antennaRadius)
   {
	if(antennaRadius<0)
    this.antennaRadius=0;
	else
	 this.antennaRadius=antennaRadius;
   }//Fine setAntennaRadius
 
  public float getAntennaRadius()
   {return antennaRadius;}
  
  public String getId()
   { return id; }
 
  public void setId(String id)
   { this.id=id; }
  
  public void setId(int iId)
   { this.id=Integer.toString(iId); }
  
  public final void draw(GL gl,SimArenaGL arena)
   {
    //Converto le coordinate	 
    //Salvo la matrice corrente
    gl.glPushMatrix();
    //Sposto il sistema di coordinate
    gl.glTranslatef(pos.x,pos.y,0);
    gl.glPushMatrix();
    //Disegno il cerchio
    arena.setActColor(gl,color);
    gl.glScalef(radius,radius,0);
    
    //Disegno il cerchio memorizzato nella displayLIst
    gl.glCallList(1);
   
    //Salvo la matrice corrente
    gl.glPopMatrix();
   
   //Disegno l'id del nodo 
   if(arena.getDrawNodeId())
    {
	 //Sposto   
	 //gl.glTranslatef(radius,-radius,0);  
	 gl.glTranslatef(0,-radius,0);  
	 //Applico la rotazione per mettere il testo dritto 
	 gl.glRotatef(180,1.0f,0,0);
	 //Applico la rotazione 
	 //gl.glRotatef(rot,0,0,1.0f);
	 //Stampo il testo
	 arena.setActColor(gl,new Color(0,0,0));
	 //gl.glScalef(0.002f*arena.getScale(),0.002f*arena.getScale(),0);
	 gl.glScalef(0.012f,0.012f,0);
	 arena.renderStrokeString(gl,GLUT.STROKE_ROMAN,id);
	}
  
   //Riprendo la matrice corrente
   gl.glPopMatrix();	 
 }//Fine drawCircle   
  
  
 }//Fine classe GraphNode
