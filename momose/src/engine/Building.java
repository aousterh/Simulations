package engine;


import java.awt.Color;

import math.Point2d;

public abstract class Building extends ScenarioElement implements PhysicalObject
 {
  private String name;
  private float attenuation;
  protected Color fill;
  protected Color border;
  
  public Building(Point2d pos)
   {
	super(pos);  
    name=null;
    attenuation=0;
    fill=Color.GRAY; 
    border=Color.RED;  
   }//Fine costruttore
  
  public void setName(String name)
   {this.name=name;}
  
  public String getName()
   {return name;}
  
  public void setAttenuation(float attenuation)
  {
   this.attenuation=attenuation;	  
   
   if(this.attenuation>1)
	 this.attenuation=1;
   if(this.attenuation<0)
     this.attenuation=0;
  }//Fine setAttenuation
 
 public float getAttenuation()
  {return attenuation;}
 
 public void setFillColor(Color color)
  {fill=color;}

 public void setBorderColor(Color color)
  {border=color;}
 
  public Color getFillColor()
   {return fill;}
  
  public Color getBorderColor()
   {return border;}
 }//Fine classe Building
