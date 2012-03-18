package parsers;

import java.io.File;
import java.util.Vector;

import org.xml.sax.Attributes;

import engine.Scenario;
import engine.SimTime;

public class ConfigSimParser extends TimeScenarioParser
 {
  private Scenario scenario;
  private SimTime simTime;
  private Vector modBuildNames;
  private Vector recBuildNames;
  
  private boolean configFile;
  
  public ConfigSimParser(File file)
   {
	scenario=null;
	simTime=null;
	modBuildNames=new Vector();
	recBuildNames=new Vector();
	configFile=false;
   }//Fine costruttore
   
  public Vector getModBuildNames()
   {return modBuildNames;}
  
  public Vector getRecBuildNames()
   {return recBuildNames;}
  
  public boolean isConfigFile()
   {return configFile;}
  
  public void startElement (String uri, String name,String qName, Attributes atts)
   {
	//Richiamo il metodo del genitore per gestire il tempo e lo scenario  
    super.startElement(uri, name, qName, atts);  
	
    //E' un file di configurazione??
    if(qName.equals("ConfigInfo"))
	 { configFile=true; }  
    
	//Nome di un model builder
	if(qName.equals("ModBuilder"))
	 { addModBuildName(atts); }  
	
	//Nome du un recorderBuilder
	if(qName.equals("RecBuilder"))
	 { addRecBuildName(atts); }  
	
  }//Fine startElement
  
 private void addModBuildName(Attributes atts) 
  {
   String name=atts.getValue(0);
   if(name!=null)
	 modBuildNames.add(name);  
  }//Fine addModBuildName 

 private void addRecBuildName(Attributes atts) 
  {
   String name=atts.getValue(0);
   if(name!=null)
	 recBuildNames.add(name);  
  }//Fine addRecBuildName
}//Fine ConfigSimParser
