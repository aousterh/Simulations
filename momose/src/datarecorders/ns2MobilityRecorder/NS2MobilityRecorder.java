package datarecorders.ns2MobilityRecorder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import datarecorders.DataRecorder;

import math.Point2d;
import math.Vector2d;
import models.Model;
import models.Node;

import engine.Scenario;
import engine.SimTime;

public class NS2MobilityRecorder extends DataRecorder 
 {
  private Vector recNodes;
  private String outputFilePath;
  private PrintStream ps;
  private  FileOutputStream fos;
  
  
  public NS2MobilityRecorder()
   {
	recNodes=new Vector(); 	  
	ps=null;	  
	fos=null;
	outputFilePath="."+File.separator+"ns2MobilityRecorderOutput.xml";
   }//Fine costruttore
	
  public void setOutputFilePath(String outputFileName)
   { this.outputFilePath=outputFileName; }
	
  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
    //Creo gli oggetti recNode
    setupNodes(models); 
    
    //Creo il file dove registrare i dati
    ps=null;
	File outXmlFile=null;
	fos=null;
	try {
         outXmlFile= new File(outputFilePath);
	     fos=new FileOutputStream(outXmlFile);
	     ps=new PrintStream(fos);
	     ps.println("#ns2 mobility file");
	     ps.print("#Nodes:"+recNodes.size()+"   scenario:("+scenario.getWidth()+","+scenario.getHeight()+")");
	     ps.println(" ");
	     ps.println(" ");
	    } 
	    catch (IOException e) 
	       { e.printStackTrace(); }
	    
	//Salvo le posizioni iniziali
	saveInitialPositions();    
   }//Fine setup
  
  private void saveInitialPositions() 
   {
	Iterator i=recNodes.iterator();
	int index=0;
	while(i.hasNext())
	 {
	  //Estraggo il prossimo nodo	
	  Node nextNode=(Node)i.next();   
	  
	  //Registro la posizione iniziale
	  Point2d position=nextNode.getPosition();  
	  ps.println("$node_("+index+") set X_ "+position.x);
	  ps.println("$node_("+index+") set Y_ "+position.y);
	  ps.println("$node_("+index+") set Z_ 0.0");
	  
	  index++;
	 } 
	
   }//Fine savaInitialPositions
  
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
    int index=0;
	while(i.hasNext())
	 {
	  //Estraggo il prossimo nodo	
	  Node nextNode=(Node)i.next();
	  //Salvo le info sul nodo
	  if(time.getTime()!=0.0)
	   saveDest(nextNode,time,index);
	  
	  index++;
	 }	
	ps.println(" ");
   }//Fine record
	
  private void saveDest(Node node, SimTime time, int index) 
   {
	Point2d pos=node.getPosition();
	Vector2d v=node.getVelocity();
	
	if((v.x!=0)||(v.y!=0))
	 {	
	  //Point2d dest=Point2d.fromVector(pos,v);
	  Point2d dest=pos;	
	  ps.println("$ns_ at "+(time.getTime()-time.getDT())+ " \"$node_("+index+") setdest "+dest.x+" "+dest.y+" "+v.getLength()+"\"");
	 } 
   }//Fine saveDest
 

public void close() 
   {
	System.out.println("NS2MobilityRecorder: Fine simulazione...");   
	//Chiudo il file
	try{fos.close();} 
	  catch (IOException e) {e.printStackTrace();} 
   }//Fine close
 }//Fine NS2MobilityRecorder
