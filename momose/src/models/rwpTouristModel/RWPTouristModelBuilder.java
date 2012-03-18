package models.rwpTouristModel;


import models.Model;

import models.simpleModelStructures.SimpleModelBuilder;

public class RWPTouristModelBuilder extends SimpleModelBuilder
 {
  public RWPTouristModelBuilder(String name)
	{ super(name); }	  
 
 public Model createFromDlg() 
  {
   RWPTouristModel rwpTouristModel=new RWPTouristModel();
   //Setto i parametri il modello
   rwpTouristModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                        configDlg.getAntennaRadius(),configDlg.getPauseTime(),
		                        configDlg.getVMin(),configDlg.getVMax(),
		                        configDlg.isPhysical());
  //Ritorno il modello
  return rwpTouristModel; 
 }//Fine createModelFromDlg

 public Model createFromFile() 
  { 
	RWPTouristModel rwpTouristModel=new RWPTouristModel();
    //Setto i parametri il modello
	rwpTouristModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		                       configParser.getAntennaRadius(),configParser.getPauseTime(),
		                       configParser.getVMin(),configParser.getVMax(),
		                       configParser.getPhysicalProp());
    //Ritorno il modello
    return rwpTouristModel; 
  }//Fine cretaeModelFromFile


}//Fine classe 