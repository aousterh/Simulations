package datarecorders;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.MomoseFileFilter;
import utils.Preferences;
import utils.Utils;

import gui.ConfigDlg;
import gui.Momose;

public class RecorderConfigDlg extends ConfigDlg 
 {
  protected JTextField filePath; 	
  protected JFileChooser fc;
  protected JButton openFileButton;
	
  public RecorderConfigDlg()
   {
	setSize(350,150);
    setTitle("Data-Recorder config dialog");
   }//Fine costruttore
  
  public void setFilePath(String text)
  {filePath.setText(text);}
  
  public String getFilePath()
   {return filePath.getText();}

 public JPanel createClientPanel() 
  { 
   JPanel clientPanel=new JPanel();
   clientPanel.add(getDefaultOptPanel());
   return clientPanel;	
  }//Fine createClientPanel

 protected JPanel getDefaultOptPanel() 
  {
   JPanel defOptPanel=new JPanel();
   defOptPanel.setLayout(new BoxLayout(defOptPanel,BoxLayout.Y_AXIS));
   
   	fc=new JFileChooser();
   	fc.setFileFilter(new MomoseFileFilter("tracefile"));
   	defOptPanel.add(new JLabel("File Path:"));
	filePath=new JTextField(30);
	defOptPanel.add(filePath);
	openFileButton=new JButton("Browse...");
	openFileButton.addActionListener(this);
	defOptPanel.add(openFileButton);
	
	return defOptPanel;
  }//Fine getDefOptPanel
 
 public  void actionPerformed(ActionEvent e)
  {
   //Gestiosco gli eventi della classe padre	 
   super.actionPerformed(e);  
   
   if(e.getSource()==openFileButton)
    {
	 Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
	 fc.setCurrentDirectory(new File(pref.getTraceFileDir()));
	 int returnVal=fc.showSaveDialog(this);
     if(returnVal==JFileChooser.APPROVE_OPTION)
      {
       File file=fc.getSelectedFile();
	   filePath.setText(file.getPath());
      } 	
     } 
  }//Fine actionPerformed
}//Fine classe RecorderConfigDlg
