package models.simpleModelStructures;

import models.Model;

import engine.SimTime;

public abstract class SimpleModel extends Model
 {
  protected int numNodes;
  protected float nodeRadius;
  protected float vMin;
  protected float vMax;
  protected float antennaRadius;
  protected float pauseTime;
	  
  public SimpleModel()
   {
    numNodes=10;
    nodeRadius=0.5f;
    vMin=0;
    vMax=5;
    antennaRadius=nodeRadius*10;
    pauseTime=1;   
		  
    isPhysical=false;  
    isThinker=false;
   }//Fine costruttore
	 
  public void setModel(int numNodes,float nodeRadius,float antennaRadius,
		               float pauseTime,float vMin,float vMax,
		               boolean isPhysical)
   {
	this.numNodes=numNodes; this.nodeRadius=nodeRadius;
	this.antennaRadius=antennaRadius; this.pauseTime=pauseTime;
	this.vMax=vMax; this.vMin=vMin;
	this.isPhysical=isPhysical;
    }//Fine setModel
 
 public void think(SimTime time){}
}//Fine classe RandomWaypointModel