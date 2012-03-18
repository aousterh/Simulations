package models.nomadicModel;

import gui.ConfigDlg;
import models.Model;
import models.ModelBuilder;

import org.xml.sax.helpers.DefaultHandler;

public class NomadicModelBuilder extends ModelBuilder
{
 private NomadicConfigDlg configDlg;
 private NomadicParser configParser;	
	
 
 public NomadicModelBuilder(String name)
  {
   super(name);  
   //Creo la finestra di configurazione
   configDlg=new NomadicConfigDlg();
   //Creo il parser
   configParser=new NomadicParser();
  }//Fine costruttore	  
   
  public ConfigDlg getConfigDlg() 
   { return configDlg;}

  public DefaultHandler getConfigParser() 
   {return configParser;}

	
 public String toXml() 
  { 
   return new String("<nn>"+configDlg.getNumNodes()+"</nn>\n"
		              +"<nr>"+configDlg.getNodeRadius()+"</nr>\n"
		              +"<ar>"+configDlg.getAntennaRadius()+"</ar>\n"
                     +"<vMin>"+configDlg.getVMin()+"</vMin>\n"
                     +"<vMax>"+configDlg.getVMax()+"</vMax>\n"
                     +"<alpha>"+configDlg.getAlpha()+"</alpha>\n"
                     +"<pauseTime>"+configDlg.getPauseTime()+"</pauseTime>\n"
                     +"<isPhy>"+configDlg.isPhysical()+"</isPhy>\n");
                    
 }//Fine toXml 

public Model createFromDlg() 
 {
  NomadicModel nomadicModel=new NomadicModel();
  //Setto i parametri il modello
  nomadicModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                    configDlg.getAntennaRadius(),configDlg.getAlpha(),
		                    configDlg.getVMin(),configDlg.getVMax(),
		                    configDlg.getPauseTime(),configDlg.isPhysical());
  //Ritorno il modello
  return nomadicModel; 
 }//Fine createModelFromDlg

public Model createFromFile() 
 { 
  NomadicModel nomadicModel=new NomadicModel();
  //Setto i parametri il modello
  nomadicModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		               configParser.getAntennaRadius(),configParser.getAlpha(),
		               configParser.getVMin(),configParser.getVMax(),
		               configParser.getPauseTime(),configParser.getPhysicalProp());
  //Ritorno il modello
  return nomadicModel; 
 }//Fine cretaeModelFromFile

 public void setConfigDlgFromFile() 
  { 
   configDlg.setNumNodes(configParser.getNumNodes()); 
   configDlg.setNodeRadius(configParser.getNodeRadius());
   configDlg.setAntennaRadius(configParser.getAntennaRadius()); 
   configDlg.setPhysical(configParser.getPhysicalProp());
   configDlg.setVMin(configParser.getVMin());
   configDlg.setVMax(configParser.getVMax());
   configDlg.setAlpha(configParser.getAlpha());
   configDlg.setPauseTime(configParser.getPauseTime());
  }//Fine setConfigDlgFromFile
}//Fine classe NomadicBuilder