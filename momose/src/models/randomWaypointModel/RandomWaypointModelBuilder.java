package models.randomWaypointModel;


import models.Model;

import models.simpleModelStructures.SimpleModelBuilder;

public class RandomWaypointModelBuilder extends SimpleModelBuilder
 {
  public RandomWaypointModelBuilder(String name)
	{ super(name); }	  
 
 public Model createFromDlg() 
  {
   RandomWaypointModel randomWaypointModel=new RandomWaypointModel();
   //Setto i parametri il modello
   randomWaypointModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                        configDlg.getAntennaRadius(),configDlg.getPauseTime(),
		                        configDlg.getVMin(),configDlg.getVMax(),
		                        configDlg.isPhysical());
  //Ritorno il modello
  return randomWaypointModel; 
 }//Fine createModelFromDlg

 public Model createFromFile() 
  { 
	RandomWaypointModel randomWaypointModel=new RandomWaypointModel();
    //Setto i parametri il modello
    randomWaypointModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		                       configParser.getAntennaRadius(),configParser.getPauseTime(),
		                       configParser.getVMin(),configParser.getVMax(),
		                       configParser.getPhysicalProp());
    //Ritorno il modello
    return randomWaypointModel; 
  }//Fine cretaeModelFromFile


}//Fine classe RandomWaypointModelBuilder