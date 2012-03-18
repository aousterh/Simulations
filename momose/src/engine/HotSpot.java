package engine;


import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import javax.media.opengl.GL;

import viewer.SimArenaGL;

import math.Point2d;


public class HotSpot extends ScenarioElement
 {
  private float radius;
  private int ID;
  private Vector connections;
  
  public HotSpot(Point2d pos,float radius,int ID)
   {
	super(pos);  
    this.radius=radius;
    this.ID=ID;
    connections=new Vector();
   }//Fine costruttore
  
  public Point2d getPosition()
   {return pos;}
 
  public float getRadius()
   {return radius;}
  
  public void setID(int ID)
   { this.ID=ID; }
 
  public int getID()
   { return ID; }
  
  public void createConnection(HotSpot hotSpot,float weight)
   { connections.add(new Connection(this,hotSpot,weight)); }
  
  public void createConnection(HotSpot hotSpot)
   { connections.add(new Connection(this,hotSpot)); }
  
  public void addConnection(Connection newConn)
   {connections.add(newConn);}
  
  public void addConnections(Vector newConns)
   {connections.addAll(newConns);}
  
  public Vector getConnections()
   {return connections;} 
  
  
  public void draw(GL gl, SimArenaGL arena) 
   {
	if(arena.getDrawHotSpot())
	 {  
	  //Salvo la matrice corrente
	  gl.glPushMatrix();
	  //Sposto il sistema di coordinate
	  gl.glTranslatef(pos.x,pos.y,0);
	  //Disegno il cerchio interno
	  arena.setActColor(gl,Color.LIGHT_GRAY);
	  gl.glScalef(radius,radius,0);
	  gl.glCallList(1);
	  //Riprendo la matrice corrente
	  gl.glPopMatrix();	
	  
	  //Stampo le connesioni di questo hotSpot
	 drawHotConnections(gl,arena);
	} 		
   }//Fine draw
  
private void drawHotConnections(GL gl,SimArenaGL arena)
  {
   Iterator i=connections.iterator();
   while(i.hasNext())
    {
     Connection nextConn=(Connection)i.next();
     HotSpot startSpot=nextConn.getStartSpot();
     HotSpot endSpot=nextConn.getEndSpot();
     arena.drawLine(gl,startSpot.getPosition(),endSpot.getPosition(),Color.LIGHT_GRAY);
    }   
  }//Fine drawHotConnections  
  
 
  public String toXml()
   {
	return new String(" <HotSpot ID=\""+ID+"\" x=\""+pos.x+"\" y=\""+pos.y+
			          "\" radius=\""+radius+"\"/>\n"); 
   }//Fine toXml

}//Fine classe HotSpot
