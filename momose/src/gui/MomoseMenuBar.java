package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import viewer.PlayerGL;
import viewer.PlayerSwing;

public class MomoseMenuBar extends JMenuBar implements ActionListener
{
 private Momose owner;
 
 //Variabili menu file
 JMenu menuFile;
 private JMenuItem newSimulation;
 
 private JMenu subLoadSim;
 private JMenuItem loadInSimWnd;
 private JMenuItem loadInConfigSimWnd;
 private JMenuItem preferences;
 private JMenuItem exit;
 
 //Variabili menu file
 JMenu menuViewer;
 private JMenuItem playerSwing;
 private JMenuItem playerOpenGL;
 
//Variabili menu file
 JMenu menuUtils;
 private JMenuItem generateScenario;
 private JMenuItem compressTraceFile;
 private JMenuItem decompressTraceFile;
 private JMenu subCompressionTool;
 
 
  
 public MomoseMenuBar(Momose owner)
  {
	this.owner=owner;
	//SubMenuFile
	this.add(createSubmenuFile());
	//SubMenuViewer
	this.add(createSubmenuViewer());
    //SubMenuUtils
	this.add(createSubmenuUtils());
  }//Fine 
 
private JMenu createSubmenuFile()
 {
  menuFile=new JMenu("File");
  menuFile.setMnemonic(KeyEvent.VK_F);
  	 
  //Item nuova simulazione
  newSimulation=new JMenuItem("New Simulation",KeyEvent.VK_N);
  newSimulation.setAccelerator(KeyStroke.getKeyStroke(
                               KeyEvent.VK_N, ActionEvent.ALT_MASK));

  newSimulation.addActionListener(this);
  menuFile.add(newSimulation);
  
  //SubMenu load simulation
  subLoadSim=new JMenu("Load simulation");
  subLoadSim.setMnemonic(KeyEvent.VK_L);
  menuFile.add(subLoadSim);
  
  loadInSimWnd=new JMenuItem("In simulation window",KeyEvent.VK_S);
  loadInSimWnd.setAccelerator(KeyStroke.getKeyStroke(
                                KeyEvent.VK_S, ActionEvent.ALT_MASK));
  loadInSimWnd.addActionListener(this);
  subLoadSim.add(loadInSimWnd);
  
  loadInConfigSimWnd=new JMenuItem("In config simulation window",KeyEvent.VK_C);
  loadInConfigSimWnd.setAccelerator(KeyStroke.getKeyStroke(
                                KeyEvent.VK_C, ActionEvent.ALT_MASK));
  loadInConfigSimWnd.addActionListener(this);
  subLoadSim.add(loadInConfigSimWnd);
  
  //Item preferenze
  preferences=new JMenuItem("Preferences",KeyEvent.VK_P);
  preferences.setAccelerator(KeyStroke.getKeyStroke(
                               KeyEvent.VK_P, ActionEvent.ALT_MASK));

  preferences.addActionListener(this);
  menuFile.add(preferences);
  
  menuFile.addSeparator();
  //Item exit
  exit=new JMenuItem("Exit",KeyEvent.VK_X);
  exit.setAccelerator(KeyStroke.getKeyStroke(
                                KeyEvent.VK_X, ActionEvent.ALT_MASK));
  exit.addActionListener(this);
  menuFile.add(exit);
  
  return menuFile;
 }//Fine createSubMenuFile

private JMenu createSubmenuViewer()
{
 menuViewer=new JMenu("View");
 menuViewer.setMnemonic(KeyEvent.VK_V);
 	 
 //Item viewerSwing
 /*playerSwing=new JMenuItem("Swing-Player",KeyEvent.VK_W);
 playerSwing.setAccelerator(KeyStroke.getKeyStroke(
                              KeyEvent.VK_W, ActionEvent.ALT_MASK));

 playerSwing.addActionListener(this);
 menuViewer.add(playerSwing);*/
 
 //Item viewerOpenGL
 playerOpenGL=new JMenuItem("Player",KeyEvent.VK_Y);
 playerOpenGL.setAccelerator(KeyStroke.getKeyStroke(
                              KeyEvent.VK_Y, ActionEvent.ALT_MASK));
 playerOpenGL.addActionListener(this);
 menuViewer.add(playerOpenGL);
 
 return menuViewer;
}//Fine createSubMenuFile


private JMenu createSubmenuUtils()
{
 menuUtils=new JMenu("Utils");
 menuUtils.setMnemonic(KeyEvent.VK_U);
 	 
 //Item viewerSwing
 generateScenario=new JMenuItem("Generate scenario from svg file",KeyEvent.VK_G);
 generateScenario.setAccelerator(KeyStroke.getKeyStroke(
                              KeyEvent.VK_G, ActionEvent.ALT_MASK));

 generateScenario.addActionListener(this);
 menuUtils.add( generateScenario);
 
 
 //Compression tools
 
 subCompressionTool=new JMenu("Compression tools");
 subCompressionTool.setMnemonic(KeyEvent.VK_M);
 menuUtils.add(subCompressionTool);
 
 compressTraceFile=new JMenuItem("Compress trace-file",KeyEvent.VK_T);
 compressTraceFile.setAccelerator(KeyStroke.getKeyStroke(
                              KeyEvent.VK_T, ActionEvent.ALT_MASK));
 compressTraceFile.addActionListener(this);
 subCompressionTool.add(compressTraceFile);
 
 decompressTraceFile=new JMenuItem("Decompress trace-file",KeyEvent.VK_D);
 decompressTraceFile.setAccelerator(KeyStroke.getKeyStroke(
                              KeyEvent.VK_D, ActionEvent.ALT_MASK));
 decompressTraceFile.addActionListener(this);
 subCompressionTool.add(decompressTraceFile);
 
 return menuUtils;
}//Fine createSubMenuUtils
 

public void actionPerformed(ActionEvent e) 
 {
  if(e.getSource()==newSimulation)
   { owner.getSimManager().newConfigSimWnd(); }	
  
  if(e.getSource()==loadInSimWnd)
   { owner.getSimManager().LoadSimFromFile(); }	
  
  if(e.getSource()==loadInConfigSimWnd)
   { owner.getSimManager().loadConfigSimWnd(); }
  
  if(e.getSource()==preferences)
  { owner.setPreferences(); }
  
  if(e.getSource()==exit)
   { 
	owner.dispose();
	System.exit(0);
   }
  
  if(e.getSource()==playerSwing)
   {
	PlayerSwing player=new PlayerSwing(5);  
	owner.showPlayer(player); 
   }
 
  if(e.getSource()==playerOpenGL)
   { 
	PlayerGL player=new PlayerGL(1);  
	owner.showPlayer(player); 
   }	
  
  if(e.getSource()==generateScenario)
   { owner.generateScenario(); }	
  
  if(e.getSource()==compressTraceFile)
   { owner.compressTraceFile(true); }	
  
  if(e.getSource()==decompressTraceFile)
   { owner.compressTraceFile(false); }	
 
 }//Fine actionPerformed
}//Fine classe MoveMenu