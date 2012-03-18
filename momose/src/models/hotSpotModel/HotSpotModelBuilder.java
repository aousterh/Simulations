package models.hotSpotModel;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import models.Model;
import models.ModelBuilder;


public class HotSpotModelBuilder extends ModelBuilder
 {
  private HotSpotConfigDlg configDlg;
  private HotSpotParser configParser;	
	
  
   public HotSpotModelBuilder(String name)
	{
	 super(name);  
	 //Creo la finestra di configurazione
	 configDlg=new HotSpotConfigDlg();
	 //Creo il parser
	 configParser=new HotSpotParser();
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
                      +"<pauseTime>"+configDlg.getPauseTime()+"</pauseTime>\n"
                      +"<isPhy>"+configDlg.isPhysical()+"</isPhy>\n");
                     
  }//Fine toXml 

 public Model createFromDlg() 
  {
   HotSpotModel hotSpotModel=new HotSpotModel();
   //Setto i parametri il modello
   hotSpotModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                    configDlg.getAntennaRadius(),configDlg.getPauseTime(),
		                    configDlg.getVMin(),configDlg.getVMax(),
		                    configDlg.isPhysical());
   //Ritorno il modello
   return hotSpotModel; 
  }//Fine createModelFromDlg

 public Model createFromFile() 
  { 
   HotSpotModel hotSpotModel=new HotSpotModel();
   //Setto i parametri il modello
   hotSpotModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		                    configParser.getAntennaRadius(),configParser.getPauseTime(),
		                    configParser.getVMin(),configParser.getVMax(),
		                    configParser.getPhysicalProp());
   //Ritorno il modello
   return hotSpotModel; 
  }//Fine cretaeModelFromFile

  public void setConfigDlgFromFile() 
   { 
    configDlg.setNumNodes(configParser.getNumNodes()); 
	configDlg.setNodeRadius(configParser.getNodeRadius());
	configDlg.setAntennaRadius(configParser.getAntennaRadius()); 
	configDlg.setPhysical(configParser.getPhysicalProp());
	configDlg.setVMin(configParser.getVMin());
	configDlg.setVMax(configParser.getVMax());
	configDlg.setPauseTime(configParser.getPauseTime());
   }//Fine setConfigDlgFromFile
 }//Fine classe HotSpotBuilder
