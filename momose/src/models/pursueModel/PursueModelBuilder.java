package models.pursueModel;

import gui.ConfigDlg;
import models.Model;
import models.ModelBuilder;

import org.xml.sax.helpers.DefaultHandler;

public class PursueModelBuilder extends ModelBuilder
{
 private PursueConfigDlg configDlg;
 private PursueParser configParser;	
	
 
  public PursueModelBuilder(String name)
	{
	 super(name);  
	 //Creo la finestra di configurazione
	 configDlg=new PursueConfigDlg();
	 //Creo il parser
	 configParser=new PursueParser();
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
                     +"<targetRadius>"+configDlg.getTargetRadius()+"</targetRadius>\n"
                     +"<isPhy>"+configDlg.isPhysical()+"</isPhy>\n");
                    
 }//Fine toXml 

public Model createFromDlg() 
 {
  PursueModel pursueModel=new PursueModel();
  //Setto i parametri il modello
  pursueModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                    configDlg.getAntennaRadius(),configDlg.getAlpha(),
		                    configDlg.getVMin(),configDlg.getVMax(),
		                    configDlg.getTargetRadius(),configDlg.isPhysical());
  //Ritorno il modello
  return pursueModel; 
 }//Fine createModelFromDlg

public Model createFromFile() 
 { 
  PursueModel pursueModel=new PursueModel();
  //Setto i parametri il modello
  pursueModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		               configParser.getAntennaRadius(),configParser.getAlpha(),
		               configParser.getVMin(),configParser.getVMax(),
		               configParser.getTargetRadius(),configParser.getPhysicalProp());
  //Ritorno il modello
  return pursueModel; 
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
   configDlg.setTargetRadius(configParser.getTargetRadius());
  }//Fine setConfigDlgFromFile
}//Fine classe PursueBuilder
