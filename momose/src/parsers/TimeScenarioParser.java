package parsers;

import org.xml.sax.Attributes;

import engine.Scenario;
import engine.SimTime;

public class TimeScenarioParser extends ScenarioParser 
 {
  protected SimTime time;
  
  public TimeScenarioParser()
   {
	super();
	time=null;
   }//Fine costruttore
  
  public Scenario getScenario()
   { return scenario; }
  
  public SimTime getSimTime()
   { return time; }
  
  public void startElement (String uri, String name,String qName, Attributes atts)
   {
	super.startElement(uri, name, qName, atts);  
	
	//Creo il tempo
	if(qName.equals("SimTime"))
	 { createSimTime(atts);  }    
   }//Fine startElement
  
  protected void createSimTime(Attributes atts) 
   {
    //Estraggo gli elementi necessari	 
    float TotalTime=Float.valueOf(atts.getValue(0));
    float dt=Float.valueOf(atts.getValue(1));
    int loops=Integer.valueOf(atts.getValue(2));

    //Creo l'oggetto ScenRectangle
    time=new SimTime(TotalTime,dt,loops);  
   }//Fine create simTime
 }//Fine TimeScenarioParser
