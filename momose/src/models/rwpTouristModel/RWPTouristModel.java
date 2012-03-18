package models.rwpTouristModel;

import java.awt.Color;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;

import models.randomWalkModel.RandomWalkNode;
import models.simpleModelStructures.SimpleModel;

public class RWPTouristModel extends SimpleModel
 {
  public RWPTouristModel()
   {super();}
	 
  public void setup(Scenario scenario,SimTime time) 
   {
    /*for(int  i=0;i<numNodes;i++)
	  {
	   //float p=2+i*1.5f;  
	   float p=5+i;
	   RWPTuristNode newNode=new RWPTuristNode(new Point2d(p,p),nodeRadius,scenario);
	   newNode.setColor(Color.ORANGE);
	   newNode.setAntennaRadius(antennaRadius);
       newNode.setSpeedBounds(vMin,vMax);
	   newNode.setPauseTime(pauseTime);
	   nodes.add(newNode);
	  }*/  
	  
	  
	  float x,y;
	  int antenna;
		 
	   for(int i=1;i<=numNodes;i++)
		{ 
		 x=(float)(Math.random())*(scenario.getWidth()-10);
		 y=(float)(Math.random())*(scenario.getHeight()-10);
		 RWPTouristNode newNode=new RWPTouristNode(new Point2d(x,y),nodeRadius,scenario);
		 newNode.setColor(Color.ORANGE);
		 newNode.setSpeedBounds(vMin,vMax);
		 newNode.setPauseTime(pauseTime);
		 
		 
         //Raggio antenna statico
		 //newNode.setAntennaRadius(generateAntennaRadius());
		 newNode.setAntennaRadius(antennaRadius);
		 
		 //Raggio antenna dinamico
		 /*float rad=generateAntennaRadius();
		 newNode.onAntenna=rad;
		 newNode.setAntennaRadius(0);*/
		 
		 
         //Aggiungo il nodo		
		 nodes.add(newNode);
		}
	  }//Fine generate nodes	  
  	  
	  
  private float generateAntennaRadius()
   {
    if(Math.random()<0.5)
	  return 10;  
    if(Math.random()<0.9)
	  return 20;  
    return 100;
   }//Fine generateAntenna Radius
 }//Fine classe RandomWaypointModel