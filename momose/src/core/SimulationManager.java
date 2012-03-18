package core;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import parsers.ConfigSimParser;
import utils.MomoseFileFilter;
import utils.Preferences;
import utils.Utils;
import datarecorders.btgRecorder.BTGRecorderBuilder;
import datarecorders.debugRecorder.DebugRecorderBuilder;
import datarecorders.ecraRecorder.ECRARecorderBuilder;
import datarecorders.ns2MobilityRecorder.NS2MobilityRecorderBuilder;
import datarecorders.viewerRecorder.ViewerRecorderBuilder;
import datarecorders.scramRecorder.ScramRecorderBuilder;

import engine.Scenario;
import engine.SimTime;

import models.ecraModel.EcraModelBuilder;
import models.hotSpotModel.HotSpotModelBuilder;
import models.nomadicModel.NomadicModelBuilder;
import models.pursueModel.PursueModelBuilder;
import models.randomWalkModel.RandomWalkModelBuilder;
import models.randomWaypointModel.RandomWaypointModelBuilder;
import models.rwpTouristModel.RWPTouristModelBuilder;
import models.touristGroupModel.TouristGroupModelBuilder;

import gui.ConfigSimulationWnd;
import gui.Momose;
import gui.SimulationWnd;


public class SimulationManager
 {
  private static int FROM_WND=0;
  private static int FROM_FILE=2;
 
  private Momose moveWnd;
  private int wndOpen;
  private int simID;
    
  public SimulationManager(Momose moveWnd)
   {
 this.moveWnd=moveWnd;
 wndOpen=0;
 simID=0;
   }//Fine costruttore
  
  public Momose getMoveWnd()
   {return moveWnd;}
  
  private void incrCounters()
   { simID++; wndOpen++; }
  
  public void wndClosed()
   { wndOpen--; }
   
 synchronized public void newConfigSimWnd() 
  {
   incrCounters();  
   //Creo  la finestra di configurazione della simulazione  
   ConfigSimulationWnd configSimWnd=new ConfigSimulationWnd(this,simID,true,
                               getModelBuilders(),getRecorderBuilders());
   //Mostro la finestra
   showConfigWnd(configSimWnd);
  }//Fine showConfigSim
 
 
 
 private void showConfigWnd(ConfigSimulationWnd cfgSimWnd)
  {
   //Aggancio la dlg alla finestra padre
   moveWnd.getDesktopPane().add(cfgSimWnd);
   //Mostro la finestra
   cfgSimWnd.setVisible(true);
  }//Fine showConfigWnd
 
 public File showLoadFileDlg(String curDir,String type)
  {
  
   JFileChooser fc=new JFileChooser();
   //Setto la directory corrente
   fc.setCurrentDirectory(new File(curDir));
   //Setto il filtro
   fc.setFileFilter(new MomoseFileFilter(type));
   //Mostro la finestra
   int returnVal=fc.showOpenDialog(moveWnd);
   
   if(returnVal==JFileChooser.APPROVE_OPTION)
    return fc.getSelectedFile();
   else
 return null;  
  }//Fine showLoadFileDlg
 
 public void LoadSimFromFile()
  {
   //Mostro la finestra di caricamento
   Preferences pref=Utils.getPreferences(Momose.PREF_FILE);  
   File file=showLoadFileDlg(pref.getConfigDir(),"config");
   if(file!=null)
    {    
  //Creo e mostro la finestra di simulazione  
     SimulationWnd simWnd=createSimFromFile(file);
     if(simWnd!=null)
      {
       //Incremento il conteggio delle finestra aperte  
       incrCounters();
       //Mostro la finestra di simulazione
       showSimWnd(simWnd,simID);
      } 
    }     
  }//Fine loadSimFromFile
 
 public void loadSimFromWnd(ConfigSimulationWnd cfgWnd)
  {
   SimulationWnd simWnd=createSimFromWnd(cfgWnd);
   if(simWnd!=null)
    {
     //Disabilito i componenti   
  cfgWnd.enableComp(false);  
  //Incremento il numero di finestre aperte   
  wndOpen++;  
     //Mostro la finestra di simulazione
     showSimWnd(simWnd,cfgWnd.getSimID());
     
    }   
  }//Fine loadSimFromWnd
 
 private void showSimWnd(SimulationWnd simWnd,int simID)
  {
   //Sett l'ID della simualazione  
   simWnd.setSimID(simID);
   //Aggancio la dlg alla finestra padre
   moveWnd.getDesktopPane().add(simWnd);
   //Mostro la finestra
   simWnd.setVisible(true);  
  }//Fine showSimWnd
 
 private SimulationWnd createSimFromWnd(ConfigSimulationWnd cfgWnd)
  {
   //Creo il tempo
   SimTime simTime=new SimTime(cfgWnd.getT(),cfgWnd.getDT(),cfgWnd.getLOOPS());
   if(ctrlSimTime(simTime)==false)
    return null;
   
   //Creo lo scenario
   Scenario scenario=SimLoader.loadScenarioFromCnfWnd(cfgWnd);
   if(scenario==null)
  {
   JOptionPane.showMessageDialog(moveWnd,"Unable to open scenario file...\n" +
                                 "Loading aborted!",
                                 "Error",JOptionPane.ERROR_MESSAGE);    
   return null;
  }  
 
   //Prendo i modelli
   Vector modBuilders=prepareModBuilders(null,FROM_WND,cfgWnd.getModBuilders(),cfgWnd.getModBuildNamesSel());
   if(modBuilders==null)
  return null;
   
   //Prendo i recorders
   Vector recBuilders=prepareRecBuilders(null,FROM_WND,cfgWnd.getRecBuilders(),cfgWnd.getRecBuildNamesSel());
   if(recBuilders==null)
   return null;
   
   //Creo e ritorno la finestra di simulazione
   return new SimulationWnd(simTime,scenario,modBuilders,recBuilders,FROM_WND,this,cfgWnd);
  }//Fine createSimFromDlg
 
 private boolean ctrlSimTime(SimTime simTime) 
  {
   if((simTime.getN()<=0)||(simTime.getDT()<=0)||(simTime.getT()<=0))
    {
  JOptionPane.showMessageDialog(moveWnd,"Error in setup of simulation time...\n"+
                                   "Loading aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE);  
  return false;
    } 
 else
  return true;
  }//Fine ctrlSimTime

private SimulationWnd createSimFromFile(File cfgFile) 
  {
   //Leggo il file di configurazione  
   ConfigSimParser cfgParser=parseConfigFile(cfgFile);
   if(cfgParser==null)
 return null;
 
   //Prelevo il tempo
   SimTime simTime=getSimTimeFromParser(cfgParser);
   if(simTime==null)
 return null;
   //Controllo il formato del tempo
   if(ctrlSimTime(simTime)==false)
  return null;  
   
   //Prelevo lo scenario
   Scenario scenario=getScenarioFromParser(cfgParser);
   if(scenario==null)
  return null; 
      
   //Prendo i modelli
   Vector modBuilders=prepareModBuilders(cfgFile,FROM_FILE,getModelBuilders(),cfgParser.getModBuildNames());
   if(modBuilders==null)
  return null;
   
   //Prendo i recorders
   Vector recBuilders=prepareRecBuilders(cfgFile,FROM_FILE,getRecorderBuilders(),cfgParser.getRecBuildNames());
   if(recBuilders==null)
   return null;
   
   //Creo e ritorno la finestra di simulazione
   return new SimulationWnd(simTime,scenario,modBuilders,recBuilders,FROM_FILE,this,null);
  }//Fine createSimulation
 
 private Vector prepareModBuilders(File cfgFile,int loadType,Vector modelsBuilders,Vector modBuildersNames)
  {
   Vector modBuildGen=setupBuilders(cfgFile,loadType,modelsBuilders,modBuildersNames);
   if(modBuildGen==null)
     { 
      JOptionPane.showMessageDialog(moveWnd,"Error in setup of models builders...\n"+
                                    "Loading aborted!",
                                    "Error",JOptionPane.ERROR_MESSAGE);  
      return null;
     }
    return modBuildGen;
   }//Fine getModelsBuilders  
 
 private Vector prepareRecBuilders(File cfgFile,int loadType,Vector recsBuilders,Vector recBuildersNames)
  {
   Vector recBuilders=setupBuilders(cfgFile,loadType,recsBuilders,recBuildersNames);
   if(recBuilders==null)
    { 
     JOptionPane.showMessageDialog(moveWnd,"Error in setup of data-recordes builders...\n"+
                                   "Loading aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE); 
     return null;
    }  
   return recBuilders;
  }//Fine getRecBuilders
 
 private ConfigSimParser parseConfigFile(File cfgFile)
  {
   ConfigSimParser cnfSimParser=SimLoader.loadDataFromCnfFile(cfgFile);
   if(cnfSimParser==null)
 {  
  JOptionPane.showMessageDialog(moveWnd,"Error in parsing main config-file...\n" +
                                  "Loading aborted!",
                                "Error",JOptionPane.ERROR_MESSAGE);  
      return null;
    }
   else
    {
     if(cnfSimParser.isConfigFile())
      { return cnfSimParser; }
     else
      {
       JOptionPane.showMessageDialog(moveWnd,"The input file is not a config-file...\n"+
                                     "Loading aborted!" ,
                                     "Error",JOptionPane.ERROR_MESSAGE);
       return null;
      }
    } 
  }//Fine loadDataInParser 
 
 private SimTime getSimTimeFromParser(ConfigSimParser cnfSimParser)
  {
   SimTime simTime=cnfSimParser.getSimTime();
   if(simTime==null)
 {
  JOptionPane.showMessageDialog(moveWnd,"Error in parsing sim-time data...\n"+
                                        "Loading aborted!", 
                                        "Error",JOptionPane.ERROR_MESSAGE);  
   return null;
    }
   return simTime;
  }//Fine getSimTimeFormParser
 
 private Scenario getScenarioFromParser(ConfigSimParser cnfSimParser)
  {
   Scenario scenario=cnfSimParser.getScenario();
   if(scenario==null)
  {
   JOptionPane.showMessageDialog(moveWnd,"Error in parsing scenario data...\n" +
                                 "Loading aborted!",
                                         "Error",JOptionPane.ERROR_MESSAGE);  
   return null;
  } 
   return scenario;
  }//Fine getScenarioFromParser
 
 private Vector setupBuilders(File cfgFile,int loadType,Vector vecTarget,Vector modNames) 
  {
   //Creo i builder
   Vector builders=getBuildersVector(vecTarget,modNames);
   if(builders==null)
  return null;
   
   //Se carico da file configuro il builder 
   if(loadType==FROM_FILE)
    {  
     //Faccio il parsing dei file di configurazione dei builder dei modelli
     boolean opOk=SimLoader.loadBuildersConfigData(builders,cfgFile);
     if(opOk==false)
   return null;
    }  
   //ritorno i builder
   return builders;
  }//Fine createModels
 
 private Vector getBuildersVector(Vector buildersSelected,Vector names)
  {
   Vector builders=new Vector();
   Iterator i=names.iterator();
   while(i.hasNext())
    {
  //Estraggo il nome 
  String nextName=(String)i.next();  
  Builder builder=getBuilder(buildersSelected,nextName);
  //Se il builder esiste lo metto nel vettore
  if(builder!=null)
   {builders.add(builder);} 
  else
   {
       //Se non trovo il builder mostro messaggio di errore
    JOptionPane.showMessageDialog(moveWnd,nextName+" not found in current system...",
                              "Warning",JOptionPane.WARNING_MESSAGE);  
    return null;
  } 
 }    
   return builders;  
 }//Fine createBuildersVector
 
 private Builder getBuilder(Vector builders,String name)
  {
   String builderName=null; 
   for(int i=0;i<builders.size();i++)
 {
  Builder nextBuilder=(Builder)builders.elementAt(i);
  builderName=nextBuilder.getName();
  if(builderName.equals(name))
    return nextBuilder;
 }   
  return null;
 }//Fine getModelBuilder
 
 //////////////
 synchronized public void loadConfigSimWnd() 
  {
   //Mostro la finestra per aprire il file di configurazione
   Preferences pref=Utils.getPreferences(Momose.PREF_FILE);    
   File file=showLoadFileDlg(pref.getConfigDir(),"config");
   if(file!=null)
    {   
  ConfigSimulationWnd cfgWnd=createCfgWndFromFile(file);
  if(cfgWnd!=null)
   showConfigWnd(cfgWnd);
 }
  }//Fine loadConfigWnd
 
 private ConfigSimulationWnd createCfgWndFromFile(File cfgFile)
  {
   //Incremento i contatori  
   incrCounters();  
   //Creo  la finestra di configurazione della simulazione
   ConfigSimulationWnd cfgWnd=new ConfigSimulationWnd(this,simID,false,
                              getModelBuilders(),getRecorderBuilders());  
     
   //Leggo il file di configurazione  
   ConfigSimParser cfgParser=parseConfigFile(cfgFile);
   if(cfgParser==null)
 return null;
 
   //Prelevo il tempo
   SimTime simTime=getSimTimeFromParser(cfgParser);
   if(simTime!=null)
    {
  //Controllo la correttezza 
  if(ctrlSimTime(simTime)==false)
   return null;
  else
   cfgWnd.setTimeData(simTime);
    }
   else
 return null; 
   
   //Prelevo lo scenario
   Scenario scenario=getScenarioFromParser(cfgParser);
   if(scenario!=null)
 cfgWnd.setScenarioCfg(scenario);
   else
 return null; 
   
   //Setto i ModelBuilder
   Vector modBuilders=prepareModBuilders(cfgFile,FROM_FILE,cfgWnd.getModBuilders(),
                                      cfgParser.getModBuildNames());
   if(modBuilders==null)
   return null; 
   //Setto le finestre di dialogo
   setDlgData(modBuilders);
   //Seleziono i builder nella lista dei modelli della finestra
   cfgWnd.setBuildersChecked(modBuilders,ConfigSimulationWnd.MODELS);
   
   
   //Prendo i recorders
   Vector recBuilders=prepareRecBuilders(cfgFile,FROM_FILE,cfgWnd.getRecBuilders(),
                               cfgParser.getRecBuildNames());
   if(recBuilders==null)
   return null;
   
   //Setto le finestre di dialogo
   setDlgData(recBuilders);
   //Seleziono i builder nella lista dei modelli dei recorders 
   cfgWnd.setBuildersChecked(recBuilders,ConfigSimulationWnd.RECORDERS);
   
   //Ritorno la finestra di configurazione
   return cfgWnd;  
  }//Fine createCfgWndFromFile
 
 private void setDlgData(Vector builders)
  {
   Iterator i=builders.iterator();
   while(i.hasNext())
 {
  Builder nextBuilder=(Builder)i.next();
  //Setto la finestra di configurazione di questo builder
  nextBuilder.setConfigDlgFromFile(); 
 }        
   }//Fine setDlgData
 
////////////////////////////////////
  private Vector getModelBuilders()
   {
 Vector modelsBuilders=new Vector();  
 
 //Random Walk Model
 modelsBuilders.add(new RandomWalkModelBuilder("Random walk Model"));
 modelsBuilders.add(new RandomWaypointModelBuilder("Random waypoint Model")); 
 modelsBuilders.add(new EcraModelBuilder("ECRA Model")); 
 modelsBuilders.add(new HotSpotModelBuilder("Hot-Spot Model")); 
 modelsBuilders.add(new NomadicModelBuilder("Nomadic Model")); 
 modelsBuilders.add(new PursueModelBuilder("Pursue Model")); 
 modelsBuilders.add(new TouristGroupModelBuilder("Tourist group Model"));
 modelsBuilders.add(new RWPTouristModelBuilder("RWP tourist Model")); 
 //Insert here new ModelBuilder
 //modelsBuilder.add(new NewModelBuilder());
 
 return modelsBuilders;
   }
  
  private Vector getRecorderBuilders()
   {
 Vector recordersBuilders=new Vector();    
 
 //ViewerRecorder
    recordersBuilders.add(new ViewerRecorderBuilder("Viewer recorder"));
    recordersBuilders.add(new ScramRecorderBuilder("Scram recorder"));
    recordersBuilders.add(new DebugRecorderBuilder("Debug recorder"));
    recordersBuilders.add(new NS2MobilityRecorderBuilder("ns-2 recorder"));
    recordersBuilders.add(new BTGRecorderBuilder("Bluetooh graph recorder"));
    recordersBuilders.add(new ECRARecorderBuilder("ECRA recorder")); 
    //Insert here new DataRecorderBuilder
 //recoredersBuilder.add(new NewDataRecorderBuilder());  
    
    return recordersBuilders;
   }
 }//Fine simulation manager
