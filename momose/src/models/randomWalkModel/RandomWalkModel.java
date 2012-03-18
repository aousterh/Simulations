package models.randomWalkModel;

import java.awt.Color;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;

import models.simpleModelStructures.SimpleModel;

public class RandomWalkModel extends SimpleModel
 {
  public RandomWalkModel()
   {super();}
  
 public void setup(Scenario scenario,SimTime time) 
  {
   int px=0; int py=0;
  		
   for(int i=1;i<=numNodes;i++)
	{   
	 RandomWalkNode newNode=new RandomWalkNode(new Point2d(px+25,py+25),nodeRadius);
	 newNode.setColor(Color.BLUE);
	 newNode.setAntennaRadius(antennaRadius);
	 newNode.setSpeedBounds(vMin,vMax);
	 newNode.setPauseTime(pauseTime);
	 nodes.add(newNode);
	
	 px++;
	  
	 if(((i%30)==0)&&(i!=0))
	   {px=0; py++;}	 
	}     	  
	 
  }//Fine generate nodes
}//Fine classe RandomWalkModel
