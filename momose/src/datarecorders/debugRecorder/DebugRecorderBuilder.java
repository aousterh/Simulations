package datarecorders.debugRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class DebugRecorderBuilder extends DataRecorderBuilder
 {
  public DebugRecorderBuilder(String modelName) 
   {super(modelName);}

  public DataRecorder createFromDlg() 
   {return new DebugRecorder();}

	
  public DataRecorder createFromFile() 
   {return new DebugRecorder();}

	
  public ConfigDlg getConfigDlg() 
   {return null;}

  public DefaultHandler getConfigParser() 
   {return null;}

  public String toXml() 
   {return null;}

  public void setConfigDlgFromFile() 
   {}
}//Fine classe DebugRecorderBuilder
