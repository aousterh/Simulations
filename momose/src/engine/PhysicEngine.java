package engine;

import java.util.Iterator;
import java.util.Vector;

import math.Point2d;
import math.Vector2d;
import models.Node;



public class PhysicEngine 
 {
  private Vector nodes;
  private Vector physicalObjs;
  private Scenario scenario;
 
  public PhysicEngine(Scenario scenario)
   {
  	nodes=new Vector();
  	physicalObjs=new Vector();
  	this.scenario=scenario;
   }//Fine PhysicEngine
 
  public void addPhysicalObjs(Vector newPhysicalObjs)
   {physicalObjs.addAll(newPhysicalObjs);}
 
  public void addNodes(Vector newNodes)
  {nodes.addAll(newNodes);}
  
  public int getNumNodes()
   {return nodes.size();}
  
  public int getNumPhysObjs()
   {return physicalObjs.size();}
  
  public void setBorder(Scenario scenario)
   {
	if(scenario.getBorderType()==Scenario.BOUNDED)
	{
	 //Aggiungo  i quattro muri esterni
	 //Superiore	
	 physicalObjs.add(new ScenRectangle(new Point2d(0,-2),
			                            scenario.getWidth()+2,2));
     //destro	
	 physicalObjs.add(new ScenRectangle(new Point2d(scenario.getWidth(),0),
			                            2,scenario.getHeight()+2));
	 //inferiore	
	 physicalObjs.add(new ScenRectangle(new Point2d(-2,scenario.getHeight()),
			                            scenario.getWidth()+2,2));
	 //sinistra
	 physicalObjs.add(new ScenRectangle(new Point2d(-2,-2),
			                            2,scenario.getHeight()+2));
	}
	//Mettere qui codice per gestire i casi boundless e boundlessRefl
   }//Fine setBorder
 
 public void animatePhysic()
  {
   //Ciclo che scorre i nodi per calcolare le collisioni
   Iterator i=nodes.iterator();
   while(i.hasNext())
    {
	 //Prelevo il prossimo nodo nodi    
	 Node node=(Node)i.next();
	 
	 ////Calcolo le collisioni/////
	 boolean collision=collisionDetection(node);
	 ////////////
	 node.setCollision(collision);
	 
     //Controllo la collisione con il bordo (sostituire)	
	 if(scenario.getBorderType()==Scenario.BOUNDED)
	  borderCollision(node);
   }   
  }//Fine animatePhysic
 
 public boolean collisionDetection(Node node)
  {
   //Creo le variabili per l'intersezione
   float t=Float.POSITIVE_INFINITY;
   
   //Testo gli oggetti fisici
   Iterator j=physicalObjs.iterator();
   while(j.hasNext())
	{
	 //Prelevo il prossimo oggetti fisico
	 PhysicalObject physicalObj=(PhysicalObject)j.next();
	 //Salta se stesso
	 if(node!=physicalObj)
	 {
	  //Calcolo l'intersezione
	  float pt=physicalObj.intersect(node);
	  //System.out.println("pt="+pt);
	  
	  //Se il punto d'intersezione attuale 
	  //è più vicino di quello globale faccio l'aggiornamento
	  if(((pt>=0)&&(pt<=1))&&(pt<t))
		    t=pt;
	 } 	
	}
	 
  //System.out.println("t="+t);
  
  //Controllo il valore di t per determinare se c'e' stata una collisione
  if((t>=0)&&(t<=1))
    {
	 //C'è stata collisione
     //Torno un poco indietro (Dell'unità minima di spostamento)
     float minVal=0.01f;
	 if(t>minVal)
	   t-=minVal; 
	  else
	   t=0;
	 
	 //Calcolo il nuovo vettore velocita'  
	 Vector2d v=node.getVelocity();
	 v.dotMul(t);
	 //imposto la nuova posizione del nodo
	 node.setPosition(v);
	 
	 //Ritorno la collisione
	 return true;
    }
  else//Non c'e' sata collisione
    { 
	 node.setPosition(node.getVelocity());//Non collide
	 return false;
    }	 
}//Fine collisionDetection
	
 public void borderCollision(Node node)
  {
   Point2d swapPos=node.getPosition();	 
   
   if(node.getPosition().x<=node.getRadius())
    { swapPos.x=swapPos.x+0.001f; }  
	   
   if(node.getPosition().y<=node.getRadius())
    { swapPos.y=swapPos.y+0.001f; }
	
   //Aggiorno la posizione del nodo
   node.setPosition(swapPos);
  }//Fine borderCollision
}//Fine classe PhysicEngine


/*
public void animatePhysic()
  {
   //Ciclo che scorre i nodi per calcolare le collisioni
   Iterator i=nodes.iterator();
   while(i.hasNext())
    {
	 //Prelevo il prossimo nodo nodi    
	 Node node=(Node)i.next();
	 
	 //Controllo se il nodo va fuori dai confini dello scenario
	 if(borderCollision(node)==false)
	  {	
	   node.setCollision(false);
	   
	   ////Calcolo le collisioni/////
	   boolean collision=collisionDetection(node);
	   ////////////
	   
	   node.setCollision(collision);
	  }
	 else
	  {node.setCollision(true);}	 
	}   
  }//Fine animatePhysic
 
 public boolean collisionDetection(Node node)
  {
   //Creo le variabili per l'intersezione
   float t=Float.POSITIVE_INFINITY;
	 
   //Testo gli oggetti fisici
   Iterator j=physicalObjs.iterator();
   while(j.hasNext())
	{
	 //Prelevo il prossimo oggetti fisico
	 PhysicalObject physicalObj=(PhysicalObject)j.next();
	 //Salta se stesso
	 if(node!=physicalObj)
	 {
	  //Calcolo l'intersezione
	  float pt=physicalObj.intersect(node);
		 		
	  //Se il punto d'intersezione attuale 
	  //è più vicino di quello globale faccio l'aggiornamento
	  	if(((pt>0)&&(pt<=1))&&(pt<t))
		    t=pt;
	 } 	
	}
	 
  //System.out.println("t="+t);
  //Controllo il valore di t per determinare se c'e' stata una collisione
  if((t<0)||(t>1))
    { 
	node.setPosition(node.getVelocity());//Non collide
	return false;
   }	 
  else
   {//C'è stata collisione
	//Calcolo il nuovo vettore velocita'  
	Vector2d v=node.getVelocity();
	v.dotMul(t);
	//imposto la nuova posizione del nodo
	node.setPosition(v);
	return true;
   }  	 
 }//Fine collisionDetection
 */
