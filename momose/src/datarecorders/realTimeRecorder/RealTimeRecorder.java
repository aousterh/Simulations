package datarecorders.realTimeRecorder;



import java.util.Iterator;
import java.util.Vector;

import viewer.RealTimeViewer;

import datarecorders.DataRecorder;

import engine.Scenario;
import engine.SimTime;
import models.Model;


public class RealTimeRecorder extends DataRecorder
 {
  private RealTimeViewer rtViewer;
  private Vector nodes;
  
  private float startInterval;
  private float endInterval;
	
  public RealTimeRecorder(RealTimeViewer rtViewer)
   {
	this.rtViewer=rtViewer;
	nodes=new Vector();
	startInterval=endInterval=0;
   }//Fine costruttore
  
  public void reset() 
   {nodes.clear();}

  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
	//Prelevo i nodi dal modelli e li mette nel vettore dei nodi  
	Iterator i=models.iterator();
	while(i.hasNext())
	 {
	  Model model=(Model)i.next();
	  //Registro i dati;
	  nodes.addAll(model.getNodes());
	 }	 
	
	//Passo il riferimento ai nodi all'arena
	rtViewer.getSimArena().setNodes(nodes);
   }//Fine setup 
  
 @Override
 public void record(SimTime time) 
  {
   if(rtViewer.isOpen())
   {  
    //System.out.println("RealTimeVisualizer: Registro i dati...");
    rtViewer.getSimArena().updateNodes(nodes);
   
    if(rtViewer.isRealTime())
     { 	
      //Fermo il tempo
 	  sleep(time);
     } 
   }	
  }//Fine record

 public void close(){}

 public void sleep(SimTime time)
  {
   //System.out.println("startInterval="+startInterval);
   float sleepTime;	 
   if(startInterval!=0)
	 {   
	  endInterval=System.currentTimeMillis();
	  //System.out.println("endInterval="+endInterval);
	  
	  float workInterval=(endInterval-startInterval)/1000;
	    
	  //Il tempo viene calcolato sottraendo lo step al tempo di lavoro del programma
	  // e frazionandolo in base alla velocità di riproduzione
	  sleepTime=(time.getDT()-workInterval);
	  //System.out.println("workInterval="+workInterval+" time.dt="+time.getDT());
	 }
	else
	 sleepTime=time.getDT();   
	     
    if(sleepTime>0)	
	 {
	  //Fermo il tempo per periodo calcolato
	  try 
	   {Thread.sleep((long)((sleepTime*1000)));} 
	  catch (InterruptedException e) 
	   {System.out.println("Simulation interrupt by user!");}
	 } 
	  
   //Prendo il temo di partenza per il periodo successivo
   startInterval=System.currentTimeMillis();
  }//Fine sleep

}//Fine RealTimeVisualizer
