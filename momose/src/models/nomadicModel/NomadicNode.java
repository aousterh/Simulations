package models.nomadicModel;
import engine.SimTime;
import math.Point2d;
import math.Vector2d;
import models.Node;

public class NomadicNode extends Node
 {
  protected Point2d refPoint;
  
  protected float vMax;
  protected float vMin;
  protected float alpha;
  
  public NomadicNode(Point2d pos, float radius,Point2d referencePoint) 
   {
	super(pos, radius);
	//Salvo un riferimento al nodo di riferimento
	this.refPoint=referencePoint;
	
	vMax=5;
	vMin=1;
	alpha=0.7f;
   }//Fine costruttore 
  
  public void setParams(float antennaRadius,float vMax,float vMin,float alpha)
   {
	setAntennaRadius(antennaRadius);  
	this.vMin=vMin;
    this.vMax=vMax;
    this.alpha=alpha;
   }//Fine setParams

  public void think(SimTime time) 
   {
    //Se ho sbattuto contro un muro  calcolo nuova direzione	 
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
      toTarget.dotMul(alpha);
      randomVector.dotMul(1-alpha);
      //Calcolo il nuovo vettore velocità	
      v=Vector2d.sum(toTarget,randomVector);
	
     
	  //Rapporto la velocità all'intervallo di tempo 
	  v.x=v.x*time.getDT();
	  v.y=v.y*time.getDT();
	 } 
   }//Fine think

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
}//Fine classe PursueNode