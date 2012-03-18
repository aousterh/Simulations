package datarecorders.btgRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class BTGRecorderBuilder extends DataRecorderBuilder
 {
  BTGRecorderDlg configDlg;	
  BTGRecorderParser configParser;	
	
  public BTGRecorderBuilder(String modelName) 
   { 
	super(modelName);
	//Creo la fisnestra di configurazione
	configDlg=new BTGRecorderDlg();
    //Creo il parser
	configParser=new BTGRecorderParser();
   }//Fine costruttore

  public ConfigDlg getConfigDlg() 
    {return configDlg;} 
	
  public DefaultHandler getConfigParser() 
    {return configParser;}

  public String toXml() 
    {
	 return new String("<filePath>"+configDlg.getFilePath()+"</filePath>\n");
	 
	 }//Fine toXml

  public DataRecorder createFromDlg() 
   { 
	BTGRecorder viewerRecorder=new BTGRecorder();
	viewerRecorder.setOutputFilePath(configDlg.getFilePath());
	return viewerRecorder;
   }//Fine createFromDlg

  public DataRecorder createFromFile() 
   {
	BTGRecorder viewerRecorder=new BTGRecorder();
	viewerRecorder.setOutputFilePath(configParser.getFilePath());
	return viewerRecorder;
   }//Fine createFromFile

  public void setConfigDlgFromFile() 
   {
	configDlg.setFilePath(configParser.getFilePath());
   }//Fine setConfigFromFile
 }//Fine ViewerRecorderBuilder
