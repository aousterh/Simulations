package parsers;

import java.awt.Color;

import math.Point2d;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import engine.ScenCircle;
import engine.ScenRectangle;
import engine.ScenText;
import engine.Scenario;

public class SvgParser extends DefaultHandler 
 {
  private static int TEXT_TAG=0;	
	
  private Scenario scenario;
  protected boolean svgFile;
  
  private int actTag;
  private ScenText actText;
  
  public SvgParser()
   { 
	super();  
	scenario=null; 
	svgFile=false;
	
	actTag=-1;
	actText=null;
   }//Fine costruttore
  
  public Scenario getScenario()
   { return scenario; }
  
  public boolean isSvgFile()
   { return svgFile; }
  
  
  public void startElement(String uri, String name,String qName, Attributes atts)
   {
	qName=qName.toLowerCase(); 
	
	if(qName.equals("svg")) 
	 {	
	  svgFile=true;
	  float width=Float.parseFloat(atts.getValue("","width"));
      float height=Float.parseFloat(atts.getValue("","height"));
	  scenario=new Scenario(width,height,Scenario.BOUNDED);
	 }	
	
	if(qName.equals("rect"))
	 {
	  float x=Float.parseFloat(atts.getValue("","x"));
	  float y=Float.parseFloat(atts.getValue("","y"));		
	  float w=Float.parseFloat(atts.getValue("","width"));
	  float h=Float.parseFloat(atts.getValue("","height"));
	  
	  //Creo il rettangolo
	  ScenRectangle newRect=new ScenRectangle(new Point2d(x,y),w,h);
	  
      //Colore di riempimento
	  Color fill=getColor(atts.getValue(uri,"fill"),Color.GRAY);
	  if(fill!=null)
		newRect.setFillColor(fill);  
		   
	  //Colore del bordo
	  Color border=getColor(atts.getValue(uri,"stroke"),Color.RED);
	  if(border!=null)
		 newRect.setBorderColor(border);
	  
	  //Aggiungo allo scenario
	  if(scenario!=null)
	   scenario.addBuilding(newRect);
	   
	 }
	  
	 if(qName.equals("circle"))
	   {
		float cx=Float.parseFloat(atts.getValue("","cx"));
		float cy=Float.parseFloat(atts.getValue("","cy"));		
		float radius=Float.parseFloat(atts.getValue("","r"));
		
		//Creo il cerchio
		ScenCircle newCircle=new ScenCircle(new Point2d(cx,cy),radius);
		
		 
        //Colore di riempimento
		Color fill=getColor(atts.getValue(uri,"fill"),Color.GRAY);
		if(fill!=null)
		 newCircle.setFillColor(fill);  
		   
		//Colore del bordo
		Color border=getColor(atts.getValue(uri,"stroke"),Color.RED);
		if(border!=null)
		  newCircle.setBorderColor(border);
		
		//Aggiungo allo scenario  
		if(scenario!=null)
		  scenario.addBuilding(newCircle);
		  
	   }	
	 
	 if(qName.equals("text"))
	   {
		float x=Float.parseFloat(atts.getValue(uri,"x"));
		float y=Float.parseFloat(atts.getValue(uri,"y"));		
		
		//Creo il cerchio
		ScenText newText=new ScenText(new Point2d(x,y),"");
		
		//Colore di riempimento
		Color fill=getColor(atts.getValue(uri,"fill"),Color.BLACK);
		if(fill!=null)
		 newText.setStrColor(fill);  
		   
		//Aggiungo allo scenario  
	    if(scenario!=null)
	     {
	      actTag=TEXT_TAG;
	      actText=newText;
		  scenario.addText(newText);
	     } 
	   }	
	 
   }//Fine startElement
  
  public void characters(char[] ch,int start,int length)
  {
   String str=new String(ch,start,length);
   if(actTag==TEXT_TAG) 
	{ 
     if(actText!=null)
     {
      actText.setStr(str);
      actText=null;
      actTag=-1;
     } 
    }
  }//Fine characters  
  
  private Color getColor(String str,Color basicColor)
   {
	if(str==null)  
	return null; 
	else
	 {
	  try
	  {
	  try
	   {
	    String strValue=extractValue(str,4);
	    int total=4;
	    
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
	  catch(NullPointerException e)
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
	 if((c==',')||(c==')'))
	  found=true;	 
	 else
	  strValue+=c; 
	 
	 count++;
	}	 
   return strValue;
  }//Fine extraceValue  
 
 }//Fine SvgParser
