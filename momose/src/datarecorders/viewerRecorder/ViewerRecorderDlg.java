package datarecorders.viewerRecorder;

import gui.Momose;


import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import utils.Preferences;
import utils.Utils;

import datarecorders.RecorderConfigDlg;

public class ViewerRecorderDlg extends RecorderConfigDlg 
 {
  private JCheckBox compressOutput;	
	
  public ViewerRecorderDlg()
   {
    super();
    setTitle("ViewerRecorder config dialog");
    setSize(400,160);
    filePath.setColumns(35);
    //Setto la directory di default
    Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
    filePath.setText(pref.getTraceFileDir()+File.separator+"viewerRecorderOutput.xml");
    
   }//Fine costruttore
  
  public JPanel createClientPanel() 
  { 
   JPanel clientPanel=new JPanel(new BorderLayout());
   clientPanel.add(getDefaultOptPanel(),BorderLayout.CENTER);
   
   compressOutput=new JCheckBox("Compress output trace-file?");
   compressOutput.setSelected(false);
   clientPanel.add(compressOutput,BorderLayout.SOUTH);
   
   return clientPanel;	
  }//Fine cretaeClientPanel
  
  public void setCompressOutput(boolean compressOut)
   {this.compressOutput.setSelected(compressOut);}
  
  public boolean getCompressOutput()
   {return compressOutput.isSelected();}
  
 }//Fine ViewerRecorderDlg
