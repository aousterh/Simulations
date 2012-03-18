package datarecorders.ns2MobilityRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class NS2MobilityRecorderBuilder extends DataRecorderBuilder
 {
  NS2MobilityRecorderDlg configDlg;	
  NS2MobilityRecorderParser configParser;	
	
  public NS2MobilityRecorderBuilder(String modelName) 
   { 
	super(modelName);
	//Creo la fisnestra di configurazione
	configDlg=new NS2MobilityRecorderDlg();
    //Creo il parser
	configParser=new NS2MobilityRecorderParser();
   }//Fine costruttore

  public ConfigDlg getConfigDlg() 
    {return configDlg;} 
	
  public DefaultHandler getConfigParser() 
    {return configParser;}

  public String toXml() 
    {
	 return new String("<filePath>"+configDlg.getFilePath()+"</filePath>");
	 
	 }//Fine toXml

  public DataRecorder createFromDlg() 
   { 
	NS2MobilityRecorder ns2MobilityRecorder=new NS2MobilityRecorder();
	ns2MobilityRecorder.setOutputFilePath(configDlg.getFilePath());
	return ns2MobilityRecorder;
   }//Fine createFromDlg

  public DataRecorder createFromFile() 
   {
	NS2MobilityRecorder ns2MobilityRecorder=new NS2MobilityRecorder();
	ns2MobilityRecorder.setOutputFilePath(configParser.getFilePath());
	return ns2MobilityRecorder;
   }//Fine createFromFile

  public void setConfigDlgFromFile() 
   {
	configDlg.setFilePath(configParser.getFilePath());
   }//Fine setConfigFromFile
 }//Fine ViewerRecorderBuilder
