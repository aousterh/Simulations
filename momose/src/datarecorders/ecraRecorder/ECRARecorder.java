package datarecorders.ecraRecorder;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import utils.TraceFileCompressor;

import datarecorders.DataRecorder;

import math.Point2d;
import models.Model;
import models.Node;
import models.ecraModel.EcraNode;

import engine.Scenario;
import engine.SimTime;

public class ECRARecorder extends DataRecorder
 {
  private Vector nodes;
  private SimTime simTime;
  private String outputFilePath;
  
  private PrintStream ps;
  private File outputFile;
  private FileOutputStream fos;
  
  private float maxDegrees[];
  private int maxActDegrees[];
  private float averageDegrees[];
  private int numTypes[];
  private float actDegrees[];
  
  private int countLoops;
  private int nodeSpaceDistr[][];
  private Scenario scenario;
  
      
  public ECRARecorder()
   {
    nodes=new Vector();	 
    ps=null;
	outputFile=null;
	fos=null;
	outputFilePath="."+File.separator+"ecraRecorderOutput.txt";
	
	maxDegrees=new float[3];
	maxDegrees[0]=maxDegrees[1]=maxDegrees[2]=0;
	maxActDegrees=new int[3];
	maxActDegrees[0]=maxActDegrees[1]=maxActDegrees[2]=0;
	
	averageDegrees=new float[3];
	averageDegrees[0]=averageDegrees[1]=averageDegrees[2]=0;
	numTypes=new int [3];
	actDegrees=new float[3];
	nodeSpaceDistr=null;
	
	countLoops=0;
   }//Fine costruttori
  
  public void setOutputFilePath(String outputFileName)
   { this.outputFilePath=outputFileName; }
  
  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
	//Creo la lista dei nodi
	setupNodes(models);  
	
	//Creo il file xml su cui salvare i dati
	createOutputFile(time);
	
	//Prendo un riferimento al tempo
	this.simTime=time;	
	
	//Prendo un riferimento allo scenario
	this.scenario=scenario;
	
	//Preparo la matrice di distribuzione
	nodeSpaceDistr=new int[(int)(scenario.getWidth()+1)][(int)(scenario.getHeight()+1)];
	for(int i=0;i<(int)(scenario.getWidth()+1);i++)
	 for(int j=0;j<(int)(scenario.getHeight()+1);j++)
         nodeSpaceDistr[i][j]=0;
   }//Fine setup
  
 private void setupNodes(Vector models)
  {
   Iterator i=models.iterator();
	while(i.hasNext())
	 {
	  //Estraggo i nodi dal modello	
	  Model nextModel=(Model)i.next();
	  nodes.addAll(nextModel.getNodes());
	 }	
  }//Fine setupNodes
 
  
 private void createOutputFile(SimTime time)
  {
   //Creo il file xml su cui salvare la simulazione
   try{
	   fos=new FileOutputStream(outputFilePath,true);
	   ps=new PrintStream(fos);
	  } 
	 catch (IOException e) 
	  { e.printStackTrace(); }
  }//Fine createXmlFile	    
 
 
public void record(SimTime time) 
 {
  int actDegrees[]=new int [3];	
  actDegrees[0]=actDegrees[1]=actDegrees[2]=0;
  maxActDegrees[0]=maxActDegrees[1]=maxActDegrees[2]=0;
  int numTypes[]=new int [3];	
  numTypes[0]=numTypes[1]=numTypes[2]=0;
  int degree,type;
  
  Iterator i=nodes.iterator();
  while(i.hasNext())
   {
	EcraNode nextNode=(EcraNode)i.next();
	
	/*//Prelevo il grado 
	degree=calcDregree(nextNode);
	
	//Aggiungo il tipo
	type=nextNode.getType();	
	numTypes[type-1]++;
	
	//calcolo il grado massimo
	if(maxActDegrees[type-1]<degree)
 	 maxActDegrees[type-1]=degree;
	
	//Aggiungo per grado medio
	actDegrees[type-1]+=degree;*/
	
	
	//Calcolo la distribuzione
	Point2d pos=nextNode.getPosition();
	nodeSpaceDistr[(int)pos.x][(int)pos.y]++;
	
	
   } 
  
  countLoops++;
  
  //Aggiungo al grado massimo totale
  /*maxDegrees[0]+=maxActDegrees[0];
  maxDegrees[1]+=maxActDegrees[1];
  maxDegrees[2]+=maxActDegrees[2];
  
  //Aggiungo al grado medio
  averageDegrees[0]+=(actDegrees[0]/nodes.size());
  averageDegrees[1]+=(actDegrees[1]/nodes.size());
  averageDegrees[2]+=(actDegrees[2]/nodes.size());*/
 }//Fine record

private int calcDregree(EcraNode node)
 {
  Iterator i=nodes.iterator();
  int degree=0;
  
  while(i.hasNext())
   {
	EcraNode nextNode=(EcraNode)i.next();
	if(nextNode.getId()!=node.getId())
	 {
	  if(Point2d.distance(node.getPosition(),nextNode.getPosition())<=
	      node.getAntennaRadius())
	  {degree++;} 
	  
	 }	
   }
  return degree;
 }//Fine calcDegrees
 
private void writeNodeInfo() 
 {
  for(int i=0;i<nodes.size();i++)
   {
	Node nextNode=(Node)nodes.elementAt(i); 	
   }		 
 }//Fine writeNodeInfo

 public void close()
   {
	//Calcolo la percentuale di appartenenza 
	calcTypePerc();
	
	
    //Chiudo il file xml
	try
	 {fos.close();} 
	catch (IOException e) 
	   {e.printStackTrace();}  
  }//Fine close	
 
 private void calcTypePerc()
  {
   /*float totalStep=simTime.getT()/simTime.getDT();
   // System.out.println("totalStep="+totalStep);
   int countTypes[]=new int[3];
   float meanTypes[]=new float[3];
   float meanPerc[]=new float[3];
   
   meanTypes[0]=meanTypes[1]=meanTypes[2]=0;
   meanPerc[0]=meanPerc[1]=meanPerc[2]=0;
   
   
   for(int i=0;i<nodes.size();i++)
    {
	 EcraNode nextNode=(EcraNode)nodes.elementAt(i);
	 countTypes=nextNode.getCountTypes();
	 meanTypes[0]+=countTypes[0]/totalStep;
	 meanTypes[1]+=countTypes[1]/totalStep;
	 meanTypes[2]+=countTypes[2]/totalStep;
	}
   
   meanPerc[0]=meanTypes[0]/nodes.size();
   meanPerc[1]=meanTypes[1]/nodes.size();
   meanPerc[2]=meanTypes[2]/nodes.size();
   
   /*System.out.println("meanPerc[0]="+meanPerc[0]);
   System.out.println("meanPerc[1]="+meanPerc[1]);
   System.out.println("meanPerc[2]="+meanPerc[2]);
   System.out.println("Somma="+(meanPerc[0]+meanPerc[1]+meanPerc[2]));
   
   //Scrivo i dati nel file di output
   //ps.append(nodes.size()+" "+(meanPerc[0]*100)+" "+(meanPerc[1]*100)+" "+(meanPerc[2]*100)+"\n");
   
   //Calcolo i gradi
   maxDegrees[0]/=countLoops;
   maxDegrees[1]/=countLoops;
   maxDegrees[2]/=countLoops;
   
   /*System.out.println("maxDeegress[0]="+maxDegrees[0]);
   System.out.println("maxDegrees[1]="+maxDegrees[1]);
   System.out.println("maxDegrees[2]="+maxDegrees[2]);
   
   
   averageDegrees[0]/=countLoops;
   averageDegrees[1]/=countLoops;
   averageDegrees[2]/=countLoops;
   
   System.out.println("averageDegreess[0]="+averageDegrees[0]);
   System.out.println("averageDegrees[1]="+averageDegrees[1]);
   System.out.println("averageDegrees[2]="+averageDegrees[2]);
   
   ps.append(nodes.size()+" "+maxDegrees[0]+" "+maxDegrees[1]+" "+maxDegrees[2]+
		     " "+averageDegrees[0]+" "+averageDegrees[1]+" "+averageDegrees[2]+"\n");*/
   
   float max=0;
   for(int i=0;i<(int)(scenario.getWidth()+1);i++)
	{   
   for(int j=0;j<(int)(scenario.getHeight()+1);j++)
	 {
	   if(max<nodeSpaceDistr[i][j])
		   max=nodeSpaceDistr[i][j];
	 }
    }
     
   float num=0;
   int iNum=0;
  //Scrivo i dati sulla distribuzione dei nodi
   for(int i=0;i<(int)(scenario.getWidth()+1);i++)
    {   
     for(int j=0;j<(int)(scenario.getHeight()+1);j++)
     {
      num=(float)(nodeSpaceDistr[i][j]/max);
      num=(float)(Math.round(num*1000.0f)/1000.0f);  
      
      
      ps.println(i+" "+j+" "+num); 	 
    	  
	  //ps.println(i+" "+j+" "+nodeSpaceDistr[i][j]);
	  
	  
	  //System.out.println(i+" "+j+" "+nodeSpaceDistr[i][j]);
	 }
     ps.print("\n");
   }
   
   
   
  }//Fine calcTypePer
 
 
 
 }//Fine classe ViewerRecorderTime



