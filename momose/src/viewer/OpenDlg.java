package viewer;

import gui.Momose;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.MomoseFileFilter;
import utils.Preferences;
import utils.Utils;

public class OpenDlg extends JDialog implements ActionListener
 {
  public static boolean OK_OPTION=true;	
  public static boolean CANC_OPTION=false;	
  
  private Container cp;//ContentPane delle importWindow
  private JOptionPane optionPane;
  
  private JButton okButton; 
  private JButton cancButton;
  
  private boolean result;
  
  private JFileChooser fc;
  private JButton openFileButton;
  private JTextField filePath;
  private JCheckBox interPos;
  
  public OpenDlg()
   {
	//Prendo il contentpane  
	cp=this.getContentPane();
	//Setto le dimensioni della finestra
	setSize(300,150);
	//Centro la finestra
	setLocationRelativeTo(getParent());
	//E' una finestra modale
    setModal(true);
    //Setto il titolo
    setTitle("Open Trace file");
    //Non può essere ridimensionata
    setResizable(false);
    
    //Setto il ritorno di default
    result=false;
	
     //Preparo i due pannelli principali
     cp.setLayout(new BorderLayout());
     cp.add(createOkCancPanel(),BorderLayout.SOUTH);
	 cp.add(createClientPanel(),BorderLayout.CENTER);
	}//Fine costruttore
  
  public boolean getResult()
   {return result;}
  
  public String getFilePath()
   {
	File file=new File(filePath.getText());
	if(file.isFile())
	 return filePath.getText();
	else
	 return null;
   }//Fine getFilePath
  
  public boolean getInterpolate()
   {return interPos.isSelected();}
  
  public JPanel createOkCancPanel()
   {
	JPanel okCancPanel=new JPanel();
	okCancPanel.setLayout(new FlowLayout());
	cp.add(okCancPanel,BorderLayout.SOUTH);
	
	okButton=new JButton("Ok");
	okButton.addActionListener(this);
	cancButton=new JButton("Cancel");
	cancButton.addActionListener(this);
	okCancPanel.add(okButton);
	okCancPanel.add(cancButton);
	
	return okCancPanel; 
   }//Fine createPanel
  
  public JPanel createClientPanel()
   {
    JPanel clientPanel=new JPanel();
    clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
    ///
    //Setto le variabili per il file xml
	fc=new JFileChooser();
	//Imposto il filtro
	fc.setFileFilter(new MomoseFileFilter("alltracefile"));
	clientPanel.add(new JLabel("Path of trace file:"));
	filePath=new JTextField(30);
	clientPanel.add(filePath);
	openFileButton=new JButton("Browse...");
	openFileButton.addActionListener(this);
	clientPanel.add(openFileButton);
	
	//Setto le varibili per l'interpolazione
	interPos=new JCheckBox("Enable positions interpolation",false);
	clientPanel.add(interPos);
	///
	return clientPanel;  
   }//Fine cretaeClientPanel
  
 public void actionPerformed(ActionEvent e) 
 {
  if(e.getSource()==okButton)
   {
	result=true;
	this.setVisible(false);
   }   
  
  if(e.getSource()==cancButton)
   {
	result=false;
	this.setVisible(false);
   } 
  
  if(e.getSource()==openFileButton)
   {
	//Setto la directory corrente  
	Preferences pref=Utils.getPreferences(Momose.PREF_FILE);
	fc.setCurrentDirectory(new File(pref.getTraceFileDir()));
	//Apro la finestra di scelta
    int returnVal=fc.showOpenDialog(this);
    if(returnVal==JFileChooser.APPROVE_OPTION)
     {
      File file=fc.getSelectedFile();
	  filePath.setText(file.getPath());
     } 	
   } 
 }//Fine actionPerformed
}//Fine classe OpenTraceFIleDlg
