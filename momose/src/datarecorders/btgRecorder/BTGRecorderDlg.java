package datarecorders.btgRecorder;

import gui.Momose;


import java.awt.BorderLayout;
import java.io.File;


import javax.swing.JPanel;

import utils.Preferences;
import utils.Utils;

import datarecorders.RecorderConfigDlg;

public class BTGRecorderDlg extends RecorderConfigDlg 
 {
  
  public BTGRecorderDlg()
   {
    super();
    setTitle("BTGRecorder config dialog");
    setSize(400,135);
    filePath.setColumns(35);
    //Setto la directory di default
    Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
    filePath.setText(pref.getTraceFileDir()+File.separator+"BTGRecorderOutput.txt");
    
   }//Fine costruttore
  
  public JPanel createClientPanel() 
   { 
    JPanel clientPanel=new JPanel(new BorderLayout());
    clientPanel.add(getDefaultOptPanel(),BorderLayout.CENTER);
    return clientPanel;	
   }//Fine cretaeClientPanel
  
 }//Fine ViewerRecorderDlg
