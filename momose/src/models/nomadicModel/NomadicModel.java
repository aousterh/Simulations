package models.nomadicModel;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import math.Vector2d;
import models.Model;
import models.Node;
import models.pursueModel.PursueNode;


public class NomadicModel extends Model
 {
  private int numNodes;
  private float nodeRadius;	
  private float vMax;
  private float vMin; 	
  private float pauseTime;
  private float alpha;
  private float antennaRadius;
  
  private Scenario scenario;
  private Point2d referencePoint;
  private boolean pause;
  private float localTime;
  private Point2d target;
  
    
  public NomadicModel()
   {
	setPhysicalProp(false);
	setThinkerProp(true);
	
	referencePoint=new Point2d();
	
    vMax=5;
    vMin=1;
    pauseTime=5;
    alpha=0.7f;
    
    pause=true;
    target=new Point2d();
    localTime=0;
    this.scenario=null;
   }//Fine costruttore
  
  public void setModel(int numNodes,float nodeRadius,float antennaRadius,
          float alpha,float vMin,float vMax,
          float pauseTime,boolean isPhysical)
   {
    this.numNodes=numNodes; this.nodeRadius=nodeRadius;
    this.antennaRadius=antennaRadius; this.alpha=alpha;
    this.vMax=vMax; this.vMin=vMin;
    this.isPhysical=isPhysical;
    this.pauseTime=pauseTime;
   }//Fine setModel
	
  public void setup(Scenario scenario, SimTime time) 
   {
    //Salvo un riferimento allo scenario   	
   	this.scenario=scenario;
   	 
   	for(int  i=0;i<numNodes;i++)
	 {
	  float p=5+i;
	  NomadicNode newNode=new NomadicNode(new Point2d(p,p),nodeRadius,referencePoint);
	  newNode.setColor(Color.PINK);
	  newNode.setParams(antennaRadius,vMax,vMin,alpha);
	  nodes.add(newNode);
	 }
	
	//Calcolo punto medio
	Point2d middlePoint=middlePoint(nodes);
	//System.out.println("Punto medio="+middlePoint);
	//Assegno punto medio al referencePoint
	referencePoint.set(middlePoint.x,middlePoint.y); 
  }//Fine setup
  
  private Point2d middlePoint(Vector localNodes)
   {
	float xMin=0,xMax=0,yMin=0,yMax=0;  
	
	Iterator  i=localNodes.iterator();
	if(i.hasNext())
	  {
       Node node=(Node)i.next();
       Point2d position=node.getPosition();
       xMin=xMax=position.x;
       yMin=yMax=position.y;
	  
	   while(i.hasNext())
	    {
	     node=(Node)i.next();
	     Point2d nextPos=node.getPosition();
	    
	     if(nextPos.x<xMin)
		   xMin=nextPos.x;  
	     if(nextPos.x>xMax)
		   xMax=nextPos.x; 
	     
	     if(nextPos.y<xMin)
		   yMin=nextPos.y;  
		 if(nextPos.y>yMax)
		   yMax=nextPos.y;
	    }		
	   }
	
	//Calcolo coordiante medie
	float middleX=(xMin+xMax)/2;
	float middleY=(yMin+yMax)/2;
	
	//Ritorno il punto medio
	return (new Point2d(middleX,middleY));  
   }//Fine middlePOint

 public void think(SimTime time)
  {
    //System.out.println("Arrivo qui...");	 
     if(pause==true)
      {
	  /* if(localTime<pauseTime)
	    {System.out.println("Sto fermo");}
	  else*/
	  if(localTime>pauseTime)	  
	   {
	    pause=false;
	    //Calcolo un nuovo target
	    target=generateTarget();
	    moveToTarget(time);//Mi muovo verso il target
	   }	 
     }
    else
	 {moveToTarget(time);} //Mi muovo verso il target  
   
  //Incremento il tempo locale  
  localTime+=time.getDT();  		
 }//Fine think
 
private void moveToTarget(SimTime time) 
  {
   //Creo un vettore verso il target
   Vector2d toTarget=new Vector2d(referencePoint,target);
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
   float distance=Point2d.distance(referencePoint,target); 
	 
   if(distance<=actV.getLength())
	  {referencePoint.set(referencePoint,toTarget);}  
	 else
	  {referencePoint.set(referencePoint,actV);} 
	
	float finalDist=Point2d.distance(referencePoint,target);
	//Se sono arrivato a destinazione mi fermo
	if(finalDist<=1)
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
   
}//Fine PursueModel