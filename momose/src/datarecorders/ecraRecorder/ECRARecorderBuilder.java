package datarecorders.ecraRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class ECRARecorderBuilder extends DataRecorderBuilder
 {
  ECRARecorderDlg configDlg;	
  ECRARecorderParser configParser;	
	
  public ECRARecorderBuilder(String modelName) 
   { 
	super(modelName);
	//Creo la fisnestra di configurazione
	configDlg=new ECRARecorderDlg();
    //Creo il parser
	configParser=new ECRARecorderParser();
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
	ECRARecorder ecraRecorder=new ECRARecorder();
	ecraRecorder.setOutputFilePath(configDlg.getFilePath());
	return ecraRecorder;
   }//Fine createFromDlg

  public DataRecorder createFromFile() 
   {
	ECRARecorder ecraRecorder=new ECRARecorder();
	ecraRecorder.setOutputFilePath(configParser.getFilePath());
	return ecraRecorder;
   }//Fine createFromFile

  public void setConfigDlgFromFile() 
   {
	configDlg.setFilePath(configParser.getFilePath());
   }//Fine setConfigFromFile
 }//Fine ViewerRecorderBuilder
