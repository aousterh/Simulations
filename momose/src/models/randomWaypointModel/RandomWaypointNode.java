package models.randomWaypointModel;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import math.Vector2d;
import models.simpleModelStructures.SimpleNode;



public class RandomWaypointNode extends SimpleNode
 {
  protected boolean pause;
  protected float localTime;
 
  protected Point2d target;
  protected Vector2d toTarget;
  protected Scenario scenario;
 
  public RandomWaypointNode(Point2d pos, float radius,Scenario scenario) 
   {
    super(pos,radius);
      
    pause=true;
    target=new Point2d();
    toTarget=new Vector2d(); 
    localTime=0;
    this.scenario=scenario;
   }//Fine costrutore
 
 public void think(SimTime time) 
  {
   //Se ho sbattuto contro un muro  calcolo nuva direzione	 
   if(collided==true)
    {
   	//pause=true;
   	//Calcolo il nuovo vettore velocità	
      v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
	  v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
      
	  //Rapporto la velocità all'intervallo di tempo 
	  v.x=v.x*time.getDT();
	  v.y=v.y*time.getDT();
    }
   else
   	{
     //System.out.println(toString());	 
     if(pause==true)
      {
	   if(localTime<pauseTime)
	    {v.x=0; v.y=0;}
	  else
	   {//System.out.println("Target="+target);
	        //System.out.println("nextPoint="+nextPoint);
		    //System.out.println("Node position="+getPosition());
		    //System.out.println("distance="+finalDist);
	    pause=false;
	    //Calcolo un nuovo target
	    target=generateTarget();
	    moveToTarget(time);//Mi muovo verso il target
	   }	 
     }
    else
	 {moveToTarget(time);} //Mi muovo verso il target  
   }
  //Incremento il tempo locale  
  localTime+=time.getDT();
 }//Fine think
 
 protected void moveToTarget(SimTime time) 
  {
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
        //System.out.println("Target="+target);
        //System.out.println("nextPoint="+nextPoint);
	    //System.out.println("Node position="+getPosition());
	    //System.out.println("distance="+finalDist);
	   }   
  }//Fine moving
 
 public Point2d generateTarget()
 {
	Point2d target=new Point2d((float)Math.random()*scenario.getWidth(),
			                   (float)Math.random()*scenario.getHeight());  
	return target;  
 }//Fine generate Target 
 
 }//Fine classe RandomWaypointNode
