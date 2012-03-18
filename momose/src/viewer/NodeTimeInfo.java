package viewer;

import java.awt.Color;

import math.Point2d;

public class NodeTimeInfo 
 {
  private Point2d pos;
  private Color color;
  private float antennaRadius; 
  
  public NodeTimeInfo(Point2d pos,float antennaRadius, Color color)
   {
	this.pos=pos;
	this.antennaRadius=antennaRadius;
	this.color=color;
   }//Fine costruttore
  
  public Point2d getPos()
   {return pos;}
  
  public float getAntennaRadius()
   {return antennaRadius;}
  
  public Color getColor()
   {return color;}
  
  public String toString()
   { return new String("Pos="+pos+" ar="+antennaRadius+" rgb="+color.getRGB()); }
  
  public String toXml()
   {return " <Pos x=\""+pos.x+"\" y=\""+pos.y+
	      "\" ar=\""+antennaRadius+
	      "\" rgb=\""+color.getRGB()+
	      "\"/>\n" ;}
 }//Fine classe 
