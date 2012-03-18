package datarecorders.viewerRecorder;

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

import engine.Scenario;
import engine.SimTime;

public class ViewerRecorder extends DataRecorder
 {
  private Vector nodes;
  private SimTime simTime;
  private String outputFilePath;
  
  private PrintStream ps;
  private File outXmlFile;
  private FileOutputStream fos;
  private boolean compressOutput;
  
  Vector lasts;
   
  public ViewerRecorder()
   {
    nodes=new Vector();	 
    ps=null;
	outXmlFile=null;
	fos=null;
	outputFilePath="."+File.separator+"viewerRecorderOutput.xml";
	compressOutput=false;
	lasts=new Vector();
   }//Fine costruttori
  
  public void setOutputFilePath(String outputFileName)
   { this.outputFilePath=outputFileName; }
  
  public void setCompressOutput(boolean compressOutput)
   { this.compressOutput=compressOutput; }
   
  public void setup(Vector models, Scenario scenario, SimTime time) 
   {
	//Creo la lista dei nodi
	setupNodes(models);  
	
	//Creo il file xml su cui salvare i dati
	createXmlFile(time);
	
	//Scrivo il preabolo del file xml
	writeStaticInfo(time,scenario);
	
	//Prendo un riferimento al tempo
	this.simTime=time;	
   }//Fine setup
  
 private void setupNodes(Vector models)
  {
   Iterator i=models.iterator();
	while(i.hasNext())
	 {
	  //Estraggo i nodi dal modello	
	  Model nextModel=(Model)i.next();
	  nodes.addAll(nextModel.getNodes());
	  //Setto le info last 
	  setupLastInfo(nextModel.getNodes().size());
	 }	
  }//Fine setupNodes
 
 private void setupLastInfo(int num)
  {
   for(int i=0;i<num;i++)
	lasts.add(new LastInfo());
  }//Fine setupLastInfo
 
 private void createXmlFile(SimTime time)
  {
   //Creo il file xml su cui salvare la simulazione
   try{
	   fos=new FileOutputStream(outputFilePath);
	   ps=new PrintStream(fos);
	  } 
	 catch (IOException e) 
	  { e.printStackTrace(); }
  }//Fine createXmlFile	    
 
 private void writeStaticInfo(SimTime simTime, Scenario scenario) 
  {
   //Scrivo il preabolo del file xml
	ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	ps.println("<TraceInfo>\n");
	   
	//Scrivo le info sullo scenario
	ps.println(scenario.toXml());
	
   //Scrivo le info di base sui nodi
   writeBaseNode();	
   
  }//Fine writeStaticInfo 
  
 private void writeBaseNode() 
  {
   //Scrivo il tag di apertura	 
   ps.println(" <Nodes num=\""+nodes.size()+"\">");
   
   //Scrivo il tag di chisura
   Iterator i=nodes.iterator();
   while(i.hasNext())
    {
	 Node nextNode=(Node)i.next();
	 ps.println("  <Node radius=\""+nextNode.getRadius()+"\"/>");
    } 
   
   //Scrivo il tag di chiusura
   ps.println(" </Nodes>");
  }//Fine writeBaseNodeInfo

public void record(SimTime time) 
   {
	//Scrivo il tag del tempo
	ps.println(" <Time t=\""+time.getTime()+"\" loop=\""+simTime.getLoop()+"\">");	 
    
	//Scrivo le info sui nodi
	writeNodeInfo();
	
	//Scrivo i tag di chiusura
	ps.println(" </Time>\n");
   }//Fine record

 /*private void writeNodeInfo() 
  {
   Iterator i=nodes.iterator();
   while(i.hasNext())
    {
	 Node nextNode=(Node)i.next(); 	
	 
	 Point2d pos=nextNode.getPosition();
	 Color rgb=nextNode.getColor();
	 
	 //Scrivo le info nel file
	 ps.println("  <NT x=\""+pos.x+"\" y=\""+pos.y+
			    "\" ar=\""+nextNode.getAntennaRadius()+
			    "\" r=\""+rgb.getRed()+
			    "\" g=\""+rgb.getGreen()+
			    "\" b=\""+rgb.getBlue()+
			    "\"/>");
	}		 
  }//Fine writeNodeInfo*/

private void writeNodeInfo() 
 {
  for(int i=0;i<nodes.size();i++)
   {
	Node nextNode=(Node)nodes.elementAt(i); 	
	 
	Point2d pos=nextNode.getPosition();
	Color rgb=nextNode.getColor();
	 
	//Scrivo le info nel file
	ps.print("  <NT x=\""+pos.x+"\" y=\""+pos.y+"\"");
	
	LastInfo lastInfo=(LastInfo)lasts.elementAt(i);
	
	if(lastInfo.cfrAndSwap(nextNode.getAntennaRadius()))
	 {ps.print(" ar=\""+nextNode.getAntennaRadius()+"\"");}
	
	
	if(lastInfo.cfrAndSwap(nextNode.getColor()))
	 {
	  ps.print(" r=\""+rgb.getRed()+
			   "\" g=\""+rgb.getGreen()+
			   "\" b=\""+rgb.getBlue()+"\"");
	 }
	
	ps.print("/>\n");
   }		 
}//Fine writeNodeInfo

  public void close()
   {
    //Scrivo le info sul tempo
	ps.println(simTime.dynamicTimetoXml()); 
	 
    //Chiudo il tag di root
	ps.println("</TraceInfo>");
	
	//Chiudo il file xml
	try
	 {fos.close();} 
	catch (IOException e) 
	   {e.printStackTrace();}  
	
	//Comprimo il file
	if(compressOutput==true)
	{	
	 File normalFile=new File(outputFilePath); 
	 if(normalFile.exists())
	  {	 
	   File compressedFile=new File(outputFilePath+".gz");
	   boolean res=TraceFileCompressor.compress(normalFile,compressedFile);
	   if(res==true)
		 normalFile.delete();  
	  }
	 
	} 
  }//Fine close	
 }//Fine classe ViewerRecorderTime


class LastInfo
 {
  private float ar;
  private Color color;
  
  public LastInfo()
   {
	this.ar=-1;
	this.color=null; 
   }//Fine costruttore
  
 public float getAR()
  {return ar;} 
 
 public Color getColor()
  {return color;} 
 
 public boolean cfrAndSwap(float nowAr)
  {
   if(ar==-1)
    {
	 ar=nowAr;
	 return true;
    }  
	 
   if(ar==nowAr)
   { return false;}
  else
   {
	ar=nowAr;
	return true;
   }
  }//Fine cfrAndSwap 
 
 public boolean cfrAndSwap(Color nowCol)
  {
   if(color==null)
	{
	 color=nowCol;
	 return true;
	}  	 
	 
   if(color==nowCol)
    { return false;}
   else
    {
	 color=nowCol;
	 return true;
    }
 }//Fine cfrAndSwap 
 
}//Fine LastInfo
