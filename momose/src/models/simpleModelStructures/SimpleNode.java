package models.simpleModelStructures;


import math.Point2d;
import models.Node;


public abstract class SimpleNode extends Node
 {
  protected float vMax;
  protected float vMin;
  protected float pauseTime;
  
  public SimpleNode(Point2d pos, float radius) 
   {
	super(pos,radius);
	vMax=5;
	vMin=0;
	pauseTime=1f;
  }//Fine costruttore
  
  public void setSpeedBounds(float vMin,float vMax)
   { this.vMin=vMin; this.vMax=vMax; }
  
  public void setPauseTime(float pauseTime)
   { this.pauseTime=pauseTime; }
 }//Fine SimpleNode