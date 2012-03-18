package viewer;

import java.util.Iterator;
import java.util.Vector;

public class SimVisualization extends Thread 
 {
  private boolean loopState;	
  private ViewTime time; 
  private Vector nodes;
  private boolean lock;
  private int velocity;
  private Player player;
  private SimArena simArena;
  private int timeInfoSize;
  
  
  private float startInterval;
  private float endInterval;
  
  public SimVisualization(ViewTime time,Vector nodes,Player player)
   {
	this.time=time;
	
	this.nodes=nodes;
	if(nodes.size()>0)
	 timeInfoSize=((ViewNode)nodes.elementAt(0)).getTimeInfoSize();
	else
	 timeInfoSize=1;	
	 		
	this.player=player;
	loopState=true;
	this.lock=true;
	this.velocity=1;
	
	//Setto l'arena
	simArena=player.getArena();
	simArena.setNodes(nodes);
	
	startInterval=endInterval=0;
   }//Fine costruttore
  
  public void setLoopState(boolean loopState)
   { this.loopState=loopState; }
  
  public boolean getLoopState()
   { return loopState; }
  
  public void  setVelocity(int velocity)
   {this.velocity=velocity;}
  
  public int  getVelocity()
   {return velocity;}
  
  public void lock()
   { lock=true; }
  
  public boolean getLock()
   { return lock; }
  
  public void setInterpolation(boolean flag)
   {
	if(flag==true)
	 time.enableBaseTime();
   }//Fine setInterpolatingTime
  
  public void run() 
   {
	//System.out.println("Thread di visualizzazione iniziato...");
	//Ciclo di simulazione
	viewLoop();
	//System.out.println("Fine thread di visualizzazione!!!"); 
   }//Fine run

 private void viewLoop() 
  {
   //Calcolo posizione dei nodi inziale
   updatePositions();
   //Blocco il trhead all'inizio
   ctrlLock();
	 
   while(loopState==true)
    {
	 //System.out.println("Ciclo di visualizzazione...");
     //Calcolo posizione dei nodi e la setto
	 updatePositions();
	 
	 //Fermo il tempo per il tempo giusto
	 sleep();
	 
     //Blocco il trhead se premuto false
	 ctrlLock();
	 
	 //Incremento il tempo
	 time.upTime(velocity);
     	 
     //Stampo le info sul tempo
	 player.upTimeLabels(time.getTime(),time.getLoop());   
     //Setto lo slider
     player.setTimeSlider(time.getTime());
   }  
  }//Fine viewCicle 
 
 public void updatePositions() 
  {
   //Calcolo l'indice del vettore delle posizione che corrisponde all'istante di tempo attuale
   float fIndex=calcFloatIndex(time,timeInfoSize); 	 
   int iIndex=(int)fIndex;		 
	 
   //Aggiorno le posizioni dei nodi
   Iterator i=nodes.iterator();
   while(i.hasNext())
    {
     ViewNode nextNode=(ViewNode)i.next();
	 nextNode.setActPosition(fIndex,iIndex);  
    }   
    
    //Faccio l'update della vista
    simArena.reDraw();
  }//Fine calcNodesPos
 
private float calcFloatIndex(ViewTime time,int timeInfoSize)
 {
  float alpha=time.getTime()/time.getT();
  float fIndex=(alpha*(timeInfoSize-1));
  return fIndex;  
 }//Fine calcFloatIndex   

public void ctrlLock()
 {
  if(lock==true)
   {
	synchronized (this) 
	 {
	  //System.out.println("Locckato!!!");
	  try {wait();} 
	   catch(InterruptedException e) 
	     {e.printStackTrace();}
	 }
	lock=false;
   }  
 }//Fine ctrlLock
 
 public void sleep()
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
     sleepTime=(time.getDT()-workInterval)/Math.abs(velocity);
     //System.out.println("workInterval="+workInterval+" time.dt="+time.getDT());
    }
   else
	sleepTime=time.getDT()/Math.abs(velocity);   
     
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
 }//Fine SimVisualization
