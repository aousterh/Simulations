package models.randomWaypointModel;

import java.awt.Color;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;

import models.simpleModelStructures.SimpleModel;

public class RandomWaypointModel extends SimpleModel
 {
  public RandomWaypointModel()
   {super();}
	 
  public void setup(Scenario scenario,SimTime time) 
   {
    for(int  i=0;i<numNodes;i++)
	  {
	   //float p=2+i*1.5f;  
	   float p=5+i;
	   RandomWaypointNode newNode=new RandomWaypointNode(new Point2d(p,p),nodeRadius,scenario);
	   newNode.setColor(Color.ORANGE);
	   newNode.setAntennaRadius(antennaRadius);
       newNode.setSpeedBounds(vMin,vMax);
	   newNode.setPauseTime(pauseTime);
	   nodes.add(newNode);
	  }  
   }//Fine generate nodes 
 }//Fine classe RandomWaypointModel