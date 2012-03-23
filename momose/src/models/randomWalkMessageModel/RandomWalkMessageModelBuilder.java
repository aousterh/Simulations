package models.randomWalkMessageModel;

import gui.ConfigDlg;

import org.xml.sax.helpers.DefaultHandler;

import models.messageModel.MessageModel;
import models.Model;
import models.simpleModelStructures.SimpleModelBuilder;
import models.simpleModelStructures.SimpleModelConfigDlg;
import models.simpleModelStructures.SimpleModelParser;


public class RandomWalkMessageModelBuilder extends SimpleModelBuilder 
{
    public RandomWalkMessageModelBuilder(String name)
    { super(name); }
    
    public Model createFromDlg() 
    {
        RandomWalkMessageModel randomWalkMessageModel = new RandomWalkMessageModel();
        //Setto i parametri il modello
        randomWalkMessageModel.setModel(configDlg.getNumNodes(), configDlg.getNodeRadius(),
                                        configDlg.getAntennaRadius(), configDlg.getPauseTime(),
                                        configDlg.getVMin(), configDlg.getVMax(),
                                        configDlg.isPhysical());
        //Ritorno il modello
        return randomWalkMessageModel; 
    }//Fine createModelFromDlg
    
    public Model createFromFile() 
    { 
        RandomWalkMessageModel randomWalkMessageModel=new RandomWalkMessageModel();
        //Setto i parametri il modello
        randomWalkMessageModel.setModel(configParser.getNumNodes(),configParser.getNodeRadius(),
                                 configParser.getAntennaRadius(),configParser.getPauseTime(),
                                 configParser.getVMin(),configParser.getVMax(),
                                 configParser.getPhysicalProp());
        //Ritorno il modello
        return randomWalkMessageModel; 
    }//Fine cretaeModelFromFile
    
}//Fine RandomWalkModelBuilder
