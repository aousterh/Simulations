package datarecorders.debugRecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import datarecorders.DataRecorder;

import engine.Scenario;
import engine.SimTime;

import models.Model;
import models.Node;

public class DebugRecorder extends DataRecorder
 {
  private Vector recNodes;
  PrintStream ps;
  FileOutputStream fos;
  int nsave;
  
  public DebugRecorder()
  {
   recNodes=new Vector(); 	  
   ps=null;	  
   fos=null;
   nsave=0;
  }//Fine costruttore
	  	
  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
    //Creo gli oggetti RecNode
	setupNodes(models); 
	
	//Creo il file dove registrare i dati
    ps=null;
	File outXmlFile=null;
	fos=null;
	try {
         outXmlFile= new File("."+File.separator+"debug_recorder_output.xml");
	     fos=new FileOutputStream(outXmlFile);
	     ps=new PrintStream(fos);
	     ps.println("Inizio scrittura in debug_out.txt");
	     ps.println("");
	    } 
	    catch (IOException e) 
	       { e.printStackTrace(); }
   }//Fine setup 

 private void setupNodes(Vector models)
  {
   Iterator i=models.iterator();
	while(i.hasNext())
	 {
	  //Estraggo i nodi dal modello	
	  Model nextModel=(Model)i.next();
	  recNodes=nextModel.getNodes();
	 }	
	//System.out.println("Fine setup, "+recNodes.size()+" RecNode creati");
  }//Fine setupNodes  
  
  public void record(SimTime time) 
  {
   Iterator i=recNodes.iterator();
   int cont=1;
   ps.println("Salvataggio #"+(nsave+1));
    //if(i.hasNext())
	while(i.hasNext())
	 {
	  //Estraggo il prossimo nodo	
	  Node nextNode=(Node)i.next();
	  ps.println(cont+") "+time.getTime()+" "+nextNode.getPosition().toString());
	  cont++;
	 }		
   ps.println("");
   nsave++;
  }//Fine record
  
  public void close() 
   {
	//System.out.println("DebugRecorder: Fine simulazione...");   
	 //Chiudo il file
	  try{fos.close();} 
	    catch (IOException e) {e.printStackTrace();} 
   }//Fine close

 }//Fine DebugRecorder
