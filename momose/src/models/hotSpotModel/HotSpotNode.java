package models.hotSpotModel;

import java.util.Iterator;
import java.util.Vector;

import math.Point2d;
import math.Vector2d;
import models.Node;

import engine.Connection;
import engine.HotSpot;
import engine.SimTime;


public class HotSpotNode extends Node
 {
  protected Vector hotSpots;
  
  protected float vMax;
  protected float vMin;
  protected float pauseTime;
  
  protected float localTime;
  protected boolean pause;
  protected HotSpot targetSpot;
	
  public HotSpotNode(Point2d pos, float radius, Vector hotSpots) 
   {
    super(pos,radius);
    this.hotSpots=hotSpots;
    
    vMax=5;
    vMin=1;
    localTime=0;
    pauseTime=2;
    pause=true;
    
    targetSpot=null;
   }//Fine costruttore 
  
  public void setVelocity(float vMin,float vMax)
   { this.vMin=vMin; this.vMax=vMax; }
 
  public void setPauseTime(float pauseTime)
   { this.pauseTime=pauseTime; }

 public void think(SimTime time)
  {
   //Se ho sbattuto contro un muro  calcolo nuva direzione	 
   if(collided==true)
    resolveCollision(time);
  else
   {
	//System.out.println(toString());	 
	if(pause==true)
	  {
	   if(localTime<pauseTime)
	    {v.x=0; v.y=0;}
	   else
	    {
	     pause=false;
	     
	     //Calcolo un nuovo target
	     targetSpot=nextSpot();
	     
	     if(targetSpot==null)
	    	pause=true;
	     else
	      moveToTarget(time);//Mi muovo verso il target
	     }	 
	   }
	  else
	   {moveToTarget(time);} //Mi muovo verso il target    
    }	
   
   //Incremento il tempo locale  
   localTime+=time.getDT();
  }//Fine think
 
 
 private void moveToTarget(SimTime time)
  {
   Point2d target=null;	 
   //Calcolo il target
   if(targetSpot!=null)
	target=targetSpot.getPosition();
   else
    target=getPosition();
   
   //Creo un vettore verso il target
   Vector2d toTarget=new Vector2d(getPosition(),target);
   //Normalizzo il vettore verso il target
   Vector2d actV=toTarget.unitVector();
   //Calcolo la velocità e la direzione del nodo
   float velocity=(float)(Math.random()*vMax+vMin);
	 
   actV.x*=velocity;
   actV.y*=velocity;  
   
   //Rapporto la velocità all'intervallo di tempo scelto
   actV.x*=time.getDT();
   actV.y*=time.getDT();
	 
    //Calcolo la distanza dal target	 
	float distance=Point2d.distance(getPosition(),target); 
	 
	if(distance<=actV.getLength())
       {v=toTarget;}  
	  else
	   {v=actV;} 
	
	 Point2d nextPoint=new Point2d(getPosition(),v); 
	 
	 float finalDist=Point2d.distance(nextPoint,target);
     //Se sono arrivato a destinazione mi fermo
	 if(finalDist<=getRadius())
	   {
		pause=true;
		localTime=0;
	   } 
 }//Fine movingToTarget 
 
 private void resolveCollision(SimTime time)
  {
   //Calcolo il nuovo vettore velocità	
   v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
   v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
	     
   //Rapporto la velocità all'intervallo di tempo 
   v.x=v.x*time.getDT();
   v.y=v.y*time.getDT();  	 
  }//Fine resolveCollision
 
 private HotSpot nextSpot()
 {
  if(targetSpot==null)
	 return nearestSpot();  
  else
   {
	 Vector connections=targetSpot.getConnections();
	 if(connections.size()>0)
	  {
	  Connection firstConn=(Connection)connections.elementAt(0);
	   return firstConn.getEndSpot();
	  }
	 else
	  return null;	 
  }   
 }//Fine nextSpot 
 
 protected HotSpot nearestSpot()
  {
   HotSpot nearest=null;
   float near;
   
   Iterator i=hotSpots.iterator();
   if(i.hasNext()!=false)
    {
	 nearest=(HotSpot)i.next();
	 near=Point2d.distance(getPosition(),nearest.getPosition());
	}
   else
	return null;
   
   while(i.hasNext())
    {
	 HotSpot nextSpot=(HotSpot)i.next();
	 float distance=Point2d.distance(getPosition(),nextSpot.getPosition());
	 if(distance<near)
	  {
	   nearest=nextSpot;
	   near=Point2d.distance(getPosition(),nextSpot.getPosition());	   	 
	  }	 
    }   
   return nearest;	 
  }//Fine nearestSpot
 
 }//Fine HotSPotNode
