package models.ecraModel;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import models.Model;
import models.ModelBuilder;


public class EcraModelBuilder extends ModelBuilder
 {
  EcraModelConfigDlg configDlg;
  EcraModelParser configParser;	
  public EcraModelBuilder(String modelName) 
   {
	super(modelName);
    //Creo la finestra di configurazione
	configDlg=new EcraModelConfigDlg();
	//Creo il parser
	configParser=new EcraModelParser();
    
   }//Fine EraModelBuilder

  public ConfigDlg getConfigDlg()
   { return configDlg; }
 
  public DefaultHandler getConfigParser() 
   {return configParser;}
 
  public Model createFromDlg() 
   {
	 EcraModel ecraModel=new EcraModel();
	 ecraModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                configDlg.getAntennaRadius(),configDlg.isPhysical(),
		                configDlg.getSpinTargetAttr(),configDlg.getColoringInterval(),
		                configDlg.getVMin(),configDlg.getVMax(),configDlg.getSpinTargetAttr(),
		                configDlg.getRoles(),configDlg.getTargetZones(),
		                configDlg.getMobileRefPoint());
		                
	return ecraModel;
   }//Fine createFromDlg

	
  public Model createFromFile() 
   {
	EcraModel ecraModel=new EcraModel();  
	ecraModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
			           configParser.getAntennaRadius(),configParser.getPhysicalProp(),
			           configParser.getTargetAttr(),configParser.getColInterval(),
			           configParser.getVMin(),configParser.getVMax(),configParser.getTargetAttr(),
			           configParser.getRoles(),configParser.getTargetAreas(),
			           configParser.getMobileRefPoint());
	return ecraModel;
   }//Fine cretaeFromParser

   public String toXml() 
    {
	 return new String("<nn>"+configDlg.getNumNodes()+"</nn>\n"
            +"<nr>"+configDlg.getNodeRadius()+"</nr>\n"
            +"<ar>"+configDlg.getAntennaRadius()+"</ar>\n"
            +"<vMin>"+configDlg.getVMin()+"</vMin>\n"
            +"<vMax>"+configDlg.getVMax()+"</vMax>\n"
            +"<colInterval>"+configDlg.getColoringInterval()+"</colInterval>\n"
            +"<targetAttr>"+configDlg.getSpinTargetAttr()+"</targetAttr>\n"
            +"<isPhy>"+configDlg.isPhysical()+"</isPhy>\n"
	        +"<roles>"+configDlg.getRoles()+"</roles>\n"
	        +"<targetAreas>"+configDlg.getTargetZones()+"</targetAreas>\n"
	        +"<mobRefPoint>"+configDlg.getMobileRefPoint()+"</mobRefPoint>");
     }//Fine toXml

 public void setConfigDlgFromFile() 
  {
   configDlg.setNumNodes(configParser.getNumNodes()); 
   configDlg.setNodeRadius(configParser.getNodeRadius());
   configDlg.setAntennaRadius(configParser.getAntennaRadius()); 
   configDlg.setPhysical(configParser.getPhysicalProp());
   configDlg.setVMin(configParser.getVMin());
   configDlg.setVMax(configParser.getVMax());
   configDlg.setTargetAttr(configParser.getTargetAttr());
   configDlg.setColoringInterval(configParser.getColInterval());
   configDlg.setRoles(configParser.getRoles());
   configDlg.setTargetZones(configParser.getTargetAreas());
   configDlg.setMobileRefPoint(configParser.getMobileRefPoint());
  }//Fine setConfigDlgFromFile
}//Fine classe EraModelBuidler
