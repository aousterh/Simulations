package models.ecraModel;

import java.awt.Color;



import math.Point2d;
import math.Vector2d;
import models.Node;

import engine.Scenario;
import engine.SimTime;



public class EcraNode extends Node 
 { 
  private int type;
  
  private float vMax;
  private float vMin;
  private float localTime;
  
  private Point2d refPoint;
  private float targetAttr;
  private int countType[];
  
  
  public EcraNode(Point2d pos, float radius,Scenario scenario,float vMin,float vMax,float alpha) 
   {
    super(pos,radius);
	type=1;
    this.vMax=vMax;//Velocità massima
    this.vMin=vMin;//velocità minima
    localTime=0;
    this.refPoint=new Point2d(scenario.getWidth()/2,scenario.getHeight()/2);
	this.targetAttr=alpha;//Peso del vettore verso il target
	countType=new int[3];
	countType[0]=countType[1]=countType[2]=0;
   }//Fine costruttore
  
 public int[] getCountTypes()
  {return countType;}
 
 public int getType()
  {return type;}
	 
  
 public void setType(int newType,Point2d newRefPoint)
  {
   if(newType==1)
    {type=1; refPoint=newRefPoint; setColor(Color.MAGENTA);}
   else
    if(newType==2)
     {type=2; refPoint=newRefPoint; setColor(Color.RED);}
     else
      {type=3; refPoint=newRefPoint; setColor(Color.BLUE);} 
   }//Fine setType 

 public void think(SimTime time) 
  {
   pursueHideMove(time);
   //Incremento il tempo locale  
   localTime+=time.getDT();	
   //Incremento il contatore di tipo
   countType[type-1]++;
  }//Fine think
 
 
 private void pursueHideMove(SimTime time)
  {
   if(collided==true)
	{resolveCollision(time);}  
   else
	{	
	 //Imposto una velocità
     float velocity=(float)(Math.random()*vMax+vMin);
     //Vettore verso target
     Vector2d toTarget=new Vector2d(this.getPosition(),refPoint);
     toTarget.normalize();
     //Moltiplico per velocità e "peso"
     toTarget.dotMul(velocity);
       
    //Vettore casuale 
    Vector2d randomVector=randomUnitVector();
    randomVector.dotMul(velocity);
    
    //Per calcolare la nuova velocità faccio 
    //una combinazione lineare dei due vettori
    toTarget.dotMul(targetAttr);
    randomVector.dotMul(1-targetAttr);
    //Calcolo il nuovo vettore velocità	
    v=Vector2d.sum(toTarget,randomVector);
	
     
	//Rapporto la velocità all'intervallo di tempo 
	v.x=v.x*time.getDT();
	v.y=v.y*time.getDT();
   } 	 
  }//Fine pursueHide
 
 private Vector2d randomUnitVector()
 {
  Vector2d randomVect=new Vector2d();
  randomVect.x=(float)(Math.random()*(Math.random()<0.5?(-1):(1)));
  randomVect.y=(float)(Math.random()*(Math.random()<0.5?(-1):(1)));
  return randomVect;
 }//Fine randomVector
 
private void resolveCollision(SimTime time)
 {
  //Calcolo il nuovo vettore velocità	
  v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
  v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
	     
  //Rapporto la velocità all'intervallo di tempo 
  v.x=v.x*time.getDT();
  v.y=v.y*time.getDT();  	 
 }//Fine resolveCollision
 
}//Fine classe EraNode
