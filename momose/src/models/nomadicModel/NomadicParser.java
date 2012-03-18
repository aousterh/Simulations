package models.nomadicModel;

import models.ModelParser;

import org.xml.sax.Attributes;

public class NomadicParser extends ModelParser
{
 protected static int ALPHA=5;
 protected static int VMIN=6;
 protected static int VMAX=7;
 protected static int PAUSE_TIME=8;
		
 private float alpha;	
 private float vMin;
 private float vMax;
 private float pauseTime;
	      	
 public NomadicParser()
  {
   super();
   alpha=0.5f;
   vMin=0;
   vMax=5;
   pauseTime=0.5f;	
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
 
 public float getAlpha()
  {
	if(alpha<0)  
	 return 0;
   else
    return alpha;		
  }//Fine getInterval  
 
 public float getPauseTime()
  {
	if(pauseTime<0)  
	 return 0;
  else
   return pauseTime;		
 }//Fine getTargetRadius 
	  
 public void startElement (String uri, String name,String qName, Attributes atts)
  {
   //Richiamo il metodo del padre (controlla numero nodi,raggio nodi e raggio antenna)	  
   super.startElement(uri,name,qName,atts);	  
	
   //Intervallo di tempo <pauseTime>value<pauseTime>
   if(qName.equals("alpha")) 
	 { actTag=ALPHA; }
   
   //Velocita' minima <vMin>value</vMin>
   if(qName.equals("vMin")) 
	 { actTag=VMIN; }
	    //Velocita' massima <vMax>value</vMax>
   if(qName.equals("vMax")) 
	 { actTag=VMAX; }
   
   if(qName.equals("pauseTime")) 
	 { actTag=PAUSE_TIME; }
  }//Fine startElement

 public void characters(char[] ch,int start,int length)
  {
	//Richiamo il metodo del padre  
	super.characters(ch,start,length);  
	  
   String str=new String(ch,start,length);
   //Intervallo
   if(actTag==ALPHA) 
	{ 
     alpha=Float.valueOf(str);
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
   
   //Raggio del target
   if(actTag==PAUSE_TIME) 
	 { 
      pauseTime=Float.valueOf(str);
      actTag=-1;
     }
  }//Fine characters
}//Fine classe NomadicParser