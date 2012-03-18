package datarecorders.ecraRecorder;

import gui.Momose;


import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import utils.Preferences;
import utils.Utils;

import datarecorders.RecorderConfigDlg;

public class ECRARecorderDlg extends RecorderConfigDlg 
 {
  private JCheckBox compressOutput;	
	
  public ECRARecorderDlg()
   {
    super();
    setTitle("ECRA recorder config dialog");
    setSize(400,160);
    filePath.setColumns(35);
    //Setto la directory di default
    Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
    filePath.setText(pref.getTraceFileDir()+File.separator+"ecraRecorderOutput.txt");
    
   }//Fine costruttore
  
  public JPanel createClientPanel() 
   { 
    JPanel clientPanel=new JPanel(new BorderLayout());
    clientPanel.add(getDefaultOptPanel(),BorderLayout.CENTER);
    return clientPanel;	
   }//Fine cretaeClientPanel
 }//Fine ViewerRecorderDlg
