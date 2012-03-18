package models.touristGroupModel;


import java.util.Vector;

import math.Point2d;

import models.hotSpotModel.HotSpotNode;

import engine.Connection;
import engine.HotSpot;

public class TouristGuideNode extends HotSpotNode
 {
  HotSpot lastSpot;	
  
  public TouristGuideNode(Point2d pos,float radius,Vector hotSpots) 
   {
    super(pos,radius,hotSpots);
    lastSpot=null;
    pause=false;
     
   }//Fine costruttore
  
  public void setPauseTime(float time)
   {pauseTime=time;}
   
  
  public void setTargetNode(HotSpot targetSpot) 
   {this.targetSpot=targetSpot;} 
  
  public void setlastTarget(HotSpot lastSpot) 
  {this.lastSpot=lastSpot;} 
 
 protected HotSpot nextSpot()
  {
   Connection conn=null;	 
	 
   if(targetSpot==null)
    return nearestSpot();  
   else
    {
	 Vector connections=targetSpot.getConnections();
	 int cSize=connections.size();
	 
	 if(cSize>0)
	  {
	   int index=(int)(Math.random()*connections.size()); 
	   conn=(Connection)connections.elementAt(index);
	   
	   if(conn.getEndSpot()==lastSpot)
	    {
		 if(index<cSize-1)
		  conn=(Connection)connections.elementAt(index+1);
		 else
		  if(index>0)
			conn=(Connection)connections.elementAt(index-1);
		   else
		    return lastSpot;
		    		
		 lastSpot=targetSpot;
		 return conn.getEndSpot();
		}
	   else
	    {
		 lastSpot=targetSpot;
		 return conn.getEndSpot();  
	    }
	   }
	   return lastSpot;
	  }
  }//Fine nextSpot 
  
  
 public void setParams(float antennaRadius,float vMax,float vMin)
   {
	setAntennaRadius(antennaRadius);  
	this.vMin=vMin;
    this.vMax=vMax;
   }//Fine setModelParam 
 
}//Fine HotSPotNode
