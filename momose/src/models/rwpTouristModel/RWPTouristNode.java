package models.rwpTouristModel;


import java.util.Vector;

import engine.HotSpot;
import engine.Scenario;
import engine.SimTime;


import math.Point2d;
import math.Ray2d;
import math.Vector2d;

import models.randomWaypointModel.RandomWaypointNode;


public class RWPTouristNode extends RandomWaypointNode
 {
  private float var;
  private int maxTrial;
  private Vector notVisited;
  
  public float onAntenna=0;
 
  public RWPTouristNode(Point2d pos, float radius,Scenario scenario) 
   {
    super(pos,radius,scenario);
    
    var=15;
    maxTrial=20;
    notVisited=new Vector();
   }//Fine costrutore
 
 public Point2d generateTarget()
  {
   Point2d lTarget=null;
   
   //Cerco di muovermi verso un hotSpot
   lTarget=moveToHotSpot();	 
   
   //Se non ci posso adare direttamente cerco un target a caso 
   if(lTarget==null)
    lTarget=moveToPoint();
     	 
   //Se collido anche con il target a caso mi fermo per un ciclo
   if(lTarget==null)
    lTarget=new Point2d(pos.x,pos.y);
   
   return lTarget;  
  }//Fine generate Target 
 
private Point2d moveToPoint() 
 {
  int i=0;
  Point2d lTarget=new Point2d();
  
  while(i<maxTrial)
   {
	//Genero il nuovo punto
	lTarget.x=pos.x+(float)((var*Math.random())*(Math.random()<0.5?(-1):(1)));
	lTarget.y=pos.y+(float)((var*Math.random())*(Math.random()<0.5?(-1):(1)));
	
	//Controllo se c'è qualche intersezione 
	if(validateTarget(lTarget)==true)  
	 {return lTarget;} 
	
	i++;
   }  
  
  return null;
 }//Fine moveToPoint 

private Point2d moveToHotSpot() 
 {
  HotSpot selSpot=null;
  
  int i=0;
  int index=0;
  
  //Se ho visitato tutti gli hotspot li rimetto tutti come non visitati
  if(notVisited.size()==0)
	 notVisited=(Vector)scenario.getHotSpots().clone();
  
  if(notVisited.size()==0)
	return null; 
   else
    {
	 //Estraggo un hotSpot	 
	 index=(int)(Math.random()*notVisited.size());
	 selSpot=(HotSpot)notVisited.elementAt(index);
	   
	 //Controllo se c'è qualche intersezione
	 if(validateTarget(selSpot.getPosition())==true)
	   {
		HotSpot selSpot2=selSpot; 
		notVisited.remove(selSpot); 
		return selSpot2.getPosition();
	   } 
	   
	   i++;
	  }
  
  return null;
 }//Fine moveToHotSpot 


private boolean validateTarget(Point2d target)
 {
  //Controllo i bounds
  if((target.x<1)||(target.x>scenario.getWidth()-1)||
     (target.y<1)||(target.y>scenario.getHeight()-1))
	  return false;
	
  Ray2d ltoTarget=new Ray2d(getPosition(),new Vector2d(getPosition(),target));
  
  return (!scenario.isCollided(ltoTarget));
 }//Fine ctrlIntersection


public void think(SimTime time) 
{
 //genAntennaOn(time);	 
	 
 //Se ho sbattuto contro un muro  calcolo nuva direzione	 
 if(collided==true)
  {
 	//pause=true;
 	//Calcolo il nuovo vettore velocità	
    /*v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
	  v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
    
	  //Rapporto la velocità all'intervallo di tempo 
	  v.x=v.x*time.getDT();
	  v.y=v.y*time.getDT();*/
	 
	 target=generateTarget();
	 moveToTarget(time);//Mi muovo verso il target 
	 
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

private void genAntennaOn(SimTime time)
 {
	float x=(float)((time.getTime()/time.getT())*(Math.PI));
	float est=(float)Math.random();
	
	if((est<=Math.sin(x))&&(Math.cos(x)>=0))
	 {setAntennaRadius(onAntenna);}
	
	if((est>Math.sin(x)*3)&&(Math.cos(x)<0))
	 {setAntennaRadius(0);}	
	 
}//Fine genAntennaOn 

}//Fine classe RandomWaypointNode
