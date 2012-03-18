package models.simpleModelStructures;

import models.ModelParser;

import org.xml.sax.Attributes;


public class SimpleModelParser extends ModelParser
 {
  protected static int PAUSETIME=5;
  protected static int VMIN=6;
  protected static int VMAX=7;
	
  private float pauseTime;	
  private float vMin;
  private float vMax;
      	
  public SimpleModelParser()
    {
	 super();
	 pauseTime=0;
	 vMin=0;
	 vMax=5;
    }//Fine costruttore
  
  public float getVMax()
   {
	if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax
  
  public float getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax
  
  public float getPauseTime()
   {
	if(pauseTime<0)  
	 return 0;
	else
     return pauseTime;		
   }//Fine getInterval  
  
  public void startElement (String uri, String name,String qName, Attributes atts)
   {
    //Richiamo il metodo del padre (controlla numero nodi,raggio nodi e raggio antenna)	  
    super.startElement(uri,name,qName,atts);	  
	
    //Intervallo di tempo <interval>value<interval>
    if(qName.equals("pauseTime")) 
	 { actTag=PAUSETIME; }
    
    //Velocita' minima <vMin>value</vMin>
    if(qName.equals("vMin")) 
	 { actTag=VMIN; }

    //Velocita' massima <vMax>value</vMax>
    if(qName.equals("vMax")) 
	 { actTag=VMAX; }
   }//Fine startElement
 
  public void characters(char[] ch,int start,int length)
   {
	//Richiamo il metodo del padre  
	super.characters(ch,start,length);  
	  
    String str=new String(ch,start,length);
    //Intervallo
    if(actTag==PAUSETIME) 
	 { 
      pauseTime=Float.valueOf(str);
      actTag=-1;
     }

    //Velocita' minima
    if(actTag==VMIN) 
	 {
      vMin=Float.valueOf(str);
      actTag=-1;
     }
   
    //Velocità massima
    if(actTag==VMAX) 
	 { 
      vMax=Float.valueOf(str);
      actTag=-1;
     }
   }//Fine characters
}//Fine SimpleModelParser