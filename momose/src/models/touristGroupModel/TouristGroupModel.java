package models.touristGroupModel;

import java.awt.Color;

import java.util.Vector;

import engine.Connection;
import engine.HotSpot;
import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import models.Model;
import models.Node;




public class TouristGroupModel extends Model
 { 
  private int numNodes;
  private float nodeRadius;
  private float vMin;
  private float vMax;
  private float antennaRadius;
  private float alpha;
  private float targetRadius;
  private HotSpot startTarget;
  private HotSpot lastSpot;
  
  
  
  public TouristGroupModel()
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
	
	
	//Nodi inseguitori
	 int countGroup=50; 
     int numGroups=numNodes/countGroup;
    
    if(numGroups==0)
    	numGroups=1;
    
    Vector group;
    //Creo i gruppi di turisti
	for(int i=0;i<numGroups;i++)
	 {		
	  group=createGroup(scenario,countGroup);
	  nodes.addAll(group);
	 } 
	
  }//Fine setup
  
 public Vector createGroup(Scenario scenario, int countGroup)
  {
   Vector group=new Vector();	 
	 
   startTarget=null;  
   lastSpot=null;  
   Point2d startPoint=calcStartPoint(scenario);   
	 
   //Creo la guida
   Node targetNode=createGuide(scenario,startPoint);
   
   //Creo i turisti
   for(int i=0;i<countGroup-1;i++)
	 {
      TouristGroupNode newNode=new TouristGroupNode(new Point2d(startPoint.x,startPoint.y),nodeRadius,targetNode);
	  newNode.setColor(Color.PINK);
	  newNode.setParams(generateAntennaRadius(),vMax,vMin,alpha);
	  
	  
	  
	  //Raggio antenna statico
	  //newNode.setAntennaRadius(generateAntennaRadius());
	  newNode.setAntennaRadius(antennaRadius);
	  
		 
		// Raggio antenna dinamico
		/* float rad=generateAntennaRadius();
		 newNode.onAntenna=rad;
		 newNode.setAntennaRadius(0);*/
	  
	  
      //Aggiugo i nodi "inseguitori"
	  group.add(newNode);
	 }
   
    
   
   
    // Aggiungo il target
	group.add(targetNode);

   return group;	 
  }//Fine createGroup
  
 public Node createGuide(Scenario scenario,Point2d startPoint)
  {
    //Creo il nodo centrale come un waypointNode
	TouristGuideNode  targetNode=new TouristGuideNode(new Point2d(startPoint.x,startPoint.y),targetRadius,scenario.getHotSpots());
	targetNode.setColor(Color.MAGENTA);
	targetNode.setParams(antennaRadius,vMax-1,vMin);
	targetNode.setTargetNode(startTarget);
	targetNode.setlastTarget(lastSpot);
	targetNode.setPauseTime(30);  
	return targetNode;
   }//Fine createGuide
 
 private float generateAntennaRadius()
  {
   if(Math.random()<0.5)
	  return 10;  
   if(Math.random()<0.9)
	  return 20;  
   return 100;
  }//Fine generateAntenna Radius

 private Point2d calcStartPoint(Scenario scenario) 
  {
   Vector hotSpots=scenario.getHotSpots();	 
   if(hotSpots.size()>0)
    { 
	 //Estraggo un'hotspot  
	 int indexSpot=(int)(Math.random()*hotSpots.size()); 
	 HotSpot fSpot=(HotSpot)hotSpots.elementAt(indexSpot);
	 
	 //Estraggo la connessione
	 Vector connections=(Vector)fSpot.getConnections();
	 if(connections.size()>0)
	  {
	   int indexConn=(int)(Math.random()*connections.size());
	   Connection conn=(Connection)connections.elementAt(indexConn);
	   HotSpot sSpot=conn.getEndSpot();
	   
	   //Estraggo il parametro alpha
	   float alpha=(float)Math.random();
	   
	   //Definisco il target iniziale
	   if(Math.random()<0.5)
	   {startTarget=fSpot; lastSpot=sSpot;}
	    
	   else
		{startTarget=sSpot; lastSpot=fSpot;}   
	   
	   //Creo il punto e lo restituisco
	   return new Point2d((1-alpha)*fSpot.getPosition().x+alpha*sSpot.getPosition().x,
			              (1-alpha)*fSpot.getPosition().y+alpha*sSpot.getPosition().y);
	  }	 
	 else
	  {
	   startTarget=fSpot;	 
	   return fSpot.getPosition();
	  }
    }  
   
   return new Point2d(scenario.getWidth()/2,scenario.getHeight()/2);
  }//Fine calcStartPoint

public void think(SimTime time){}
  
 }//Fine PursueModel
