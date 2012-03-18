package gui;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.Builder;
import core.SimSaver;
import core.SimulationManager;

import datarecorders.DataRecorder;
import datarecorders.DataRecorderBuilder;
import datarecorders.realTimeRecorder.RealTimeRecorder;

import models.Model;
import models.ModelBuilder;

import utils.Preferences;
import utils.Utils;
import viewer.InternalFrameViewer;
import viewer.RealTimeViewer;
import viewer.Viewer;

import engine.Scenario;
import engine.SimThread;
import engine.SimTime;


public class SimulationWnd extends JInternalFrame implements ActionListener
 {
  static int FROM_WND=0;
  static int FROM_FILE=1;
	
  //Variabili di simulazione
  private SimThread sim;	
  private SimTime simTime;
  private Scenario scenario;
  private Vector modBuilders;
  private Vector recBuilders;
  
   
  //Variabili dell'interfaccia
  private Container cp;//ContentPane della finestra
  private JButton closeButton;
  private boolean fRun;
  
  //Tempo
  private JLabel simTimeLabel;
  private JLabel simTimeFormatLabel;
  private JLabel simLoopLabel;
  
  //Commands
  private JButton run;
  private JButton pause;
  private JButton stop;
  private JButton resetButt;
  private JButton showSim;
  
  //Log
  private JTextArea textLog;
  private JScrollPane scrollTextLog;
  private JCheckBox verboseMode;
  private JButton saveLog;
  private SimProgressBar progressBar;
  private long startTime;
  
  //Visualizzazione simulazione
  private RealTimeViewer rtViewer;
  private InternalFrameViewer internalViewer;
  private RealTimeRecorder rtRecorder;
   
  private int loadType;
  private SimulationManager simManager;
  private ConfigSimulationWnd cfgWnd;
	
  public SimulationWnd(SimTime simTime,Scenario scenario,
		               Vector modBuilders,Vector recBuilders,
		               int loadType,SimulationManager simManager,
		               ConfigSimulationWnd cfgWnd)
   {
	//Imposto i parametri di simulazione 
	sim=null;  
	this.simTime=simTime;
	this.scenario=scenario;
	this.modBuilders=modBuilders;
	this.recBuilders=recBuilders;
	
	this.simManager=simManager;
	this.cfgWnd=cfgWnd;
	
	//Setto i paramentri della finestra
	setWndParameter();
	
	//Setto i parametri della finestra di visualizzazione
	setRtViewer();
	
	//Creo l'interfaccia
	cp.add(getMainPanel());
	
	//Simulazione da finestra o caricata 
	this.loadType=loadType;
	
	//Resetto la simulazione
	resetSimulation();
  }//Fine costruttore
  
  public void setSimID(int simID)
   {
	setTitle("Simulation #"+simID);
	internalViewer.setTitle("Simulation #"+simID);
   }//Fine setSimID
  
  public void setWndParameter()
   {
    //Prendo il contentpane  
	cp=this.getContentPane();
	//Setto le dimensioni della finestra
	setSize(600,600);
	setIconifiable(true);
    setResizable(true);
	setTitle("Simulation #"); 
   }//Fine setWndParameter
  
  private void setRtViewer()
   {
	//Creo il viewer real time  
	rtViewer=new RealTimeViewer(1);
	rtViewer.setScenario(scenario);
	
	//Creo il recoder
	rtRecorder=new RealTimeRecorder(rtViewer);
	rtRecorder.setName("Real Time visualizer");
		
	//Creo la finestra del viewer
	createInternalViewer(rtViewer);
   }//Fine setrtViewer
  
  public void createInternalViewer(RealTimeViewer rtViewer)
   {
    //Creo la finestra
    internalViewer=new InternalFrameViewer(rtViewer);
	internalViewer.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	internalViewer.setSize(600,600);
	internalViewer.setResizable(true);
	internalViewer.setMaximizable(true);
	internalViewer.setIconifiable(true);
	internalViewer.setClosable(true);
		
	rtViewer.setOwnerWindow(internalViewer);
	//Aggancio alla finestra di move
	Momose moveWnd=simManager.getMoveWnd();
	moveWnd.getDesktopPane().add(internalViewer);  
   }//Fine cretaeInternalViewer
  
  public JPanel getMainPanel()
   {
    JPanel mainPanel=new JPanel(new BorderLayout());	
    mainPanel.add(createClosePanel(),BorderLayout.SOUTH);
    mainPanel.add(createClientPanel(),BorderLayout.CENTER);
    return mainPanel;
   }//Fine getMainPanel
  
 private Component createClosePanel() 
  {
   //Creo il pannello dei bottoni ok e canc	 
   JPanel closePanel=new JPanel();
   closePanel.setLayout(new FlowLayout());
   //Aggiungo i bottoni a questo pannello
   closeButton=new JButton("Close");
   closeButton.addActionListener(this);
   closePanel.add(closeButton);
   return closePanel; 
  }//Fine createClosePanel
 
 private Component createClientPanel() 
  {
   JPanel clientPanel=new JPanel();
   clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
  
   //Pannello GeneralInfo
   clientPanel.add(getGeneralInfoPanel());
   
   //Pannello tempo e scenario 
   JPanel timeScenPanel=new JPanel(new BorderLayout()); 
   clientPanel.add(timeScenPanel);
   //Creo il pannello con le info sul tempo
   timeScenPanel.add(getTimePanel(),BorderLayout.CENTER);
   //Creo il pannello con le info sullo scenario
   timeScenPanel.add(getScenarioPanel(),BorderLayout.EAST);
  
   //Pannello comandi
   clientPanel.add(getCommadsPanel());
   
   //Pannello log
   clientPanel.add(getLogPanel());
   
   //Ritorno il pannello	
   return clientPanel;
  }//Fine createClientPanel


private JPanel getTimePanel() 
 {
  //Creo il pannello dei tempi	
  JPanel timePanel=new JPanel();
  timePanel.setLayout(new BoxLayout(timePanel,BoxLayout.Y_AXIS));
  timePanel.setSize(300,100);
  timePanel.setMaximumSize(new Dimension(300,100));
  timePanel.setBorder(BorderFactory.createTitledBorder("Time Info"));
  
  //Durata simulazione
  JPanel staticTimePanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(staticTimePanel);
  staticTimePanel.add(new JLabel("Simulation duration (s): "+
		                     simTime.getT()+
		                     " ( "+Utils.getTimeString(simTime.getT())+" )"));
  JPanel staticStepPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(staticStepPanel);
  staticStepPanel.add(new JLabel("Step time (s): "+simTime.getDT()));
  JPanel staticLoopsPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(staticLoopsPanel);
  staticLoopsPanel.add(new JLabel("Total iterations: "+simTime.getN()));
  
  //Dati varibali
  JPanel actTimePanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(actTimePanel);
  actTimePanel.add(new JLabel("Simulation time (s): "));
  simTimeLabel=new JLabel("0.0");
  actTimePanel.add(simTimeLabel);
  simTimeFormatLabel=new JLabel(" "+Utils.getTimeString(0.0f));
  actTimePanel.add(simTimeFormatLabel);
  
  JPanel iterPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(iterPanel);
  iterPanel.add(new JLabel("Iteration: "));
  simLoopLabel=new JLabel("0");
  iterPanel.add(simLoopLabel);
  
  //Ritorno il pannello
  return timePanel;
 }//Fine getTimePanel

private JPanel getScenarioPanel() 
 {
  JPanel scenarioPanel=new JPanel();
  scenarioPanel.setLayout(new BoxLayout(scenarioPanel,BoxLayout.Y_AXIS));
  scenarioPanel.setSize(300,100);
  scenarioPanel.setMaximumSize(new Dimension(300,200));
  scenarioPanel.setBorder(BorderFactory.createTitledBorder("Scenario Info"));
  
  
  scenarioPanel.add(new JLabel("Dimension: ["+scenario.getWidth()+";"+scenario.getHeight()+"] "));
  
  if(scenario.getBorderType()==Scenario.BOUNDED)
   scenarioPanel.add(new JLabel("Border type: Bounded"));
  else
   scenarioPanel.add(new JLabel("Border type: Boundless"));
  
  scenarioPanel.add(new JLabel("Number of Building: "+scenario.getBuildings().size()));
  scenarioPanel.add(new JLabel("Number of Hot-spots: "+scenario.getHotSpots().size()));
  
  //Ritorno il pannello
  return scenarioPanel;
 }//Fine getInfoPanel


 private JPanel getGeneralInfoPanel() 
  {
   JPanel generalInfoPanel=new JPanel(); 
   generalInfoPanel.setLayout(new BorderLayout());
   generalInfoPanel.setSize(600,100);
   generalInfoPanel.setBorder(BorderFactory.createTitledBorder("General Info"));
   
   JPanel modelsInfoPanel=new JPanel();
   generalInfoPanel.add(BorderLayout.WEST,modelsInfoPanel);
   modelsInfoPanel.setLayout(new BoxLayout(modelsInfoPanel,BoxLayout.Y_AXIS)); 
   modelsInfoPanel.add(new JLabel("Models loaded: "+modBuilders.size()));
   JList modList=createNamesList(modBuilders);
   modList.setEnabled(false);
   JScrollPane listModScroller=new JScrollPane(modList);
   listModScroller.setPreferredSize(new Dimension(250,100));
   modelsInfoPanel.add(listModScroller);
   
   
   JPanel recordersInfoPanel=new JPanel();
   generalInfoPanel.add(BorderLayout.EAST,recordersInfoPanel);
   recordersInfoPanel.setLayout(new BoxLayout(recordersInfoPanel,BoxLayout.Y_AXIS)); 
   recordersInfoPanel.add(new JLabel("Data-recorders loaded: "+recBuilders.size()));
   JList recList=createNamesList(recBuilders);
   recList.setEnabled(false);
   JScrollPane listRecScroller=new JScrollPane(recList);
   listRecScroller.setPreferredSize(new Dimension(250,100));
   recordersInfoPanel.add(listRecScroller);
   
   
   //Ritorno il pannello
   return generalInfoPanel;
 }//Fine getGeneralInfoPanel
 
 public JList createNamesList(Vector builders)
  {
   JList namesList=new JList();
   String names[]=new String[builders.size()];
   for(int i=0;i<builders.size();i++)
    {
	 Builder nextBuilder=(Builder)builders.elementAt(i);
	 names[i]=nextBuilder.getName();
    }  
   namesList.setListData(names);
   return namesList; 	 
  }//Fine createNamesList
 
 private Component getCommadsPanel() 
  {
   JPanel commandsPanel=new JPanel();
   commandsPanel.setLayout(new FlowLayout());
   commandsPanel.setSize(600,100);
   //commandsPanel.setBorder(BorderFactory.createTitledBorder("Simulation commands"));	
   progressBar=new SimProgressBar();
   progressBar.setPreferredSize(new Dimension(200,30));
   progressBar.setString("System Ready");
   commandsPanel.add(progressBar);
   
   //Pannello dei bottoni
   JPanel buttonPanel=new JPanel(new FlowLayout());
   commandsPanel.add(buttonPanel);
   run=new JButton("Run");
   run.addActionListener(this);
   buttonPanel.add(run);
   pause=new JButton("Pause");
   pause.addActionListener(this);
   buttonPanel.add(pause);
   stop=new JButton("Stop");
   stop.addActionListener(this);
   buttonPanel.add(stop);
   resetButt=new JButton("Reset");
   resetButt.addActionListener(this);
   buttonPanel.add(resetButt);
   
   showSim=new JButton("Show");
   showSim.addActionListener(this);
   buttonPanel.add(showSim);
   
   //Ritorno il pannello
   return commandsPanel;
  }//Fine getCommandsPanel 

 private JPanel getLogPanel() 
  {
   JPanel logPanel=new JPanel();
   logPanel.setLayout(new BoxLayout(logPanel,BoxLayout.Y_AXIS));
   logPanel.setPreferredSize(new Dimension(600,250));
   logPanel.setBorder(BorderFactory.createTitledBorder("Simulation log"));
   
   //TextLog 
   textLog=new JTextArea(40,40);
   scrollTextLog=new JScrollPane(textLog);
   scrollTextLog.setPreferredSize(new Dimension(580,150));
   logPanel.add(scrollTextLog);
   
   //Pannello Azioni
   JPanel actPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
   logPanel.add(actPanel);
   
   saveLog=new JButton("Save log");
   saveLog.addActionListener(this);
   actPanel.add(saveLog);
   
   verboseMode=new JCheckBox("Verbose Mode");
   verboseMode.setSelected(true);
   actPanel.add(verboseMode,BorderLayout.WEST);
   
   //Ritorno il pannello
   return logPanel;
  }//Fine getLogPanel
 
 public void appendToLog(String text)
  { 
   if(verboseMode.isSelected())
    {  
     textLog.append(text+"\n");
     //Posiziono la barra alla fine del testo di log   
     textLog.setCaretPosition(textLog.getDocument().getLength());
    } 
  }//Fine appendToLog
  
/////////////////////
 public void upTimeLabels(float time,int loop)
  {
   simTimeLabel.setText((new Float(time)).toString());
   simLoopLabel.setText((new Integer(loop)).toString());
   simTimeFormatLabel.setText("( "+Utils.getTimeString(time)+" )");
  }//Fine upTimeLabels
 
  public void setProgressValue(float value)
   {progressBar.setValue(value);}
 
  private void stopSimThread()
   {
	if(sim!=null)
	 {		
	  sim.ferma();
	  synchronized (sim) 
	   {sim.notify();}	
	 }  
   }//Fine stopSimThread
 
////////////////////////////////
public void actionPerformed(ActionEvent e) 
 {
  //CloseButton	 
  if(e.getSource()==closeButton)
    {
	 //Se e' in simulazione ferma tutto 
	 stopSimThread(); 
	  
	 //Chiudo la finestra di visualizzazione
	 closeRtViewer();
	 
	 //Se esiste la finestra di cfg riabilito i componenti
	 if(cfgWnd!=null)
	  cfgWnd.enableComp(true);	 
	 
	 //Chiudo la finestra
	 dispose();
	} 
  
  if(e.getSource()==run)
   {
	if(fRun==true)  
	 {		
	  createAndStartSimulation();
	  fRun=false;
	 }
	else
	 {
	  //Sblocco il thread di visualizzazione
	  synchronized(sim) 
	   {sim.notify();}	
	  cicleButtons();
	 }		
   }
   
  if(e.getSource()==pause)
   {
    //Blocco il thread di visualizzazione
	if(sim!=null)  
     sim.lock();  
	pauseCicleButtons();
   } 
    
  if(e.getSource()==stop)
   {
	stopSimThread();
	enableAllButtons(false);
   }  
  
  if(e.getSource()==resetButt)
   {resetSimulation();} 
  
  if(e.getSource()==showSim)
   {showSimulation();} 
  
  if(e.getSource()==saveLog)
   {saveLog();}	  
 }//Fine actionPerformed

 public void startSetup()
  {
   enableAllButtons(false); 
   progressBar.setString("Setup...");	
  }//Fine startSetup

 public void startSimCicle()
  {
	progressBar.reset();
	progressBar.set(0,simTime.getT());
	cicleButtons();
	//Prendo il tempo all'avvio della simulazione
	startTime=System.currentTimeMillis();
  }//Fine startSimCicle
 
 public void endSimCicle()
  { enableAllButtons(false); }
  
 public void startSimClose()
  {
   enableAllButtons(false);
   progressBar.setString("End simulation...");
  }
 
 public void simEnd()
  {
   //Calcolo e stampo la durata della simualzione	 
   long elapsedTimeMillis=System.currentTimeMillis()-startTime;
   appendToLog("Simulation ended in "+(elapsedTimeMillis/1000f)+" seconds");
   //Resetto i bottoni
   onlyResetButton();
   progressBar.setString("End simulation!");
  }
 
 private void cicleButtons()
  {
   run.setEnabled(false);	 
   stop.setEnabled(true);
   pause.setEnabled(true);
   resetButt.setEnabled(false);
   saveLog.setEnabled(false);
  }//Fine pressPlay
 
 private void pauseCicleButtons()
  {
   run.setEnabled(true);	 
   stop.setEnabled(true);
   pause.setEnabled(false);
   resetButt.setEnabled(false);
   saveLog.setEnabled(false);
  }//Fine pressPause
 
 private void enableAllButtons(boolean flag)
  {
   run.setEnabled(flag);	 
   stop.setEnabled(flag);
   pause.setEnabled(flag);
   resetButt.setEnabled(flag);
   saveLog.setEnabled(flag);
  }//Fine pressPause
 
 private void onlyResetButton()
  {
   run.setEnabled(false);	 
   stop.setEnabled(false);
   pause.setEnabled(false);
   resetButt.setEnabled(true);
   saveLog.setEnabled(true);
  }//Fine pressPause
 
 private void resetButton()
  {
   run.setEnabled(true);	 
   stop.setEnabled(false);
   pause.setEnabled(false);
   resetButt.setEnabled(false);
   saveLog.setEnabled(true);
  }//Fine resetSimulation
 
 private void resetSimulation()
  {
   sim=null;
   fRun=true;
   //Resetto il tempo
   simTime.reset();
   startTime=0;
   //Resetto i bottoni	 
   resetButton();	
   //Resetto la finestra di visualizzazione
   resetRtViewer();
   //Resetto la barra di progressione
   progressBar.reset();
   //Resetto i dati sul tempo
   simTimeLabel.setText("0.0");
   simTimeFormatLabel.setText("( "+Utils.getTimeString(0.0f)+" )");
   simLoopLabel.setText("0");
   //Resetto il log
   textLog.setText(null);
  }//Fine resetSimualtion
 
 private void createAndStartSimulation()
  {
   appendToLog("Build simualtion data...");	 
   //Creo i modelli	 
   appendToLog(" Building models...");
   Vector models=createModels(); 
   appendToLog("  "+models.size()+" models created...");
   
   //Creo i  recorders
   appendToLog(" Building data-recorders...");
   Vector recorders=createRecorders(); 
   appendToLog("  "+recorders.size()+" data-recorders created...");
   appendToLog(" ");
   
   //Aggiungo il recorder per il realTime
   rtRecorder.reset();
   recorders.add(rtRecorder);
   
   //Creo e avvio il thread di simulazione
   sim=new SimThread(scenario,models,recorders,simTime,this);
   sim.start();
   startSetup();
  }//Fine createAndStartSimulation
 
 private Vector createRecorders() 
 {
  Vector recorders=new Vector();
  Iterator i=recBuilders.iterator();
  while(i.hasNext())
   {
	 //Estraggo il builder   
	 DataRecorderBuilder nextBuilder=(DataRecorderBuilder)i.next();
	 //Richiamo il metodo di creazione
	 DataRecorder newRecorder=null;
	 if(loadType==FROM_WND)
	  newRecorder=nextBuilder.createFromDlg();
	 else//FROM_FILE
	  newRecorder=nextBuilder.createFromFile();
	
	 //Setto e aggiungo alla lista
	 if(newRecorder!=null)
     {
	   //Setto il nome del modello
	   newRecorder.setName(nextBuilder.getName());
	   //Aggiungo il modello alla lista dei modelli
	   recorders.add(newRecorder);
	  }  
	 }
 //Ritorno i recorders creati
 return recorders;	
}//Fine createRecorders

private Vector createModels()
 {
  Vector models=new Vector();
  Iterator i=modBuilders.iterator();
  while(i.hasNext())
   {
	 //Estraggo il builder   
	 ModelBuilder nextBuilder=(ModelBuilder)i.next();
	 //Richiamo il metodo di creazione
	 Model newModel=null;
	 
	 //Carico le info nel builder
	 if(loadType==FROM_WND)
	  newModel=nextBuilder.createFromDlg();
	 else//FROM_FILE
	  newModel=nextBuilder.createFromFile();
	 
	 //Setto e aggiungo alla lista dei modelli
	 if(newModel!=null)
	  {
	   //Setto il nome del modello
	   newModel.setName(nextBuilder.getName());
	   //Aggiungo il modello alla lista dei modelli
	   models.add(newModel);
	  }  
   }
  //Ritorno i modelli creati
  return models;
 }//Fine createModels

 private void saveLog() 
  {
   //Setto la directory di default
   Preferences pref=Utils.getPreferences(Momose.PREF_FILE);	
   //Creo la finestra di salvataggio
   JFileChooser fc=new JFileChooser();
   //Setto la directory di default
   fc.setCurrentDirectory(new File(pref.getLogFileDir()));
   //Mostro la finestra
   int returnVal=fc.showSaveDialog(this);
   if(returnVal==JFileChooser.APPROVE_OPTION)
	{
	 //Prelevo il nome del file su cui salvare i dati  
	 File file=fc.getSelectedFile();
	 //Chiamo il SimSaver per salvare il file selezionato
	 SimSaver.saveFile(textLog.getText(),file); 
	}  	 
  }//SaveLog
 
  private void showSimulation() 
   {
	rtViewer.setOpen(true);  
	internalViewer.setVisible(true);
   }//Fine showSimulation
  
  private void closeRtViewer()
   {internalViewer.dispose();}
  
  private void resetRtViewer()
   {
	rtRecorder.reset();
	rtViewer.getSimArena().reDraw();
   }//Fine resetViewer
 }//Fine classe simulationWnd
