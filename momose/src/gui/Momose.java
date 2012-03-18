package gui;


import java.io.File;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;


import core.SimulationManager;

import utils.Preferences;
import utils.Utils;
import viewer.InternalFrameViewer;
import viewer.Player;


public class Momose extends JFrame 
 {
  public static String PREF_FILE="."+File.separator+"momosepref.xml";	
	
  private JDesktopPane desktopPane;
  private Vector simThreads;
  private int simCount; 
  
  private SimulationManager simManager;
  
  public Momose()
   {
  try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      } 
      catch (Exception e) {}
       	
	//Creo il manager delle simulazioni  
	simManager=new SimulationManager(this);  
	  
	//Creo il desktopPane della finestra madre   
	desktopPane=new JDesktopPane();
	this.getContentPane().add(desktopPane);
	
	//Creo il vettore dei riferimenti ai thread
	simThreads=new Vector();
    simCount=0;
	
	//Creo il menu principale
	this.setJMenuBar(createMenuBar());
   }//Fine costruttore
  
  public SimulationManager getSimManager()
   {return simManager;}
  
  public JDesktopPane getDesktopPane()
   {return desktopPane;}
  
  public JMenuBar createMenuBar()
   {
    //Creo la barra del menu
    MomoseMenuBar menuBar=new MomoseMenuBar(this);
    return menuBar;	 
   }//Fine cretaeMenuBar
  
  public void showPlayer(Player player)
   {
    //Creo la finestra interna  
	JInternalFrame internalViewer=new InternalFrameViewer(player);
	internalViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	internalViewer.setSize(700,600);
	internalViewer.setResizable(true);
	internalViewer.setMaximizable(true);
	internalViewer.setIconifiable(true);
	internalViewer.setClosable(true);
	
	//Aggancio la finestra a
	getDesktopPane().add(internalViewer);
	//Mostro la finestra
	internalViewer.setVisible(true);	  
   }//Fine showPlayer
  
  public static void main(String[] args) 
   {
	//Creo l'istanza della fineatra del simulatore  
    Momose momoseFrame=new Momose();
    //Setto i paramentri principali
    momoseFrame.setTitle("MOMOSE");
    momoseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    momoseFrame.setSize(1000,700);
    //Visualizzo la finestra
    momoseFrame.setVisible(true);	
   }//Fine main 

 public void setPreferences() 
  {
   //Provo a leggere il file di configurazione
   Preferences pref=Utils.getPreferences(PREF_FILE); 
   PrefConfigDlg prefCfgDlg=new PrefConfigDlg(PREF_FILE,pref);
   prefCfgDlg.setVisible(true);
  }//Fine setPreferences
 
 public void compressTraceFile(boolean compression) 
  {
   //Provo a leggere il file di configurazione
   Preferences pref=Utils.getPreferences(PREF_FILE); 
   CompressTraceFileDlg compressDlg=new CompressTraceFileDlg(pref,compression);
   compressDlg.setVisible(true);
  }//Fine compDecTraceFile

 public void generateScenario() 
  {
   //Provo a leggere il file di configurazione
   Preferences pref=Utils.getPreferences(PREF_FILE); 
   ScenarioGeneratorDlg generatorDlg=new ScenarioGeneratorDlg(pref);
   generatorDlg.setVisible(true);	
  }//Fine generateScenario
 }//Fine classe Simulator
