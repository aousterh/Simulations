package viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.GZIPInputStream;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import parsers.CmdLineParser;
import parsers.XmlParser;

import utils.MomoseFileFilter;
import utils.Utils;

import engine.Scenario;

public abstract class Player extends Viewer implements ActionListener
 {
  //Variabili dell'interfaccia
  private JButton openTraceFileButton;
  private JFileChooser fc;
  
  private JButton start;
  private JButton pause;
  private JButton stop;
  private JButton rewind;
  private JButton ff;
  private JFloatSlider timeSlider;
  
  //Pannello info generali
  private JLabel numNodesLabel;
  private JLabel interpolationLabel;
  
  //Pannello scenario
  private JLabel dimLabel;
  private JLabel borderLabel;
  private JLabel buildLabel;
  private JLabel hotSpotLabel;
  
  //Pannello tempo
  private JLabel timeLabel;
  private JLabel loopLabel;
  private JLabel timeFormatLabel;
  private JLabel tTimeLabel;
  private JLabel sTimeLabel;
  private JLabel tTimeFormatLabel;
  private JLabel tLoopsLabel;
  
  //Pannello velocita'
  private JLabel vLabel;	

  
  //Variabili del viewer
  private ViewerParser saxHandler;
  protected Scenario scenario;
  protected Vector nodes;
  private ViewTime time;
  private SimVisualization simVisualization;

	
  public Player(String name,int scale) 
   {
	super(name,scale);
    //Variabili per gestire i file di trace
	saxHandler=new ViewerParser();
	
	//Variabili di visualizzazione
	time=null;
	simVisualization=null;
	scenario=null;
	nodes=null;
   }//Fine costruttore
  
  protected abstract JPanel getSimPanel();
  
  public void reset()
   {
	scenario=null;
	nodes.clear();
   }//Fine reset

  protected void addPanels() 
   {
    //Aggiungo il pannello del controllo della simulazione
	this.add(BorderLayout.SOUTH,getCtrlPanel());  
	  
	//Creo il pannello di sinistra
	JPanel westPanel=new JPanel();
	westPanel.setLayout(new BoxLayout(westPanel,BoxLayout.Y_AXIS));
	//Creo il pannello delle info generali
	westPanel.add(getGenInfoPanel());
	//Creo il pannello per le info sullo scnario
	westPanel.add(getScenarioPanel());
	//Creo pannello delle informazioni sul tempo
	westPanel.add(getTimePanel());
    //Creo pannello delle opzioni
	westPanel.add(getOptionPanel());
    //Creo il pannello velocita' riproduzione
	westPanel.add(getSpeedPanel());
    //Creo pannello open file
	westPanel.add(getOpenPanel());
    
	//Aggoingo il pannello west
	this.add(BorderLayout.WEST,westPanel);
   }//Fine addPanels
  
  private JPanel getOpenPanel()
  {
   JPanel openPanel=new JPanel();
   openPanel.setLayout(new FlowLayout());
   openPanel.setPreferredSize(new Dimension(150,100));
   
   //Creo botton per aprire i file
   openTraceFileButton=new JButton("Open trace-file");
   openTraceFileButton.addActionListener(this);
   openPanel.add(openTraceFileButton);
   
   return openPanel;
  }//Fine cretaeOpenPanel
  
  private JPanel getGenInfoPanel()
   {
	JPanel genInfoPanel=new JPanel();  
	genInfoPanel.setLayout(new BoxLayout(genInfoPanel,BoxLayout.Y_AXIS));
    genInfoPanel.setPreferredSize(new Dimension(150,100));
     
    JPanel numPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
    genInfoPanel.add(numPanel);
    numNodesLabel=new JLabel("Number of nodes: #");
    numPanel.add(numNodesLabel);
    
    JPanel interpPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
    genInfoPanel.add(interpPanel);
    interpolationLabel=new JLabel("Interpolation enabled: x");
    interpPanel.add(interpolationLabel);
    
    return  genInfoPanel;
   }//Fine getGenInfoPanel 
  
  private JPanel getScenarioPanel() 
   {
	  
    JPanel scenarioPanel=new JPanel();
    scenarioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    scenarioPanel.setPreferredSize(new Dimension(150,200));
    scenarioPanel.setBorder(BorderFactory.createTitledBorder("Scenario Info"));
   
    JPanel scenarioInfoPanel=new JPanel(); 
    scenarioInfoPanel.setLayout(new BoxLayout(scenarioInfoPanel,BoxLayout.Y_AXIS));
    //scenarioPanel.setPreferredSize(new Dimension(150,200));
    scenarioPanel.add(scenarioInfoPanel);
    
    dimLabel=new JLabel("Dimension: [#;#]");
    scenarioInfoPanel.add(dimLabel);
    
    borderLabel=new JLabel("Border type: x");
    scenarioInfoPanel.add(borderLabel);
    
    buildLabel=new JLabel("Number of Building: #");
    scenarioInfoPanel.add(buildLabel);
    
    hotSpotLabel=new JLabel("Number of Hot-spots: #");
    scenarioInfoPanel.add(hotSpotLabel);
   
    //Ritorno il pannello
    return scenarioPanel;
   }//Fine getInfoPanel
 
 private JPanel getTimePanel() 
 {
  //Creo il pannello dei tempi	
  JPanel timePanel=new JPanel();
  timePanel.setLayout(new BoxLayout(timePanel,BoxLayout.Y_AXIS));
  timePanel.setSize(200,500);
  timePanel.setBorder(BorderFactory.createTitledBorder("Time Info"));
  
  //Durata simulazione
  JPanel statTimePanel=new JPanel();
  statTimePanel.setLayout(new BoxLayout(statTimePanel,BoxLayout.Y_AXIS));
  timePanel.add(statTimePanel);
  
  
  
  JPanel staticTimePanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  statTimePanel.add(staticTimePanel);
  tTimeLabel=new JLabel("Sim duration (s): #");
  staticTimePanel.add(tTimeLabel);
  
  JPanel staticTimeFormPanel=new JPanel(new FlowLayout());
  statTimePanel.add(staticTimeFormPanel);
  tTimeFormatLabel=new JLabel("( #:#:#.# )");
  staticTimeFormPanel.add(tTimeFormatLabel);
  
  
  JPanel staticStepPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(staticStepPanel);
  sTimeLabel=new JLabel("Step time (s): #");
  staticStepPanel.add(sTimeLabel);
  
  JPanel staticLoopsPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(staticLoopsPanel);
  tLoopsLabel=new JLabel("Total iterations: #");
  staticLoopsPanel.add(tLoopsLabel);
  
  //Dati varibali
  JPanel actTimePanel=new JPanel();
  actTimePanel.setLayout(new BoxLayout(actTimePanel,BoxLayout.Y_AXIS));
  timePanel.add(actTimePanel);
  
  JPanel actTTimePanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  actTimePanel.add(actTTimePanel);
  actTTimePanel.add(new JLabel("Sim time (s): "));
  timeLabel=new JLabel("0.0");
  actTTimePanel.add(timeLabel);
  
  
  
  JPanel actTTimeFormPanel=new JPanel(new FlowLayout());
  actTimePanel.add(actTTimeFormPanel);
  timeFormatLabel=new JLabel("( 0:0:0.0 )");
  actTTimeFormPanel.add(timeFormatLabel);
  
  JPanel iterPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
  timePanel.add(iterPanel);
  iterPanel.add(new JLabel("Iteration: "));
  loopLabel=new JLabel("0");
  iterPanel.add(loopLabel);
  
  //Ritorno il pannello
  return timePanel;
 }//Fine getTimePanel
 
 private JPanel getSpeedPanel()
  {
   JPanel vPanel=new JPanel(new FlowLayout());
   vPanel.add(new JLabel("Animation speed:"));
   vLabel=new JLabel("1x");
   vPanel.add(vLabel);
   return vPanel;	 
  }//Fine getVelocityPanel
 
 private JPanel getCtrlPanel() 
 {
  JPanel ctrlPanel=new JPanel();
  ctrlPanel.setLayout(new BorderLayout());
  
  
  //Bottoni di play  pause e stop
  JPanel buttonTimePanel=new JPanel();
  buttonTimePanel.setLayout(new FlowLayout());
  ctrlPanel.add(buttonTimePanel,BorderLayout.NORTH);
  
  // Pulsanti di comando della simulazione
  //rewind=new JButton(Utils.getIcon("player_rew.png","Rewind"));
  rewind=new JButton("<<");
  rewind.addActionListener(this);
  rewind.setEnabled(false);
  buttonTimePanel.add(rewind);
  
  //start=new JButton(Utils.getIcon("player_play.png","Play"));
  start=new JButton("Play");
  start.addActionListener(this);
  start.setEnabled(false);
  buttonTimePanel.add(start);
  
  //pause=new JButton(Utils.getIcon("player_pause.png","Pause"));
  pause=new JButton("Pause");  
  pause.addActionListener(this);
  buttonTimePanel.add(pause);
  pause.setEnabled(false);
  
  //ff=new JButton(Utils.getIcon("player_fwd.png","Fast forward"));
  ff=new JButton(">>");
  ff.addActionListener(this);
  ff.setEnabled(false);
  buttonTimePanel.add(ff);
  
  //stop=new JButton(Utils.getIcon("player_stop.png","Stop"));
  stop=new JButton("Stop");
  stop.addActionListener(this);
  buttonTimePanel.add(stop);
  stop.setEnabled(false);
  
  
  //Slider del tempo
  JPanel sliderTimePanel=new JPanel();
  sliderTimePanel.setLayout(new FlowLayout());
  ctrlPanel.add(sliderTimePanel,BorderLayout.SOUTH);
  timeSlider=new JFloatSlider();
  timeSlider.addChangeListener(this);
  ctrlPanel.add(timeSlider);
  timeSlider.setEnabled(false);
  
  return ctrlPanel;
 }//Fine createTimePanel
 
 protected JPanel getOptionPanel()
 {
  JPanel optPanel=new JPanel();
  optPanel.setLayout(new BoxLayout(optPanel,BoxLayout.Y_AXIS));
  
  // Creo lo spinner per la scale
  JPanel scalePanel=new JPanel(new FlowLayout());
  optPanel.add(scalePanel);
  scalePanel.add(new JLabel("Scale:"));
  SpinnerNumberModel modelScale=new SpinnerNumberModel(scale,1,100,0.5);
  modelScale.setMaximum(null);
  scaleSpinner=new JSpinner(modelScale);
  ((JSpinner.DefaultEditor)scaleSpinner.getEditor()).getTextField().setColumns(3);
  scaleSpinner.addChangeListener(this);
  scalePanel.add(scaleSpinner);
  
  // Visualizzazione raggio antenna
  drawAntennaBox=new JCheckBox("Draw Antennas",false);
  drawAntennaBox.addItemListener(this);
  optPanel.add(drawAntennaBox);
  
  drawGraphBox=new JCheckBox("Draw graph",false);
  drawGraphBox.addItemListener(this);
  optPanel.add(drawGraphBox);
  
  //Visualizzazione HotSpot
  drawHotSpotBox=new JCheckBox("Draw Hot-spots",false);
  drawHotSpotBox.addItemListener(this);
  optPanel.add(drawHotSpotBox);
    
  //Visualizzazione id dei nodi
  drawNodeIdBox=new JCheckBox("Draw Node ID",false);
  drawNodeIdBox.addItemListener(this);
  optPanel.add(drawNodeIdBox);
  
  return optPanel;
 }//Fine getOptionPanel
 
 private void setGuiLabels(ViewTime time,Scenario scenario,int numNodes,boolean enabled)
  {
   setGenInfoLabels(numNodes,enabled);
   setScenarioLabels(scenario);
   setTimeLabels(time,enabled);
  }//Fine setGuiLabels
 
 private void setGenInfoLabels(int numNodes,boolean enabled)
  {
   numNodesLabel.setText("Number of nodes: "+numNodes);
   if(enabled==true)
    interpolationLabel.setText("Interpolation enabled: yes");
   else
	interpolationLabel.setText("Interpolation enabled: no");   
  }//Fine setGenIfnoLAbels
 
 private void setScenarioLabels(Scenario scenario)
  {
   dimLabel.setText("Dimension: ["+scenario.getWidth()+";"+scenario.getHeight()+"] ");
   if(scenario.getBorderType()==Scenario.BOUNDED)
	  borderLabel.setText("Border type: Bounded");
   else
    borderLabel.setText("Border type: Boundless");
	buildLabel.setText("Number of Building: "+scenario.getBuildings().size());
	hotSpotLabel.setText("Number of Hot-spots: "+scenario.getHotSpots().size());
  }//Fine setScenarioLabels
 
 private void setTimeLabels(ViewTime time,boolean interpolate)
  {
   tTimeLabel.setText("Sim duration (s): "+(new Float(time.getT())).toString());
   tTimeFormatLabel.setText("( "+Utils.getTimeString(time.getT())+" )");
   if(interpolate==true)
    {  
	 sTimeLabel.setText("Step time (s): 0.01");	
     tLoopsLabel.setText("Total iteration: "+new Integer((int)(time.getT()/0.01f)).toString());
	}
   else
    {
	 sTimeLabel.setText("Step time (s): "+(new Float(time.getDT())).toString());	
	 tLoopsLabel.setText("Total iteration: "+(new Integer(time.getN())).toString()); 
    }  
   timeLabel.setText((new Float(time.getTime())).toString());
   loopLabel.setText((new Integer(time.getLoop())).toString());
   timeFormatLabel.setText("( "+Utils.getTimeString(0)+" )");
  }//Fine setTimeLabels
 
 private void createSimulation(boolean interpolate)
 {
  //Setto i dati di simulazione
  setSimElements();	 
  //Setto lo slider
  resetTimeSlider();
  //setto le label dell'interfaccia
  setGuiLabels(time,scenario,nodes.size(),interpolate);
  //Faccio partire il thread di simulazione
  simVisualization=new SimVisualization(time,nodes,this);
  //Abilito/disabilito l'interpolazione
  simVisualization.setInterpolation(interpolate);
  //Resetto la velocità di esecuzione
  resetAnimSpeed();
  //Faccio partire il thread di visualizzazione
  simVisualization.start();
 }//Fine createSimulation

 private void resetAnimSpeed()
  {
   simVisualization.setVelocity(1);  
   vLabel.setText("1x");
  }//Fine resetAnimSpeed
 
private void setSimElements()
 {
  //Setto il tempo
  time=saxHandler.getViewTime();
  //Setto lo scenario
  scenario=saxHandler.getScenario();
  simArena.setScenario(scenario);
  simArena.reDraw();
  //Setto il vettore dei nodi
  nodes=saxHandler.getNodes();
 }//Fine setSimElementes

private void resetTimeSlider() 
 {
  //Setto il valore minimo
  timeSlider.setFloatData(0,time.getT(),0);
  //Setto le label associate allo slider
  setTimeSliderLabels(time);
  timeSlider.setEnabled(true);
 }//Fine setTimeSLider

private void setTimeSliderLabels(ViewTime time) 
 {
  timeSlider.setMajorTickSpacing(10);
  timeSlider.setPaintTicks(true);
  
  // Creo la tabella dei label
  Hashtable labelTable = new Hashtable();
  labelTable.put(new Integer(0),new JLabel("0"));
	   
  Float middlef=new Float((int)(time.getT()/2));
  labelTable.put(new Integer(middlef.intValue()*100),new JLabel(middlef.toString()));
  
  Integer max=new Integer((int)time.getT());
  labelTable.put(max.intValue()*100, new JLabel(max.toString()));
	   
  timeSlider.setLabelTable(labelTable );
  timeSlider.setPaintLabels(true);	
 }//Fine setTimeSLiderLabels

 public void setTimeSlider(float time) 
  { timeSlider.setFloatValue(time); }

 public void setViewTimeFromSlider()
  {
   time.impTime(timeSlider.getFloatValue());
   upTimeLabels(time.getTime(),time.getLoop());
  }//Fine changeTime
 
 public void upTimeLabels(float time,int loop)
  {
   timeLabel.setText((new Float(time)).toString());
   timeFormatLabel.setText("( "+Utils.getTimeString(time)+" )");
   loopLabel.setText((new Integer(loop)).toString());
  }//Fine upTimeLabels
 
 
 public void enableElements(boolean flag)
 {
  if(flag==true)
   {
	 start.setEnabled(true);
	 rewind.setEnabled(true);
	 ff.setEnabled(true);
	 pause.setEnabled(true);
	 stop.setEnabled(true);
	 timeSlider.setEnabled(true);
	 openTraceFileButton.setEnabled(false);
   }
  else
   {
	 start.setEnabled(false);
	 rewind.setEnabled(false);
	 ff.setEnabled(false);
	 pause.setEnabled(false);
	 stop.setEnabled(false);
	 timeSlider.setEnabled(false);
	 openTraceFileButton.setEnabled(true);
   }
 }//Fine buttonEnable
 
 
 private OpenDlg openConfigDlg()
  {
   //Chiamo la finestra di selezione file
   OpenDlg otfDlg=new OpenDlg(); 
   otfDlg.setVisible(true);
   return otfDlg; 
  }//Fine openConfigDlg


 private boolean openTraceFile(String filePath)  
  {
   if(filePath==null)
    {
	 JOptionPane.showMessageDialog(this,"Invalid path...\n"+
                                   "Loading aborted!",  
      	                           "Error",JOptionPane.ERROR_MESSAGE); 
	 return false;
    } 
      
     File file=new File(filePath);
     if(!file.exists())
      {
	   JOptionPane.showMessageDialog(this,"File not found...\n"+
			                         "Loading aborted!",  
	                          	     "Error",JOptionPane.ERROR_MESSAGE);  
	   return false;  
      }

  //Determino l'estensione del trace-file
  String ext=MomoseFileFilter.getExtension(file);
  
  //Faccio il parsing del file-trace
  boolean res=false;
  if(ext.equals("xml"))
	 res=parsingTraceFile(file,false);
  else
   {
	if(ext.equals("gz"))  
	 {res=parsingTraceFile(file,true);} 
	else
	{	
	 JOptionPane.showMessageDialog(this,"Bad extension of input file...\n"+
                "Loading aborted!",  
                "Error",JOptionPane.ERROR_MESSAGE);
	 return false;
	}
   }	  
  
  if(res==false)
	 return false;
  
  if(saxHandler.isTraceFile()==false)
  {
	JOptionPane.showMessageDialog(this,"The input file is not a trace-file...\n"+
			                       "Loading aborted!",  
                                  "Warning",JOptionPane.WARNING_MESSAGE); 
   return false;  
  }
 return true;
}//Fine openXmlFIle

private boolean parsingTraceFile(File file,boolean compressed) 
 {
  saxHandler.reset();
  boolean res=false;
  
  if(compressed==false)
   {  
	res=XmlParser.parse(file,saxHandler);
   }	
  else
   {
	//Creo l'inputStream per leggere il file compresso
	try {
		GZIPInputStream zipInutStream=new GZIPInputStream(new FileInputStream(file));
		//Faccio il parsing dell'inputStream
		res=XmlParser.parse(zipInutStream,saxHandler);
		} 
	   catch (FileNotFoundException e) 
	    {
		 JOptionPane.showMessageDialog(this,"File not found...\n"+
                                        "Loading aborted!",  
         	                            "Error",JOptionPane.ERROR_MESSAGE);  
		 return false;
	    } 
	   catch (IOException e) 
	    {
		 JOptionPane.showMessageDialog(this,"I/O error during trace-file parsing..."+
                                       "Loading aborted!",  
                                       "Error",JOptionPane.ERROR_MESSAGE);  
		 return false;
		}
   }
  
  if(res==false)
   {
    JOptionPane.showMessageDialog(this,"Error in parsing "+file.getPath()+"\n"+
		                                 "Loading aborted!",  
		         	                     "Error",JOptionPane.ERROR_MESSAGE);  	
	return false;
   } 	
  return true; 
 }//Fine parsingTraceFile

 public void openFileFromCmdLine(String filePath,boolean interpolate)
  {
   boolean fileResult=openTraceFile(filePath); 	 
   if(fileResult==true)
    {   
     timeSlider.reset(); 
     createSimulation(interpolate);  
     enableElements(true);
    } 
  }//Fine openFileFromCmdLine
 
 public void actionPerformed(ActionEvent e) 
  {
   if(e.getSource()==openTraceFileButton)
    {
	 //Apro la finestra di simulazione   
	 OpenDlg dlg=openConfigDlg();
	 //Se premuto ok
	 if(dlg.getResult()==OpenDlg.OK_OPTION)
	  {
	   //Provo ad aprire il trace file	 
	   boolean fileResult=openTraceFile(dlg.getFilePath()); 	 
	   if(fileResult==true)
	    {   
	     timeSlider.reset(); 
	     createSimulation(dlg.getInterpolate());  
	     enableElements(true);
	    }
	  }
    }   
	
 if(e.getSource()==start)
  {
	//Resetto la velocità di riproduzione  
	//resetAnimSpeed();
   //Sblocco il thread di visualizzazione
   synchronized (simVisualization) 
	{simVisualization.notify();}
  }

 if(e.getSource()==pause)
  {
   //Blocco il thread di visualizzazione
   simVisualization.lock();
  }

 if(e.getSource()==stop)
  {
   simVisualization.setLoopState(false);
   enableElements(false);
  }	  

 if(e.getSource()==rewind)
  {
   int newVel=simVisualization.getVelocity()-1;
   if(newVel==0)
    newVel=-1;
   simVisualization.setVelocity(newVel); 
   vLabel.setText(newVel+"x");
  }	 

 if(e.getSource()==ff)
  {
   int newVel=simVisualization.getVelocity()+1;
   if(newVel==0)
    newVel=1;
   simVisualization.setVelocity(newVel);
   vLabel.setText(newVel+"x");
  }	 
 }//Fine actionPerformed
 
 public void stateChanged(ChangeEvent e) 
  {
   super.stateChanged(e);	
	 
	if(e.getSource()==timeSlider)
	  { 
	   //Re-imposto il tempo  
	   setViewTimeFromSlider();
	   //Aggiorno la posizione dei nodi 
	   if(simVisualization!=null)
		  simVisualization.updatePositions();
	  }	 
   }//Fine stateChanged
 
 protected void windowClosing() 
  {
   //simVisualization.interrupt();	 
   if(simVisualization!=null)	 
    simVisualization.setLoopState(false); 
  }//Fine WindowClosing
 
 
 public static JFrame getFramePlayer(String arenaType,String fileName,
		                             boolean interpolate,int scale,int w,int h)
  {
   Player player=null;	 
	 
   if(arenaType.equals("opengl"))
	 player=new PlayerGL(scale);
    else
     player=new PlayerSwing(scale); 
   
   //Creo il frame dell'applicazione	 
   JFrame framePlayer=new JFrame();
   framePlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   framePlayer.setSize(w,h);
   framePlayer.setTitle(player.getName());
   framePlayer.add(player);
   
   //Se h passato un path aprodirettamente il trace-file
   if(fileName!=null)
    player.openFileFromCmdLine(fileName,interpolate); 
    
   //Ritorno la finestra
   return framePlayer; 
  }//Fine getInternalViewer 
 
 private static void printUsage()
  {
   System.err.println("Usage: java [-options] viewer.Player [args...]\n"+
     " Options include:\n"+
     "  -?,--help               Print this help message\n"+
	 //"  -t,--type <string>      Type of simulation arena {swing,opengl}\n"+
	 "  -f,--file <string>      Name of the input trace-file\n"+
	 "  -i,--interpolate        Interpolate position (linked at -f option)\n"+
	 "  -s,--scale <integer>    Initial scale of simulation arena\n"+
	 "  -w,--width <integer>    Width of main window\n"+
	 "  -h,--height <integer>   Height of main window\n");
  }//Fine printUsage

 public static void main(String[] args) 
  {
   CmdLineParser parser=new CmdLineParser();
   CmdLineParser.Option oHelp=parser.addBooleanOption('?',"help"); 
   //CmdLineParser.Option oArenaType=parser.addStringOption('t',"type");
   CmdLineParser.Option oFileName=parser.addStringOption('f',"file");
   CmdLineParser.Option oInterpolate=parser.addBooleanOption('i',"interpolate");
   CmdLineParser.Option oScale=parser.addIntegerOption('s',"scale");
   CmdLineParser.Option oWidth=parser.addIntegerOption('w',"width");
   CmdLineParser.Option oHeight=parser.addIntegerOption('h',"height");   
   
   //Faccio il parsing della linea di comando
   try
    {parser.parse(args);}                                                                       
   catch ( CmdLineParser.OptionException e ) 
    {                             
     System.err.println(e.getMessage());                                 
     printUsage();                                                       
     System.exit(2);                                                     
    }
	 
   //Prelevo i parametri dalla linea di comando
   if((Boolean)parser.getOptionValue(oHelp,Boolean.FALSE))
     { printUsage(); System.exit(0); } 
   
   String fileName=(String)parser.getOptionValue(oFileName,null);
   boolean interpolate=(Boolean)parser.getOptionValue(oInterpolate,Boolean.FALSE);
   
   String arenaType="opengl"; 
  /* String arenaType=(String)parser.getOptionValue(oArenaType,"opengl"); 
   
   if(!arenaType.equals("swing")&&(!arenaType.equals("opengl")))
    { 
	 System.err.println("Unknown option '"+arenaType+"'");  
	 printUsage(); System.exit(2); 
	} */
   
   int scale=(Integer)parser.getOptionValue(oScale,new Integer(1));
   if(scale<1)
	  scale=1; 
   
   int width=(Integer)parser.getOptionValue(oWidth,new Integer(900));
   if(width<1)
	  width=1; 
   
   int height=(Integer)parser.getOptionValue(oHeight,new Integer(800));
   if(height<1)
	  height=1;
  
  //Creo il frame dell'applicazione 
  JFrame framePlayer=getFramePlayer(arenaType,fileName,interpolate,scale,width,height);
  //Visualizzo il frame
  framePlayer.setVisible(true);
 }//Fine main
}//Fine Player


