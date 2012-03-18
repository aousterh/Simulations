package models.pursueModel;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import models.Model;
import models.Node;
import models.randomWaypointModel.RandomWaypointNode;


public class PursueModel extends Model
 { 
  private Node targetNode;
  
  private int numNodes;
  private float nodeRadius;
  private float vMin;
  private float vMax;
  private float antennaRadius;
  private float alpha;
  private float targetRadius;
  
  public PursueModel()
  {
   numNodes=10;
   nodeRadius=0.5f;
   vMin=0;
   vMax=5;
   antennaRadius=nodeRadius*10;
   alpha=0.5f;   
   targetRadius=0.5f;	  
   
   isPhysical=false;  
   isThinker=false;
  }//Fine costruttore
 
 public void setModel(int numNodes,float nodeRadius,float antennaRadius,
		              float alpha,float vMin,float vMax,
		              float targetRadius,boolean isPhysical)
  {
   this.numNodes=numNodes; this.nodeRadius=nodeRadius;
   this.antennaRadius=antennaRadius; this.alpha=alpha;
   this.vMax=vMax; this.vMin=vMin;
   this.isPhysical=isPhysical;
   this.targetRadius=targetRadius;
  }//Fine setModel
  
  
	
  public void setup(Scenario scenario, SimTime time) 
   {
	Vector localNodes=new Vector();
	
    //Creo il nodo centrale come un waypointNode
	targetNode=new RandomWaypointNode(new Point2d(15,10),targetRadius,scenario);
	targetNode.setColor(Color.BLACK);
	targetNode.setAntennaRadius(antennaRadius);
	
	
	//Nodi inseguitori
	for(int i=0;i<numNodes;i++)
	 {
	  float p=5+i;
	  PursueNode newNode=new PursueNode(new Point2d(p,p),nodeRadius,targetNode);
	  newNode.setColor(Color.MAGENTA);
	  newNode.setParams(antennaRadius,vMax,vMin,alpha);
	  localNodes.add(newNode);
	 }
	
	//Calcolo punto medio
	//Point2d middlePoint=middlePoint(localNodes);
	//System.out.println("Punto medio="+middlePoint);
	//targetNode.setPosition(middlePoint); 
    
    
	//Aggiugo i nodi "inseguitori"
	nodes.addAll(localNodes);
	
    //Aggiungo il target
	nodes.add(targetNode);
	
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

  public void think(SimTime time){}
  
 }//Fine PursueModel
