package models.hotSpotModel;

import java.awt.Color;
import java.util.Vector;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import models.Model;


public class HotSpotModel extends Model
 {
  private Vector hotSpots;	
	
  private int numNodes;
  private float nodeRadius;
  private float vMin;
  private float vMax;
  private float antennaRadius;
  private float pauseTime;
  
  public HotSpotModel()
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
		               float interval,float vMin,float vMax,
		               boolean isPhysical)
  {
   this.numNodes=numNodes; this.nodeRadius=nodeRadius;
   this.antennaRadius=antennaRadius; this.pauseTime=interval;
   this.vMax=vMax; this.vMin=vMin;
   this.isPhysical=isPhysical;
  }//Fine setModel
	
  public void setup(Scenario scenario, SimTime time) 
   {
    for(int  i=0;i<numNodes;i++)
	 {
	  float p=5+i;
	  HotSpotNode newNode=new HotSpotNode(new Point2d(p,p),nodeRadius,scenario.getHotSpots());
	  newNode.setColor(Color.MAGENTA);
	  newNode.setAntennaRadius(antennaRadius);
	  newNode.setVelocity(vMin,vMax);
	  newNode.setPauseTime(pauseTime);
	  nodes.add(newNode);
	 }
    
    //Salvo un riferimento agli hot spot
    hotSpots=scenario.getHotSpots();
   }//Fine setup

  public void think(SimTime time) {}
 }//Fine classe HotSpotModel
