package parsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import utils.Preferences;

public class PrefParser extends DefaultHandler
 {
  protected static int CONFIG_DIR=0;
  protected static int SCENARI_DIR=1;
  protected static int TRACEFILE_DIR=2;
  protected static int LOGFILE_DIR=3;
		
  private int actTag;
  private Preferences preferences;
  
  public PrefParser()
   {
	super();
	actTag=-1;
	preferences=new Preferences();
   }//Fine costruttore
  
  public Preferences getPreferences()
   { return preferences; }
  
  public void startElement (String uri, String name,String qName, Attributes atts)
  {
   if(qName.equals("ConfigFileDir")) 
	 { actTag=CONFIG_DIR;}
   if(qName.equals("ScenarioFileDir")) 
	 { actTag=SCENARI_DIR; }
   if(qName.equals("TraceFileDir")) 
	 { actTag=TRACEFILE_DIR; }
   if(qName.equals("LogFileDir")) 
	 { actTag=LOGFILE_DIR; }
  }//Fine startElement

 public void characters(char[] ch,int start,int length)
  {
   String str=new String(ch,start,length);
   
   if(actTag==CONFIG_DIR) 
	{ 
     preferences.setConfigDir(str);
     actTag=-1;
    }

   if(actTag==SCENARI_DIR) 
	{
     preferences.setScenariDir(str);
     actTag=-1;
    }
  
   if(actTag==TRACEFILE_DIR) 
	{ 
     preferences.setTraceFileDir(str);
     actTag=-1;
    }
   
   if(actTag==LOGFILE_DIR) 
	{ 
    preferences.setLogFileDir(str);
    actTag=-1;
   }
   
  }//Fine characters
}//Fine PrefParser
  
  
 
