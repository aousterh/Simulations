package models.MessageModel;

import gui.ConfigDlg;
import models.ModelBuilder;


import org.xml.sax.helpers.DefaultHandler;

public abstract class MessageModelBuilder extends ModelBuilder 
 {
  protected MessageModelConfigDlg configDlg;
  protected MessageModelParser configParser;
 
  public MessageModelBuilder(String name)
   {
    super(name);  
    //Create the configuration window
    configDlg=new MessageModelConfigDlg(name);
    //Create the parser
    configParser=new MessageModelParser();
   }//End constructor

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
}//Fine MessageModelBuilder