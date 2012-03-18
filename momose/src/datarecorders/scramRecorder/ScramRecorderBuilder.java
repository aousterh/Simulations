package datarecorders.scramRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class ScramRecorderBuilder extends DataRecorderBuilder
 {
  ScramRecorderDlg configDlg;	
  ScramRecorderParser configParser;	
	
  public ScramRecorderBuilder(String modelName) 
   { 
	super(modelName);
	//Creo la fisnestra di configurazione
	configDlg=new ScramRecorderDlg();
    //Creo il parser
	configParser=new ScramRecorderParser();
   }//Fine costruttore

  public ConfigDlg getConfigDlg() 
    {return configDlg;} 
	
  public DefaultHandler getConfigParser() 
    {return configParser;}

  public String toXml() 
    {
	 return new String("<filePath>"+configDlg.getFilePath()+"</filePath>\n" +
	 		           "<compressOutput>"+configDlg.getCompressOutput()+"</compressOutput>");
	 
	 }//Fine toXml

  public DataRecorder createFromDlg() 
   { 
	ScramRecorder scramRecorder=new ScramRecorder();
	scramRecorder.setOutputFilePath(configDlg.getFilePath());
	scramRecorder.setCompressOutput(configDlg.getCompressOutput());
	return scramRecorder;
   }//Fine createFromDlg

  public DataRecorder createFromFile() 
   {
	ScramRecorder scramRecorder=new ScramRecorder();
	scramRecorder.setOutputFilePath(configParser.getFilePath());
	scramRecorder.setCompressOutput(configParser.getCompressOutput());
	return scramRecorder;
   }//Fine createFromFile

  public void setConfigDlgFromFile() 
   {
	configDlg.setFilePath(configParser.getFilePath());
	configDlg.setCompressOutput(configParser.getCompressOutput());
   }//Fine setConfigFromFile
 }//Fine ScramRecorderBuilder
