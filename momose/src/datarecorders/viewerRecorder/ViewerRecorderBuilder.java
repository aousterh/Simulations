package datarecorders.viewerRecorder;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;

public class ViewerRecorderBuilder extends DataRecorderBuilder
 {
  ViewerRecorderDlg configDlg;	
  ViewerRecorderParser configParser;	
	
  public ViewerRecorderBuilder(String modelName) 
   { 
	super(modelName);
	//Creo la fisnestra di configurazione
	configDlg=new ViewerRecorderDlg();
    //Creo il parser
	configParser=new ViewerRecorderParser();
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
	ViewerRecorder viewerRecorder=new ViewerRecorder();
	viewerRecorder.setOutputFilePath(configDlg.getFilePath());
	viewerRecorder.setCompressOutput(configDlg.getCompressOutput());
	return viewerRecorder;
   }//Fine createFromDlg

  public DataRecorder createFromFile() 
   {
	ViewerRecorder viewerRecorder=new ViewerRecorder();
	viewerRecorder.setOutputFilePath(configParser.getFilePath());
	viewerRecorder.setCompressOutput(configParser.getCompressOutput());
	return viewerRecorder;
   }//Fine createFromFile

  public void setConfigDlgFromFile() 
   {
	configDlg.setFilePath(configParser.getFilePath());
	configDlg.setCompressOutput(configParser.getCompressOutput());
   }//Fine setConfigFromFile
 }//Fine ViewerRecorderBuilder
