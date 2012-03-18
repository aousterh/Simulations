package datarecorders.btgRecorder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import datarecorders.DataRecorder;

import models.Model;
import models.Node;


import engine.Scenario;
import engine.SimTime;

public class BTGRecorder extends DataRecorder
 {
  private BTGraph btGraph;
  private String outputFilePath;
  
  private PrintStream ps;
  private File outputFile;
  private FileOutputStream fos;
 
  public BTGRecorder()
   {
    btGraph=new BTGraph();	 
    ps=null;
	outputFile=null;
	fos=null;
 	outputFilePath="."+File.separator+"BTGRecorderOutput.txt";
   }//Fine costruttori
  
  public void setOutputFilePath(String outputFileName)
   { this.outputFilePath=outputFileName; }
  
  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
	//Prendo i riferimenti dei nodi
	createBTGraph(models);
	//Creo il file di output su cui salvare i dati
	createOutputFile();
	
	//Scrivo le info 
	ps.println("Simulation time="+time.getT());
	ps.println("Simulation step="+time.getDT());
	ps.println("Simulation loops="+time.getN());
	ps.println("# of nodes simulated="+btGraph.getNumVertices());
	ps.println("\n<Connected components> <#Nodes of largest Connected components>  <Diameter> <Max degree>");
  }//Fine setup
  
 private void createBTGraph(Vector models)
  {
   Iterator i=models.iterator();
	while(i.hasNext())
	 {
	  //Estraggo i nodi dal modello	
	  Model nextModel=(Model)i.next();
	  //Aggiungo i nodi del modello corrente al grafo
	  addBTGVertices(nextModel.getNodes());
	 }	
  }//Fine createBtGraph  
 
 private void addBTGVertices(Vector nodes)
  {
   Iterator i=nodes.iterator();
   while(i.hasNext())
	{
	 //Creo il nuovo vertice  
	 Node nextNode=(Node)i.next();  
	 BTGVert newVert=new BTGVert(nextNode);
	 //Aggiungo il vertice al grafo bluetooth
	 btGraph.addVertice(newVert);
	}		 
  }//Fine addBTGVertices
  
 private void createOutputFile()
  {
   //Creo il file xml su cui salvare la simulazione
   try{
	   fos=new FileOutputStream(outputFilePath);
	   ps=new PrintStream(fos);
	  } 
	 catch (IOException e) 
	  { e.printStackTrace(); }
  }//Fine createXmlFile	    
 
  
 
  public void record(SimTime time) 
   {
	//if(((int)time.getTime())%2==0)  
	// { 
	  //Calcolo Nt
	  btGraph.calcNgraph();
	
	  //Calcolo Ct
	  btGraph.calcCgraph(7); 
	
	  //Calcolo il grado massimo e medio dei nodi
	  int maxDegree=btGraph.calcMaxDegree();
	  
      //Eseguo la ricerca BFS sul grafo costruito
	  btGraph.bfsSearch(0);
	  
	  //Calcolo il diametro del grafo con il metodo esaustivo
	  //int diameterMatrix=btGraph.calcDiameterWithMartix();
	 
	  //Scrivo le info nel file di output
	  ps.println(btGraph.getNumCompCon()+" "+
			     btGraph.getNumNodesLargConComp()+" "+
			     btGraph.getDiameter()+" "+
			     //diameterMatrix+" "+
	             maxDegree+" ");
	// }  
   
  }//Fine record

  
public void close()
 {
  //Chiudo il file 
	try
	 {fos.close();} 
	catch (IOException e) 
	   {e.printStackTrace();}  

  }//Fine close	
 }//Fine classe GBTRecorder



