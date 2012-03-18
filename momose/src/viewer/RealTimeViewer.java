package viewer;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import engine.Scenario;

public class RealTimeViewer extends Viewer
 {
  private InternalFrameViewer window;
  private boolean open;
  private boolean rt;
  private JCheckBox realTime;
  	
 public RealTimeViewer(float scale) 
   {
	super("Real time visualizer",scale);
	window=null;
	open=false;
	rt=true;
   }//Fine costruttore

  protected void addPanels() 
   { this.add(BorderLayout.SOUTH,getOptionPanel()); }
  
  public void setOwnerWindow(InternalFrameViewer window)
   {this.window=window;}

  public void setOpen(boolean open)
   {this.open=open;}
  
  public boolean isOpen() 
   {return open;}
  
  public void setRealTime(boolean rt)
   {this.rt=rt;}
 
  public boolean isRealTime() 
   {return rt;}
  
  public SimArena getSimArena()
   {return simArena;}
  
  protected JPanel getSimPanel()
   {
    //Creo l'area di simulazione
    simArena=new SimArenaGL(scale);
    simArena.revalidate();
    //Creo il pannello di sfondo
    JPanel backgroundPanel=new JPanel(new BorderLayout());
    backgroundPanel.add(simArena,BorderLayout.CENTER);
   
    //Ritorno il pannello
    return backgroundPanel;
   }//Fine getSimPanel
  
  protected JPanel getOptionPanel()
   {
    JPanel optPanel=new JPanel();
    optPanel.setLayout(new BoxLayout(optPanel,BoxLayout.X_AXIS));
   
   // Creo lo spinner per la scale
   JPanel defPanel=new JPanel();
   defPanel.setLayout(new BoxLayout(defPanel,BoxLayout.Y_AXIS));
   optPanel.add(defPanel);
   
   JPanel scalePanel=new JPanel();
   scalePanel.add(new JLabel("Scale:"));
   SpinnerNumberModel modelScale=new SpinnerNumberModel(scale,1,100,1);
   modelScale.setMaximum(null);
   scaleSpinner=new JSpinner(modelScale);
   ((JSpinner.DefaultEditor)scaleSpinner.getEditor()).getTextField().setColumns(3);
   scaleSpinner.addChangeListener(this);
   scalePanel.add(scaleSpinner);
   defPanel.add(scalePanel);
   
   // Visualizzazione real-time
   realTime=new JCheckBox("Real time",true);
   realTime.addItemListener(this);
   defPanel.add(realTime);
   
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
   
   // Visualizzazione ID dei nodi
   drawNodeIdBox=new JCheckBox("Draw node ID",false);
   drawNodeIdBox.addItemListener(this);
   optPanel.add(drawNodeIdBox);  
   
   return optPanel;
  }//Fine getOptionPanel
  
  public void setScenario(Scenario scenario)
   { simArena.setScenario(scenario);}
  
  //public void setNodes(Vector nodes)
   //{ simArena.setNodes(nodes); simArena.reDraw();}

  protected void windowClosing() 
   {
	setOpen(false); 
	window.setVisible(false);
   }//Fine windowClosing
  
 public void itemStateChanged(ItemEvent e) 
  {
   super.itemStateChanged(e);
   if(e.getSource()==realTime)
 	 { rt=realTime.isSelected(); }  
   }//Fine itemStateChanged 
 }//Fine RealTimeiewer
