package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.Preferences;

public class PrefConfigDlg extends JDialog implements ActionListener
{
 private JTextField configDirText; 	
 private JTextField scenariDirText;
 private JTextField outputDirText;
 private JTextField logFileDirText;
 
 private JFileChooser configDirFc;
 private JFileChooser scenariDirFc;
 private JFileChooser outputDirFc;
 private JFileChooser logFileDirFc;
 
 private JButton openConfigDirButton;
 private JButton openScenariDirButton;
 private JButton openOutputButton;
 private JButton openLogFileDirButton;
 
 private JButton saveButton;
 private JButton cancelButton;
 
 private  String prefFilePath;
 
	
 public PrefConfigDlg(String prefFilePath,Preferences preferences)
  {
   this.prefFilePath=prefFilePath;	 
	 
   setSize(400,320);
   setTitle("Prefereces config dialog");
   //E' una finestra modale
   setModal(true);
   //Non puo' essere ridimensionata
   setResizable(true);
   //Sempre in primo piano
   setAlwaysOnTop(true);
   //Setto il layout
   setLayout(new BorderLayout());
   add(getSaveCancPanel(),BorderLayout.SOUTH);
   add(getClientPanel(preferences),BorderLayout.CENTER);
  }//Fine costruttore
 
private JPanel getSaveCancPanel()
 {
  JPanel saveCancPanel=new JPanel();
  saveCancPanel.setLayout(new FlowLayout());
  
  saveButton=new JButton("Save");
  saveButton.addActionListener(this);
  saveCancPanel.add(saveButton);
  
  cancelButton=new JButton("Cancel");
  cancelButton.addActionListener(this);
  saveCancPanel.add(cancelButton);
  return saveCancPanel; 	
 }//Fine getSaveCancPanel
 
private JPanel getClientPanel(Preferences preferences) 
 {
  JPanel clientPanel=new JPanel();
  clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
  
    //Setto la directory dei file di configurazione
  	clientPanel.add(new JLabel("Default config-files directory:"));
	configDirText=new JTextField(30);
	configDirText.setText(preferences.getConfigDir());
	clientPanel.add(configDirText);
	
	openConfigDirButton=new JButton("Browse...");
	openConfigDirButton.addActionListener(this);
	clientPanel.add(openConfigDirButton);
	
	configDirFc=new JFileChooser();
	configDirFc.setCurrentDirectory(new File(preferences.getConfigDir()));
	configDirFc.setDialogTitle("Select a directory ");
	configDirFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	
    //Setto la directory degli scenari
  	clientPanel.add(new JLabel("Default scenario-files directory:"));
	scenariDirText=new JTextField(30);
	scenariDirText.setText(preferences.getScenariDir());
	clientPanel.add(scenariDirText);
	
	openScenariDirButton=new JButton("Browse...");
	openScenariDirButton.addActionListener(this);
	clientPanel.add(openScenariDirButton);
	
	scenariDirFc=new JFileChooser();
	scenariDirFc.setCurrentDirectory(new File(preferences.getScenariDir()));
	scenariDirFc.setDialogTitle("Select a directory ");
	scenariDirFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	
    //Setto la directory dei traceFile
  	clientPanel.add(new JLabel("Default output directory:"));
	outputDirText=new JTextField(30);
	outputDirText.setText(preferences.getTraceFileDir());
	clientPanel.add(outputDirText);
	
	openOutputButton=new JButton("Browse...");
	openOutputButton.addActionListener(this);
	clientPanel.add(openOutputButton);
	
	outputDirFc=new JFileChooser();
	outputDirFc.setCurrentDirectory(new File(preferences.getTraceFileDir()));
	outputDirFc.setDialogTitle("Select a directory ");
	outputDirFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	
	
    //Setto la directory dei logFile
  	clientPanel.add(new JLabel("Default log-files directory:"));
	logFileDirText=new JTextField(30);
	logFileDirText.setText(preferences.getLogFileDir());
	clientPanel.add(logFileDirText);
	
	openLogFileDirButton=new JButton("Browse...");
	openLogFileDirButton.addActionListener(this);
	clientPanel.add(openLogFileDirButton);
	
	logFileDirFc=new JFileChooser();
	logFileDirFc.setCurrentDirectory(new File(preferences.getLogFileDir()));
	logFileDirFc.setDialogTitle("Select a directory ");
	logFileDirFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	
  return clientPanel;
 }//Fine getDefOptPanel

public  void actionPerformed(ActionEvent e)
 {
  if(e.getSource()==cancelButton)
   { this.dispose(); }
  if(e.getSource()==saveButton)
	 {
	  savePrefFile(prefFilePath);
	  this.dispose();
	 }	
	
  if(e.getSource()==openConfigDirButton)
   {
    int returnVal=configDirFc.showOpenDialog(this);
    if(returnVal==JFileChooser.APPROVE_OPTION)
     {
      File file=configDirFc.getSelectedFile();
	  configDirText.setText(file.getPath());
     } 	
    } 
  
  if(e.getSource()==openScenariDirButton)
  {
   int returnVal=scenariDirFc.showOpenDialog(this);
   if(returnVal==JFileChooser.APPROVE_OPTION)
    {
     File file=scenariDirFc.getSelectedFile();
	  scenariDirText.setText(file.getPath());
    } 	
   } 
  
  if(e.getSource()==openOutputButton)
  {
   int returnVal=outputDirFc.showOpenDialog(this);
   if(returnVal==JFileChooser.APPROVE_OPTION)
    {
     File file=outputDirFc.getSelectedFile();
	  outputDirText.setText(file.getPath());
    } 	
   } 
  
  if(e.getSource()==openLogFileDirButton)
  {
   int returnVal=logFileDirFc.showOpenDialog(this);
   if(returnVal==JFileChooser.APPROVE_OPTION)
    {
     File file=logFileDirFc.getSelectedFile();
	 logFileDirText.setText(file.getPath());
    } 	
   } 
  
 }//Fine actionPerformed

private boolean savePrefFile(String fileName) 
 {
  //Creo il file di configurazione
  FileOutputStream fos;
	try 
	 {fos=new FileOutputStream(fileName); } 
	catch (FileNotFoundException e) 
	 {
      JOptionPane.showMessageDialog(this,"Unable to create "+fileName+"!\n" +
      		                        "Preferences saving aborted!",
                                    "Error",JOptionPane.ERROR_MESSAGE);
	 return false; 	
	}  
	
	if(fos==null)
	{	 
	 JOptionPane.showMessageDialog(this,"Unable to create "+fileName+"!\n" +
                                   "Preferences saving aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE);	
	 return false;
	} 
		
	//Creo lo stream
	PrintStream ps=new PrintStream(fos);
	
	//Scrivo il file xml
	ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	ps.println("<PrefInfo>");
	    
	//Salvo la directory dei file di configurazione
	ps.println("<ConfigFileDir>"+configDirText.getText()+"</ConfigFileDir>");
    //Salvo la directory degli scenari
	ps.println("<ScenarioFileDir>"+scenariDirText.getText()+"</ScenarioFileDir>");
    //Salvo la directory degli traceFile
	ps.println("<TraceFileDir>"+outputDirText.getText()+"</TraceFileDir>");
    //Salvo la directory degli logFile
	ps.println("<LogFileDir>"+logFileDirText.getText()+"</LogFileDir>");
	
	//Chiudo il tag principale
	ps.println("</PrefInfo>");
	
	//Chiudo il file 
	try 
	 { fos.close();	} 
	catch (IOException e) 
	 {
	  JOptionPane.showMessageDialog(this,"Unable to close "+fileName+"!\n" +
                                   "Preferences saving aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE);
      return false; 		
	 }  
  return true;  	
 }//Fine savePrefFile
}//Fine classe RecorderConfigDlg