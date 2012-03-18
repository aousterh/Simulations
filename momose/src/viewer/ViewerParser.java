package viewer;

import java.awt.Color;
import java.util.Vector;

import math.Point2d;

import org.xml.sax.Attributes;

import parsers.ScenarioParser;



public class ViewerParser extends ScenarioParser
 {
  //private Scenario scenario;
  private ViewTime time;
  private Vector nodes;
  private boolean traceFile;
  private int nodeCount;
  
  private long count=1;
    
  public ViewerParser()
   {
    super();
   
    this.nodes=new Vector();
    nodeCount=0;
    traceFile=false;
   }//Fine costruttore
  
  public void reset()
   {
    scenario=null;
    time=null;
    nodes.clear();  
   }//Fine reset
  
 // public Scenario getScenario()
  // {return scenario;}
 
 public ViewTime getViewTime()
  {return time; }
 
 public Vector getNodes()
  {return nodes;}
 
  public boolean isTraceFile()
   {return traceFile;}
 
 public void startElement (String uri, String name,String qName, Attributes atts)
 {
  super.startElement(uri, name, qName, atts);	
  
  //E' un trace-file??
  if(qName.equals("TraceInfo"))
	 { traceFile=true; } 
  
   //Creo il TEMPO
	if(qName.equals("SimTime"))
	 { createSimTime(atts);  }  	
  
  //Creo i NODI
  if(qName.equals("Node"))
   { createViewNode(atts); }
  
  //Resetto il conteggio dei nodi
  if(qName.equals("Time"))
   { nodeCount=0; }
  
  if(qName.equals("NT"))
  { 
   createNodeTimeInfo(uri,atts);
   //Incremento l'indice dei nodi
   nodeCount++;
  }
 }//Fine startElement 
 
 protected void createSimTime(Attributes atts) 
  {
  //Estraggo gli elementi necessari	 
  float TotalTime=Float.valueOf(atts.getValue(0));
  float dt=Float.valueOf(atts.getValue(1));
  int loops=Integer.valueOf(atts.getValue(2));

  //Creo l'oggetto ScenRectangle
  time=new ViewTime(TotalTime,dt,loops);  
 }//Fine create simTime
 
 private void createNodeTimeInfo(String uri,Attributes atts) 
  {
   //Prelevo il nodo attuale	 
   ViewNode node=(ViewNode)nodes.elementAt(nodeCount);	 
	 
   float x=Float.valueOf(atts.getValue(0));
   float y=Float.valueOf(atts.getValue(1));
   Point2d newPos=new Point2d(x,y);
   
   //Estraggo i Dati variabili
   //Raggio antenna
   float antennaRadius=readAntennaRadius(uri,atts,node);
     
   //Colore
   Color color=readColor(uri,atts,node);
   
   //Aggiungo l'informazione su questo istante di tempo al vettore 
   NodeTimeInfo info=new NodeTimeInfo(newPos,antennaRadius,color);
   node.addNodeTimeInfo(info);
  }//Fine createPosition

 private float readAntennaRadius(String uri,Attributes atts,ViewNode node)
  {
   int index=atts.getIndex(uri,"ar");
   if(index!=-1)
    {return Float.valueOf(atts.getValue(index));}
   else
    {
	 Vector timeInfos=node.getTimeInfo();
	 int infoSize=timeInfos.size();
	 if(infoSize==0)
		return 0;  
	 else
	  {	  
	   NodeTimeInfo info=(NodeTimeInfo)timeInfos.elementAt(infoSize-1); 	  
	   return info.getAntennaRadius();
	  }
	}   
  }//Fine getAntenna
 
 private Color readColor(String uri,Attributes atts,ViewNode node)
  {
   int indexR=atts.getIndex(uri,"r");
   int indexG=atts.getIndex(uri,"g");
   int indexB=atts.getIndex(uri,"b");
  
  if((indexR!=-1)&&(indexB!=-1)&&(indexG!=-1))
   {
	int r=Integer.valueOf(atts.getValue(indexR));
	int g=Integer.valueOf(atts.getValue(indexG));
	int b=Integer.valueOf(atts.getValue(indexB));
	return new Color(r,g,b);  
   }
  else
   {
	Vector timeInfos=node.getTimeInfo();
	int infoSize=timeInfos.size();
	if(infoSize==0)
	   return Color.BLUE;  
	  else
	   {	  
	    NodeTimeInfo info=(NodeTimeInfo)timeInfos.elementAt(infoSize-1); 	  
	    return info.getColor();
	   }
	}   
 }//Fine getAntenna
 
 private void createViewNode(Attributes atts) 
  {
   ViewNode newNode=new ViewNode(Float.valueOf(atts.getValue(0)));	
   nodes.add(newNode); 	
  }//Fine createViewNode
}//Fine classe ViewerSaxHandlerTime
