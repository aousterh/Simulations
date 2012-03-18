package parsers;

import java.awt.Color;

import math.Point2d;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import engine.Connection;
import engine.HotSpot;
import engine.ScenCircle;
import engine.ScenRectangle;
import engine.ScenText;
import engine.Scenario;

public class ScenarioParser extends DefaultHandler 
 {
  protected Scenario scenario;
  protected boolean scenarioFile;
  
  public ScenarioParser()
   { 
	super();  
	scenario=null; 
	scenarioFile=false;
   }//Fine costruttore
  
  public Scenario getScenario()
   { return scenario; }
  
  public boolean isScenarioFile()
   { return scenarioFile; }
  
  public void startElement (String uri, String name,String qName, Attributes atts)
   {
	//E' un file di scnenario??
	if(qName.equals("ScenarioInfo")) 
	 scenarioFile=true;
	
	//Creo lo SCENARIO
	if(qName.equals("Scenario"))
	 { createScenario(uri,atts);  }  
	//Aggiungo il ScenRectangle allo scenario
	if(qName.equals("Rect"))
	  { createScenRectangle(uri,atts);  } 
	//Aggiungo il ScenCircle allo scenario
	if(qName.equals("Circle"))
	  { createScenCircle(uri,atts);  } 
	//Aggiungo il testo
	if(qName.equals("Text"))
	  { createScenText(uri,atts);  } 
	//Aggiungo l'hotSpot allo scenario
	 if(qName.equals("HotSpot"))
	  { createHotSpot(uri,atts);  } 
	//Aggiungo la connessione tra gli hotspot 
	 if(qName.equals("Connection"))
	  { createConnection(uri,atts);  } 
  }//Fine startElement
  
    
 private void createScenText(String uri,Attributes atts) 
  {
   //Estraggo gli elementi necessari	 
   float x=Float.valueOf(atts.getValue(uri,"x"));
   float y=Float.valueOf(atts.getValue(uri,"y"));
   String str=atts.getValue(uri,"str");
   
   //Creo il testo
   ScenText newText=new ScenText(new Point2d(x,y),str);
   
   //Colore del punto
   Color pointColor=getColor(atts.getValue(uri,"pointColor"),null);
   if(pointColor!=null)
	 newText.setPointColor(pointColor);  
   
   //Colore del testo
   Color strColor=getColor(atts.getValue(uri,"strColor"),Color.BLACK);
   if(strColor!=null)
	 newText.setStrColor(strColor);
   
   //Angolo di rotazione
   String strAngle=atts.getValue(uri,"rotation");
   if(strAngle!=null)
    {
	 float angle=Float.valueOf(strAngle);
	 newText.setRotation(angle);
    }  
   
   //Scala del testo
   String strScale=atts.getValue(uri,"scale");
   if(strScale!=null)
    {
	 float scale=Float.valueOf(strScale);
	 newText.setScale(scale);
    }  
   
   
   //Aggiungo il ScenText allo scenario
   if(scenario!=null)
    { scenario.addText(newText); }	
  }//Fine createScenTExt

protected void createScenCircle(String uri,Attributes atts) 
  {
   //Estraggo gli elementi necessari	 
   float x=Float.valueOf(atts.getValue(uri,"x"));
   float y=Float.valueOf(atts.getValue(uri,"y"));
   float radius=Float.valueOf(atts.getValue(uri,"radius"));
   
   //Creo l'oggetto ScenRectangle
   ScenCircle newCircle=new ScenCircle(new Point2d(x,y),radius);
   
   //Estraggo e setto i dati opzionali
   //Nome del building
   String name=atts.getValue(uri,"name");
   if(name!=null)
   { newCircle.setName(name);}
   
   
   // Colore di riempimento
   Color fill=getColor(atts.getValue(uri,"fill"),Color.GRAY);
   if(fill!=null)
	 newCircle.setFillColor(fill);  
   
   //Colore del bordo
   Color border=getColor(atts.getValue(uri,"border"),Color.RED);
   if(border!=null)
	 newCircle.setBorderColor(border);
   
   //Valore di attenuazione
   float attenuation=Float.valueOf(atts.getValue(uri,"attenuation"));
   newCircle.setAttenuation(attenuation);
   
   //Aggiungo il ScenRectangle allo scenario
   if(scenario!=null)
    { scenario.addBuilding(newCircle); }	 
 }//Fine createScenCircle

 protected void createScenRectangle(String uri, Attributes atts) 
  {
   //Estraggo gli elementi necessari	 
   float x=Float.valueOf(atts.getValue(uri,"x"));
   float y=Float.valueOf(atts.getValue(uri,"y"));
   float width=Float.valueOf(atts.getValue(uri,"width"));
   float height=Float.valueOf(atts.getValue(uri,"height"));
   
   //Creo l'oggetto ScenRectangle
   ScenRectangle newRectangle=new ScenRectangle(new Point2d(x,y),width,height);
   
   //Estraggo i dati opzionali
   //Nome del building
   String name=atts.getValue(uri,"name");
   if(name!=null)
   {newRectangle.setName(name);} 
   
   //Valore di attenuazione
   float attenuation=Float.valueOf(atts.getValue(uri,"attenuation"));
   newRectangle.setAttenuation(attenuation);
   
   //Angolo di rotazione
   String strAngle=atts.getValue(uri,"rotation");
   if(strAngle!=null)
    {
	 float angle=Float.valueOf(strAngle);
	 newRectangle.setRotAngle(angle);
    }  
   
   //Colore di riempimento
   Color fill=getColor(atts.getValue(uri,"fill"),Color.GRAY);
   if(fill!=null)
	 newRectangle.setFillColor(fill);  
   
   //Colore del bordo
   Color border=getColor(atts.getValue(uri,"border"),Color.RED);
   if(border!=null)
	 newRectangle.setBorderColor(border);
   
   //Aggiungo il ScenRectangle allo scenario
   if(scenario!=null)
    { scenario.addBuilding(newRectangle); }
  }//Fine createScenRectange

 private Color getColor(String str,Color basicColor)
  {
	if(str==null)  
	return null; 
	else
	 {
	  try
	   {
	    String strValue=extractValue(str,0);
	    int total=0;
	    
	    int red=Integer.valueOf(strValue);
	    total+=strValue.length()+1;  
	    
	  
	    strValue=extractValue(str,total);
	    int green=Integer.valueOf(strValue);
	    total+=strValue.length()+1;  
	  
	  
	    strValue=extractValue(str,total);
	    int blue=Integer.valueOf(strValue);
	  
	    return new Color(red,green,blue);
	  }
	  catch(NumberFormatException e)
	   {return basicColor;}
	  
	 }	
  }//Fine getColor
 
 private String extractValue(String str,int startIndex)
  {
   boolean found=false;
   char c;
   int count=0;
   String strValue=new String();
      
   while((!found)&&((startIndex+count)<str.length()))
    {
	 c=str.charAt(startIndex+count);
	 if(c==',')
	  found=true;	 
	 else
	  strValue+=c; 
	 
	 count++;
	}	 
   
   return strValue;
  }//Fine extraceValue
 
 
 protected void createScenario(String uri,Attributes atts) 
  {
   float width=Float.valueOf(atts.getValue(uri,"width"));
   float height=Float.valueOf(atts.getValue(uri,"height"));	
   int borderType=Integer.valueOf(atts.getValue(uri,"border"));
   //Creo lo scneario
   scenario=new Scenario(width,height,borderType); 
  }//Fine createScenario
 
 protected void createConnection(String uri, Attributes atts) 
  {
   //Estraggo gli attributi	 
   int startId=Integer.valueOf(atts.getValue(uri,"startSpot"));
   int endId=Integer.valueOf(atts.getValue(uri,"endSpot"));
   float weight=Float.valueOf(atts.getValue(uri,"weight"));
  
   if(scenario!=null)
    {
	 //Estraggo nodo iniziale
	 HotSpot startSpot=scenario.getHotSpot(startId);
	 HotSpot endSpot=scenario.getHotSpot(endId);
	 
	 //Cerco Aggiungo la connessione
	 if((startSpot!=null)&&(endSpot!=null))
	  {
	   Connection newConn=new Connection(startSpot,endSpot,weight);
	   //Aggiungo la connessione al nodo di partenza
	   startSpot.addConnection(newConn);
	  } 	 
	}   
 }//Fine createConnection

protected void createHotSpot(String uri, Attributes atts) 
 {
  //Estraggo gli elementi necessari
  int ID=Integer.valueOf(atts.getValue(uri,"ID")); 	
  float x=Float.valueOf(atts.getValue(uri,"x"));
  float y=Float.valueOf(atts.getValue(uri,"y"));
  float radius=Float.valueOf(atts.getValue(uri,"radius"));
  

  //Creo l'oggetto HotSpot
  HotSpot newSpot=new HotSpot(new Point2d(x,y),radius,ID);    

  //Aggiungo il ScenRectangle allo scenario
  if(scenario!=null)
   { scenario.addHotSpot(newSpot); }	
 }//Fine create HotSPot
 
 
}//Fine ScenarioParser
