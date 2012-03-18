package models.simpleModelStructures;

import gui.ConfigDlg;
import models.ModelBuilder;


import org.xml.sax.helpers.DefaultHandler;

public abstract class SimpleModelBuilder extends ModelBuilder 
 {
  protected SimpleModelConfigDlg configDlg;
  protected SimpleModelParser configParser;
 
  public SimpleModelBuilder(String name)
   {
    super(name);  
    //Creo la finestra di configurazione
    configDlg=new SimpleModelConfigDlg(name);
    //Creo il parser
    configParser=new SimpleModelParser();
   }//Fine costruttore

  public ConfigDlg getConfigDlg()
   { return configDlg; }

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
}//Fine SimpleModelBuilder