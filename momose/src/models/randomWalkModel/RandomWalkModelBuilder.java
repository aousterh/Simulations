package models.randomWalkModel;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import models.Model;
import models.simpleModelStructures.SimpleModelBuilder;
import models.simpleModelStructures.SimpleModelConfigDlg;
import models.simpleModelStructures.SimpleModelParser;


public class RandomWalkModelBuilder extends SimpleModelBuilder 
 {
  public RandomWalkModelBuilder(String name)
   { super(name); }
 
 public Model createFromDlg() 
  {
   RandomWalkModel randomWalkModel=new RandomWalkModel();
   //Setto i parametri il modello
   randomWalkModel.setModel(configDlg.getNumNodes(),configDlg.getNodeRadius(),
		                    configDlg.getAntennaRadius(),configDlg.getPauseTime(),
		                    configDlg.getVMin(),configDlg.getVMax(),
		                    configDlg.isPhysical());
   //Ritorno il modello
   return randomWalkModel; 
  }//Fine createModelFromDlg

 public Model createFromFile() 
  { 
   RandomWalkModel randomWalkModel=new RandomWalkModel();
   //Setto i parametri il modello
   randomWalkModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
		                    configParser.getAntennaRadius(),configParser.getPauseTime(),
		                    configParser.getVMin(),configParser.getVMax(),
		                    configParser.getPhysicalProp());
   //Ritorno il modello
   return randomWalkModel; 
  }//Fine cretaeModelFromFile

}//Fine RandomWalkModelBuilder
