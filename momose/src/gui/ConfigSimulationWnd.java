package gui;

import engine.Scenario;
import engine.SimTime;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Builder;
import core.SimSaver;
import core.SimulationManager;

import utils.MomoseFileFilter;
import utils.Preferences;
import utils.Utils;

public class ConfigSimulationWnd extends JInternalFrame implements ActionListener,
                                                                   ChangeListener
{ 
 public static int MODELS=0;
 public static int RECORDERS=1;	
	
 public static int EMPTY_SCENARIO=0;
 public static int LOAD_SCENARIO_FILE=1;
 public static int LOAD_SCENARIO_CONFIG=2;	
 
 
 public static int SCENARIO_BOUNDED=0;
 public static int SCENARIO_BOUNDLESS=1;
 
 private Container cp;//ContentPane della finestra
 
 private JButton closeButton; 
  
 private SimulationManager simManager;
 
 private int simID;
 
 private Vector modelsBuilders;
 private Vector recordersBuilders;
 
 ///Elementi dell'interfaccia di configurazione
 //Time panel
 private JLabel durationLabel;
 private JLabel stepLabel;
 private JLabel loopLabel;
 private JSpinner spinnerT;
 private JSpinner spinnerDT;
 private JSpinner spinnerLOOPS;
 private JLabel timeLabel;
 ///
 
 //Scenario panel
 JRadioButton fileScenarioRadio;
 JRadioButton emptyScenarioRadio;
 JRadioButton configScenarioRadio;
 
 private JFileChooser scenarioFC;
 private JButton browseScenarioButton;
 private JTextField scenarioPath;
 private JLabel loadScenarioLabel;
 
 
 JRadioButton boundedRadio;
 JRadioButton boundlessRadio;
 private JSpinner spinnerWidth;
 private JSpinner spinnerHeight;
 private JLabel borderLabel;
 private JLabel widthLabel;
 private JLabel heightLabel;
 
 private boolean  newSimulation;
 private Scenario scenarioCfg;
 
 ///
 
 //Models panel
 private JLabel selectModelLabel;
 private CheckboxList modelsList;
 private JButton showConfigModel;
 
 //Data-recorders panel
 private JLabel selectRecorderLabel;
 private CheckboxList recordersList;
 private JButton showConfigRecorder;
 
 //Actions panel
 private JButton runSimulation; 
 private int simCount;
 private JButton saveConfigFile;
 
 
 
 public ConfigSimulationWnd(SimulationManager manager,int simID,boolean newSimulation,
		                     Vector modelsBuilders,Vector recordersBuilders)
  {
   //Prendo il contentpane  
   cp=this.getContentPane();
   //Setto le dimensioni della finestra
   setSize(650,570);
   setTitle("Config simulation #"+simID);
   //Può essere ridimensionata
   setResizable(true);
   //Si puo' mettere a icona
   setIconifiable(true);
   
   this.modelsBuilders=modelsBuilders;
   this.recordersBuilders=recordersBuilders;
   
   
   this.simManager=manager;
   this.simID=simID;
   this.newSimulation=newSimulation;
   scenarioCfg=null;
		
	//Preparo i due pannelli principali
	cp.setLayout(new BorderLayout());
	cp.add(createClosePanel(),BorderLayout.SOUTH);
	cp.add(createClientPanel(),BorderLayout.CENTER);
   }//Fine costruttore
 
 
 public void setTimeData(SimTime time)
  {
   spinnerT.setValue((double)time.getT());	
   spinnerDT.setValue((double)time.getDT());
   spinnerLOOPS.setValue((int)time.getN());	
  }//Fine setTimeData
 
 public void setScenarioCfg(Scenario scenarioCfg)
  {this.scenarioCfg=scenarioCfg;}
 
 public float getT()
  {return (float)(((Double)spinnerT.getValue()).doubleValue());}
 
 public float getDT()
  {return (float)(((Double)spinnerDT.getValue()).doubleValue());}
 
 public int getLOOPS()
  {return (int)(((Integer)spinnerLOOPS.getValue()).intValue());}
 
 public int getScenarioLoadType()
  {
   if(emptyScenarioRadio.isSelected())
	return EMPTY_SCENARIO;
   else
    {   
	 if(fileScenarioRadio.isSelected())  
	  return LOAD_SCENARIO_FILE;
	 else
	  return LOAD_SCENARIO_CONFIG; 	 
    }
  }//Fine ScenariioLoadType
 
 public int getScenarioBorderType()
  {
   if(boundedRadio.isSelected())
	return SCENARIO_BOUNDED;
   else
	return SCENARIO_BOUNDLESS;  
 }//Fine ScenariioLoadType
 
 public float getScenarioWidth()
  {return (float)(((Double)spinnerWidth.getValue()).doubleValue());} 
 
 public float getScenarioHeight()
  {return (float)(((Double)spinnerHeight.getValue()).doubleValue());}  
 
 public String getScenarioPath()
  {return scenarioPath.getText();}
 
 public Scenario getScenarioCfg()
  {return scenarioCfg;}
 
 public Vector getModBuilders() 
  { return modelsBuilders; } 

 public Vector getRecBuilders() 
  { return recordersBuilders; }
 
 public Vector getModBuildNamesSel() 
  { return modelsList.getNameOfCheckBoxSelected(); } 
 
 public Vector getRecBuildNamesSel() 
  { return recordersList.getNameOfCheckBoxSelected(); }
 
 public Vector getModBuildSel() 
  {
   Vector modNames=getModBuildNamesSel();  	 
   return getBuildersFromNames(modelsBuilders,modNames);
  }//Fine getModBuildeSel

 public Vector getRecBuildSel() 
  {
   Vector recNames=getRecBuildNamesSel();	
   return getBuildersFromNames(recordersBuilders,recNames);
  }//Fine getRecBuildSel
 
 
 public int getSimID()
  { return simID; }
 
 public JPanel createClosePanel()
  {
   //Creo il pannello dei bottoni ok e canc	 
   JPanel okCancPanel=new JPanel();
   okCancPanel.setLayout(new FlowLayout());
   cp.add(okCancPanel,BorderLayout.SOUTH);
	
   //Aggiungo il bottone per chiudere la finestra
   closeButton=new JButton("Close");
   //closeButton.setIcon(Utils.getIcon("close.png"));
   closeButton.addActionListener(this);
   okCancPanel.add(closeButton);
   return okCancPanel; 
 }//Fine createPanel

public JPanel createClientPanel()
 {
  JPanel clientPanel=new JPanel();
  clientPanel.setLayout(new FlowLayout());
  
  //Pannello del tempo
  clientPanel.add(createTimePanel());
  //Pannello dello scenario
  clientPanel.add(createScenarioPanel());
  //Pannello dei modelli
  clientPanel.add(createModelsPanel());
  //Pannello dei data-recorders
  clientPanel.add(createRecordersPanel());
  //Pannello delle azioni
  clientPanel.add(createActionsPanel());
  
  return clientPanel;  
 }//Fine cretaeClientPanel

 private JPanel createActionsPanel() 
  {
   JPanel actionsPanel=new JPanel();
   actionsPanel.setLayout(new FlowLayout());
   actionsPanel.setSize(600,50); 
   
   runSimulation=new JButton("Run simulation");
   //runSimulation.setIcon(Utils.getIcon("runsim.png"));
   runSimulation.addActionListener(this);
   actionsPanel.add(runSimulation,BorderLayout.WEST);
   simCount=0;
   
   saveConfigFile=new JButton("Save in config-files");
   //saveConfigFile.setIcon(Utils.getIcon("savesim.png"));
   saveConfigFile.addActionListener(this);
   actionsPanel.add(saveConfigFile,BorderLayout.EAST);
   
   return actionsPanel;
  }//Fine createActionPanel

private JPanel createModelsPanel() 
  {
   JPanel modelsPanel=new JPanel();
   modelsPanel.setLayout(new BorderLayout());
   modelsPanel.setSize(325,300); 
   modelsPanel.setBorder(BorderFactory.createTitledBorder("Models settings"));
   
   selectModelLabel=new JLabel("Select models:");
   modelsPanel.add(selectModelLabel,BorderLayout.NORTH);
   //Creo la lista dei modelli disponibili
   modelsList=new CheckboxList();
   modelsList.setListData(createArrayOfCheckBox(modelsBuilders)); 
   JScrollPane listScroller=new JScrollPane(modelsList);
   listScroller.setPreferredSize(new Dimension(300,100));
   modelsPanel.add(listScroller,BorderLayout.CENTER);
   //Creo il bottone per aprire la finestra di configurazione
   
   showConfigModel=new JButton("Show Model Config dialog");
   showConfigModel.setSize(100,50);
   showConfigModel.addActionListener(this);
   modelsPanel.add(showConfigModel,BorderLayout.SOUTH);
   return modelsPanel;
  }//Fine createModelsPanel 
 
 private JPanel createRecordersPanel() 
 {
  JPanel recordersPanel=new JPanel();
  recordersPanel.setLayout(new BorderLayout());
  recordersPanel.setSize(325,300); 
  recordersPanel.setBorder(BorderFactory.createTitledBorder("Data-recorders settings"));
  
  selectRecorderLabel=new JLabel("Select data-recorders:");
  recordersPanel.add(selectRecorderLabel,BorderLayout.NORTH);
  //Creo la lista dei data-recorders disponibili
  recordersList=new CheckboxList();
  recordersList.setListData(createArrayOfCheckBox(recordersBuilders));
  JScrollPane listScroller=new JScrollPane(recordersList);
  listScroller.setPreferredSize(new Dimension(300,100));
  recordersPanel.add(listScroller,BorderLayout.CENTER);
  //Creo il bottone per aprire la finestra di configurazione
  showConfigRecorder=new JButton("Show Data-Recorder Config dialog");
  showConfigRecorder.addActionListener(this);
  recordersPanel.add(showConfigRecorder,BorderLayout.SOUTH);
  
  return recordersPanel;
 }//Fine createRecordersPanel
 
 private JPanel createScenarioPanel() 
  {
   JPanel scenarioPanel=new JPanel();
   scenarioPanel.setLayout(new BoxLayout(scenarioPanel,BoxLayout.Y_AXIS));
   scenarioPanel.setSize(650,300);
   scenarioPanel.setBorder(BorderFactory.createTitledBorder("Scenario settings"));
   
   //SEZIONE CONFIG SCENARIO
   configScenarioRadio=new JRadioButton("Load from config file");
   if(newSimulation==false)
    {  
     //pannello config scenario
	 JPanel radioConfigPanel=new JPanel(new BorderLayout());
	 scenarioPanel.add(radioConfigPanel,BorderLayout.WEST);
	 configScenarioRadio.setSelected(true);
	 configScenarioRadio.addActionListener(this);
	 radioConfigPanel.add(configScenarioRadio);
    }
   
   ////SEZIONE EMPTY SCENARIO
   //pannello empty scenario
   JPanel radioEmptyPanel=new JPanel(new BorderLayout());
   scenarioPanel.add(radioEmptyPanel,BorderLayout.WEST);
   emptyScenarioRadio=new JRadioButton("Empty scenario");
   emptyScenarioRadio.setSelected(newSimulation);
   emptyScenarioRadio.addActionListener(this);
   radioEmptyPanel.add(emptyScenarioRadio);
   
   //Pannello dimensioni scenario
   JPanel dimensionPanel=new JPanel(new FlowLayout());
   scenarioPanel.add(dimensionPanel);
   widthLabel=new JLabel("Width:");
   dimensionPanel.add(widthLabel);
   SpinnerNumberModel modelWidth=new SpinnerNumberModel(100.0,10.0,100.0,1.0);
   modelWidth.setMaximum(null);
   spinnerWidth=new JSpinner(modelWidth);
   ((JSpinner.DefaultEditor)spinnerWidth.getEditor()).getTextField().setColumns(4);
   spinnerWidth.addChangeListener(this);
   dimensionPanel.add(spinnerWidth);
   heightLabel=new JLabel("  Height:");
   dimensionPanel.add(heightLabel);
   SpinnerNumberModel modelHeight=new SpinnerNumberModel(100.0,10.0,100.0,1.0);
   modelHeight.setMaximum(null);
   spinnerHeight=new JSpinner(modelHeight);
   ((JSpinner.DefaultEditor)spinnerHeight.getEditor()).getTextField().setColumns(4);
   spinnerHeight.addChangeListener(this);
   dimensionPanel.add(spinnerHeight);
   
   
   //Pannello bordo
   JPanel borderPanel=new JPanel(new FlowLayout());
   scenarioPanel.add(borderPanel);
   borderLabel=new JLabel("Border type:");
   borderPanel.add(borderLabel);
   boundedRadio=new JRadioButton("Bounded");
   boundedRadio.setSelected(true);
   boundedRadio.addActionListener(this);
   borderPanel.add(boundedRadio);
   boundlessRadio=new JRadioButton("Boundless");
   boundlessRadio.setSelected(false);
   boundlessRadio.addActionListener(this);
   borderPanel.add(boundlessRadio);
   
   
   
   
   ////SEZIONE LOAD SCENARIO
   //Radio browse
   JPanel radioBrowsePanel=new JPanel(new BorderLayout());
   scenarioPanel.add(radioBrowsePanel,BorderLayout.WEST);
   fileScenarioRadio=new JRadioButton("Load from file");
   fileScenarioRadio.setSelected(false);
   fileScenarioRadio.addActionListener(this);
   radioBrowsePanel.add(fileScenarioRadio);
   
   //Pannello load scenario file
   JPanel browseFilePanel=new JPanel(new FlowLayout());
   scenarioPanel.add(browseFilePanel);
   loadScenarioLabel=new JLabel("Scenario filename:");
   browseFilePanel.add(loadScenarioLabel);
   scenarioPath=new JTextField(30);
   browseFilePanel.add(scenarioPath);
   browseScenarioButton=new JButton("Browse");
   //browseScenarioButton.setIcon(Utils.getIcon("browse.png"));
   browseScenarioButton.addActionListener(this);
   browseFilePanel.add(browseScenarioButton);
   //Creo la finestra per scegliere il file dello scenario
   
   scenarioFC=new JFileChooser();
   //Setto il filtro
   scenarioFC.setFileFilter(new MomoseFileFilter("scenario"));
   ////
   
   //Creo i gruppi dei bottoni
   ButtonGroup scenarioModGroup=new ButtonGroup();
   scenarioModGroup.add(fileScenarioRadio);
   scenarioModGroup.add(emptyScenarioRadio);
   if(newSimulation==false)
    scenarioModGroup.add(configScenarioRadio);
   
   ButtonGroup borderModGroup=new ButtonGroup();
   borderModGroup.add(boundedRadio);
   borderModGroup.add(boundlessRadio);
   
   //Abilito/disabilito i componenti
   changeScenarioOptions(newSimulation,false);
   
    
   //Ritorno il pannello
   return scenarioPanel; 	
  }//Fine createScenarioPanel

private JPanel createTimePanel() 
  {
   //Creo il pannello dei tempi	
   JPanel timePanel=new JPanel();
   timePanel.setLayout(new BoxLayout(timePanel,BoxLayout.Y_AXIS));
   timePanel.setSize(500,100);
   timePanel.setBorder(BorderFactory.createTitledBorder("Time settings"));
   
   //Sotto-pannello degli spinner
   JPanel spinnersPanel=new JPanel(new FlowLayout());
   timePanel.add(spinnersPanel);
   
   //Tempo totale
   durationLabel=new JLabel("Simulation duration (s):");
   spinnersPanel.add(durationLabel);
   SpinnerNumberModel modelTotal=new SpinnerNumberModel(60.0,1.0,3600.0,1.0);
   modelTotal.setMaximum(null);
   spinnerT=new JSpinner(modelTotal);
   ((JSpinner.DefaultEditor)spinnerT.getEditor()).getTextField().setColumns(4);
   spinnerT.addChangeListener(this);
   spinnersPanel.add(spinnerT);
   
   //Step
   stepLabel=new JLabel("Step time (s):");
   spinnersPanel.add(stepLabel);
   SpinnerNumberModel modelDT=new SpinnerNumberModel(1.0,0.01,60.0,0.01);
   modelDT.setMaximum(null);
   modelDT.setValue(new Double(1f));
   spinnerDT=new JSpinner(modelDT);
   ((JSpinner.DefaultEditor)spinnerDT.getEditor()).getTextField().setColumns(5);
   spinnerDT.addChangeListener(this);
   spinnersPanel.add(spinnerDT);
   
   //Cicli
   loopLabel=new JLabel("Simulation iterations:");
   spinnersPanel.add(loopLabel);
   SpinnerNumberModel modelLOOPS=new SpinnerNumberModel(60,1,3600,1);
   modelLOOPS.setMaximum(null);
   spinnerLOOPS=new JSpinner(modelLOOPS);
   ((JSpinner.DefaultEditor)spinnerLOOPS.getEditor()).getTextField().setColumns(5);
   spinnerLOOPS.addChangeListener(this);
   spinnersPanel.add(spinnerLOOPS);
   
   //Indicazione del tempo
   JPanel timeLabelPanel=new JPanel(new FlowLayout());
   timeLabel=new JLabel("Simulation time: "+Utils.getTimeString(getT()));
   timeLabelPanel.add(timeLabel);
   timePanel.add(timeLabelPanel);
   
   return timePanel;
  }//Fine createTimePanel

 public void actionPerformed(ActionEvent e) 
  {
   //OkCancPanel	 
   if(e.getSource()==closeButton)
    {dispose();}   

   if(e.getSource()==runSimulation)
    {
	 simCount++;
	 simManager.loadSimFromWnd(this); 
    }
   
   if(e.getSource()==saveConfigFile)
    { saveCfgFile(); }
   
   //Scenario panel
   if(e.getSource()==browseScenarioButton)
   {
	//Prendo la directory degli scenari   
	Preferences pref=Utils.getPreferences(Momose.PREF_FILE);	
    //Setto la directory corrente
	scenarioFC.setCurrentDirectory(new File(pref.getScenariDir()));
	//Apro la finestra di scelta
    int returnVal=scenarioFC.showOpenDialog(this);
    if(returnVal==JFileChooser.APPROVE_OPTION)
     {
      File file=scenarioFC.getSelectedFile();
	  scenarioPath.setText(file.getPath());
     } 	
    } 
   
   if(e.getSource()==configScenarioRadio)
    {changeScenarioOptions(false,false);} 
   
   if(e.getSource()==emptyScenarioRadio)
    {changeScenarioOptions(true,false);} 
  
  if(e.getSource()==fileScenarioRadio)
   {changeScenarioOptions(false,true);}   
  
   ///ModelsPanel
   if(e.getSource()==showConfigModel)
    {
	 String selected=modelsList.getSelectedItem(); 
	 if(selected!=null)
	  {
	   //Estraggo il builder selezionato	 
	   Builder modelBuilder=getBuilder(modelsBuilders,selected);
	   //Visualizzo la finestra di configurazione
	   showConfigDlg(modelBuilder);
	  }
	}
  
  //RecordersPanel 
  if(e.getSource()==showConfigRecorder)
   {
	 String selected=recordersList.getSelectedItem(); 
	 if(selected!=null)
	  {
	   //Estraggo il builìder selezionato	 
	   Builder modelBuilder=getBuilder(recordersBuilders,selected);
	   //Visualizzo la finestra di configurazione
	   showConfigDlg(modelBuilder);
	  }
	} 
 }//Fine actionPerformed
 
 
private void showConfigDlg(Builder builder)
  {
   ConfigDlg configDlg=builder.getConfigDlg();
     
   if(configDlg!=null)
	 {configDlg.setVisible(true);}
	else
     JOptionPane.showMessageDialog(this,"Config dialog not found!",
			                       "Warning",JOptionPane.WARNING_MESSAGE);	
  }//Fine showConfigDlg
 
 private void changeScenarioOptions(boolean flagEmpty,boolean flagLoad)
  {
   widthLabel.setEnabled(flagEmpty);
   heightLabel.setEnabled(flagEmpty);  
   borderLabel.setEnabled(flagEmpty);
   spinnerWidth.setEnabled(flagEmpty);  
   spinnerHeight.setEnabled(flagEmpty);
   boundedRadio.setEnabled(flagEmpty);
   boundlessRadio.setEnabled(flagEmpty);
   
   scenarioPath.setEnabled(flagLoad);  
   browseScenarioButton.setEnabled(flagLoad);
   loadScenarioLabel.setEnabled(flagLoad);  
  }//Fine changeScenarioOptions
 


 public void stateChanged(ChangeEvent e) 
  {
   //Time panel	 
   if(e.getSource()==spinnerT)
	 calcLOOPS();
   if(e.getSource()==spinnerDT)
	 calcLOOPS();
   if(e.getSource()==spinnerLOOPS)
	 calcDT();
  }//Fine stateChanged
 
 private void calcT()
  {
   float dt=getDT(); int N=getLOOPS();	 
   spinnerT.setValue((double)(dt*N));
   timeLabel.setText("Simulation time: "+Utils.getTimeString(getT()));  
  }//Fine calcT

 private void calcDT()
  {
   float T=getT(); int N=getLOOPS();
   float newDT=(T/N);
   //newDT=(float)(Math.round(newDT*100.0f)/100.0f);
   spinnerDT.setValue((double)newDT);
   timeLabel.setText("Simulation time: "+Utils.getTimeString(getT()));
  }//Fine calcDT

 private void calcLOOPS()
  {
   float T=getT(); float dt=getDT();
   int newN=(int)(T/dt);
   spinnerLOOPS.setValue(newN);	
   timeLabel.setText("Simulation time: "+Utils.getTimeString(getT()));
  }//Fine calcN
 
 

 private JCheckBox[] createArrayOfCheckBox(Vector vector)
  {
   JCheckBox arrayCheckBox[]=new JCheckBox[vector.size()];
   for(int i=0;i<vector.size();i++)
   {
	Builder nextBuilder=(Builder)vector.elementAt(i);
	arrayCheckBox[i]=new JCheckBox(nextBuilder.getName());
   }    
  return arrayCheckBox; 	 
 }//Fine createListOfCheckBox
 
 public boolean setBuildersChecked(Vector builders, int type)
  {
   Iterator i=builders.iterator();
   boolean opOk=true;
   while(i.hasNext())
    {
	 Builder nextBuilder=(Builder)i.next();
	 //Provo a selezionare la checkbox
	 if(type==MODELS)
	  opOk=modelsList.setChecked(nextBuilder.getName());
	 else
	  opOk=recordersList.setChecked(nextBuilder.getName());
	 
	 if(opOk==false)
	  return false;
	}    
   //Tutto ok
   return true; 
  }//Fine setModSelects

 private Vector getBuildersFromNames(Vector buildersSelected,Vector names)
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
    }    
  return builders;	 
 }//Fine createBuildersVector

private Builder getBuilder(Vector builders,String name)
 {
  String builderName;	
  for(int i=0;i<builders.size();i++)
	{
	 Builder nextBuilder=(Builder)builders.elementAt(i);
	 builderName=nextBuilder.getName();
	 if(builderName.equals(name))
	   return nextBuilder;	 
	}    	 
  return null;
 }//Fine getModelBuilder
 
 synchronized public void saveCfgFile() 
  {
   //Mostro la finestra di salvataggio
   JFileChooser fc=new JFileChooser(); 
   // Setto la directory di default
   Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
   //Setto la directory di default
   fc.setCurrentDirectory(new File(pref.getConfigDir()));
   //Setto il filtro
   fc.setFileFilter(new MomoseFileFilter("config"));
   //Mostro la finestra
   int returnVal=fc.showSaveDialog(this);
   if(returnVal==JFileChooser.APPROVE_OPTION)
	{
	 //Prelevo il nome del file su cui salvare i dati  
	 File file=fc.getSelectedFile();
	 //Chiamo il SimSaver per salvare il file selezionato
	 SimSaver.saveCnfFile(this,file); 
	} 	 
 }//Fine saveCnfFile
 
 public void enableComp(boolean flag)
  {
	 durationLabel.setEnabled(flag);
	 stepLabel.setEnabled(flag);
	 loopLabel.setEnabled(flag);
	 
	 spinnerT.setEnabled(flag);
	 spinnerDT.setEnabled(flag);
	 spinnerLOOPS.setEnabled(flag);
	 timeLabel.setEnabled(flag);
	 
	 
	 //Scenario panel
	 fileScenarioRadio.setEnabled(flag);
	 emptyScenarioRadio.setEnabled(flag);
	 configScenarioRadio.setEnabled(flag);
	 
	 scenarioFC.setEnabled(flag);
	 browseScenarioButton.setEnabled(flag);
	 scenarioPath.setEnabled(flag);
	 loadScenarioLabel.setEnabled(flag);
	 	 
	 boundedRadio.setEnabled(flag);
	 boundlessRadio.setEnabled(flag);
	 spinnerWidth.setEnabled(flag);
	 spinnerHeight.setEnabled(flag);
	 borderLabel.setEnabled(flag);
	 widthLabel.setEnabled(flag);
	 heightLabel.setEnabled(flag);
	 
	 //Models panel
	 selectModelLabel.setEnabled(flag);
	 modelsList.setEnabled(flag);
	 showConfigModel.setEnabled(flag);
	 
	 //Data-recorders panel
	 selectRecorderLabel.setEnabled(flag);
	 recordersList.setEnabled(flag);
	 showConfigRecorder.setEnabled(flag);
	 
	 //Actions panel
	 runSimulation.setEnabled(flag); 
	 saveConfigFile.setEnabled(flag);   	 
  }//Fine enableComp
 }//Fine classe ConfigSimulationDlg



