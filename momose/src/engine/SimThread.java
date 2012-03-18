package engine;

import gui.SimulationWnd;

import java.util.Iterator;
import java.util.Vector;

import datarecorders.DataRecorder;

import models.Model;

public class SimThread extends Thread
{
 //Componenti del simulatore
 private AiManager aiManager;	
 private PhysicEngine physicEngine;
 
 //Dati della simulazione	
 private SimTime time;
 private Vector models;
 private Vector recorders;
 private Scenario scenario;
 private SimulationWnd simWnd;
 
 //Variabili per il controllo della simulazione
 private boolean state;	
 private boolean lock;
 
 
 public SimThread(Scenario scenario,Vector models,Vector recorders,
		          SimTime time,SimulationWnd simWnd)
  {
   //Imposto i componenti della simulazione
   aiManager=new AiManager();
   physicEngine=new PhysicEngine(scenario);
   
   //Imposto i dati della simulazione
   this.scenario=scenario;
   this.time=time;
   this.simWnd=simWnd;
   this.models=models;
   this.recorders=recorders;
  
   //imposto le variabili di controllo
   state=true;
   lock=false;
  }//Fine costruttore
 
 public void lock()
 { lock=true; }

 public boolean getLock()
  { return lock; }
 
 
 private void setup()
  {
   simWnd.appendToLog("Setup started...");	 
   
   simWnd.appendToLog(" Models setup...");
   setupModels();
   
   simWnd.appendToLog(" AI Manager setup...");
   setupAI();
   
   simWnd.appendToLog(" Physic Engine setup...");
   setupPhysic();
   
   simWnd.appendToLog(" Data-Recorders setup...");
   setupRecorders();
   
   simWnd.appendToLog("Setup ended...");
   simWnd.appendToLog(" ");
   
   //Stampo le info gnerali
   printGeneralInfo();
  }//Fine setup
 
 private void printGeneralInfo()
  {
   simWnd.appendToLog("Summarising information:");
   //Info sullo scenario
   printScenarioInfo();
   //Info sul tempo
   printSimTimeInfo();
   simWnd.appendToLog(" Number of thinkers in AI Manager: "+
		              aiManager.getNumThinkers());
   simWnd.appendToLog(" Number of models thinkers in AI Manager: "+
                      aiManager.getNumModThinkers());
   simWnd.appendToLog(" Number of nodes in Physic engine: "+
		              physicEngine.getNumNodes());
   simWnd.appendToLog(" Number of physical objects in Physic engine: "+
		              physicEngine.getNumPhysObjs());
   simWnd.appendToLog(" ");
  }//Fine printGeneralInfo
 
 private void printScenarioInfo()
  {
   simWnd.appendToLog(" Scenario info:");	 
   simWnd.appendToLog("  Dimension: "+scenario.getWidth()+" x "+scenario.getHeight());
   
   String borderType=null;
   if(scenario.getBorderType()==Scenario.BOUNDED)
	borderType="bounded";
   else
	borderType="boundless";   
   simWnd.appendToLog("  BorderType: "+borderType);
   
   simWnd.appendToLog("  Number of buildings: "+scenario.getBuildings().size());
   simWnd.appendToLog("  Number of Hot-spot: "+scenario.getHotSpots().size());
  }//Fine printScnenarioInfo
 
 private void printSimTimeInfo()
  {
   simWnd.appendToLog(" Simulation time info:"); 	 
   simWnd.appendToLog("  Simulation duration (s): "+time.getT());	 
   simWnd.appendToLog("  Step time (s): "+time.getDT());
   simWnd.appendToLog("  Number of interations: "+time.getN());
  }//Fine printTimeInfo
 
 
 private void setupModels() 
  {
   Iterator i=models.iterator();
   while(i.hasNext())
	{
	 Model model=(Model)i.next();
     //Stampo le info
	 simWnd.appendToLog("  setup "+model.getName()+"...");
	 //Genero i nodi del modello;
	 model.setup(scenario,time);
	}	
  }//Fine setupModels
 
 private void setupAI() 
  {
   Vector nodes=null;	 
   Iterator i=models.iterator();
   while(i.hasNext())
	{
	 Model model=(Model)i.next();
	 //Aggiungo i ThinkerObject al manager dell'AI;
	 nodes=model.getNodes();
	 aiManager.addThinkers(nodes);
	 //Stampo le info
	 simWnd.appendToLog("  "+model.getName()+" add "+nodes.size()+" to AI manager");
	 
	 //Se il modello e' "Pensante" lo carico nel manager dell'IA
	 if(model.isThinker())
	 {	 
	  aiManager.addThinkerModel(model);
	  //Stampo le info
	  simWnd.appendToLog("  "+model.getName()+" is Thinker, added to AI Manager");
	 } 
	}	   	
   }//Fine setupAI
 
 private void setupPhysic() 
  {
   Vector nodes=null;	 
   Iterator i=models.iterator();
   while(i.hasNext())
	{
	 Model model=(Model)i.next();
	 //Aggiungo i nodi da muovere al motore fisico;
	 nodes=model.getNodes();
	 physicEngine.addNodes(nodes);
     //Stampo le info
	 simWnd.appendToLog("  "+model.getName()+" add "+nodes.size()+" to physic engine");
	 
	 //Controllo se i nodi sono anche oggeti fisici
	 //Se si, li aggiungo alla lista degli oggetti fisici
	 if(model.isPhysical()==true)
	  {	 
	   physicEngine.addPhysicalObjs(model.getNodes());
       //Stampo le info
	   simWnd.appendToLog("  "+model.getName()+" is Physical, added nodes to Physical objects list"); 
	  }  
    }
  
  //Aggiungo i physicaObject dello scenario
  physicEngine.addPhysicalObjs(scenario.getBuildings());
  
  //Setto il tipo di bordo
  physicEngine.setBorder(scenario);
  
 }//Fine setupPhysic

 private void setupRecorders()
  {
   Iterator i=recorders.iterator();
   while(i.hasNext())
	{
	 DataRecorder recorder=(DataRecorder)i.next();
	 //Stampo le info
	 simWnd.appendToLog("  setup "+recorder.getName()+"...");
	 //Registro i dati;
	 recorder.setup(models,scenario,time);
	}	  
  }//Fine setupRecorders


 private void simulationCicle()
  {
   simWnd.appendToLog("Simulation cicle started...");
   simWnd.startSimCicle();
   
 ///  
   //Ciclo di simulazione
   while((time.getTime()<=time.getT())&&(state==true)) 
    {
	 //Stampo le info sul tempo
	 printSimCicleInfo();
	
     //Raccolta dati
	 record();
	
	 if(time.getTime()<time.getT())
	  {	
	   //Calcolo AiManager
	   aiManager.animateAI(time);
	   //Calcolo PhysicEngine
	   physicEngine.animatePhysic();
	  }
	
    //Incremento del tempo  
	time.upTime();
	
    //Blocco il trhead se premuto pause
	ctrlLock();
	
   }//Fine ciclo di simulazione		 
 
   //Rimetto a posto il tempo (Togliere se possibile)
   time.downTime();
   //Reimposto i dati visulizzati in finestra
   simWnd.upTimeLabels(time.getTime(),time.getLoop());
 ///  
   simWnd.setProgressValue(time.getTime());
   simWnd.appendToLog("Simulation cicle ended...");
   simWnd.appendToLog(" ");
   //Invio "evento" di fine ciclo
   simWnd.endSimCicle();
  }//Fine simulationCicle

 private void printSimCicleInfo()
  {
   simWnd.upTimeLabels(time.getTime(),time.getLoop());
   simWnd.appendToLog("Iteration #"+time.getLoop()+": time="+time.getTime());
   simWnd.setProgressValue(time.getTime());
  }//Fine printSimInfo
 
 
 
 public void record()
  {
   Iterator i=recorders.iterator();
   while(i.hasNext())
    {
     DataRecorder recorder=(DataRecorder)i.next();
     //Registro i dati;
     recorder.record(time);
     //Stampo le info
     simWnd.appendToLog(recorder.getName()+" recording...");
    }	  
  }//Fine record
 
 
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

 
 public void run() 
  {
   //Fase di setup	 
   setup();
   //Ciclo di simulazione
   simulationCicle(); 	 
   //Fine simulazione
   endSimulation(); 
  }//Fine run
 
 private void endSimulation()
  {
   //Comunico l'iizio della fase di chisura simulazione	 
   simWnd.appendToLog("End simulation...");
   simWnd.startSimClose();
   
   
   Iterator i=recorders.iterator();
   while(i.hasNext())
	{
	 DataRecorder recorder=(DataRecorder)i.next();
	 //Registro i dati;
	 recorder.close();
	 //Stampo le info
	 simWnd.appendToLog(recorder.getName()+" ended...");
	}	    	 
   
   //Comunico fine simulazione
   simWnd.appendToLog("END SIMULATION!!!");
   simWnd.simEnd();
  }//Fine endSimulation
 
  public void ferma() 
   {state=false;}
}//Fine Simulation