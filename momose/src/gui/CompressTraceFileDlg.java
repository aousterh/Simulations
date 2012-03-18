package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import utils.MomoseFileFilter;
import utils.Preferences;
import utils.TraceFileCompressor;

public class CompressTraceFileDlg extends JDialog implements ActionListener
 {
  private JTextField inputText; 	
  private JFileChooser inputFc;
  private JButton openInputChooserButton;
  private JFileChooser outputFc;
  private JButton openOutputChooserButton;
  private JTextField outputText;
  
  private JButton okButton;
  private JButton cancelButton;
  private boolean compression;
    
   
  public CompressTraceFileDlg(Preferences preferences,boolean compression)
	  {
	   this.compression=compression;	 
	   setSize(450,200);
	   if(compression==true)
	    setTitle("Compress trace-file dialog");
	   else
		setTitle("Decompress trace-file dialog");   
	   //E' una finestra modale
	   setModal(true);
	   //Non puo' essere ridimensionata
	   setResizable(true);
	   //Sempre in primo piano
	   setAlwaysOnTop(true);
	   //Setto il layout
	   setLayout(new BorderLayout());
	   add(getOkCancPanel(),BorderLayout.SOUTH);
	   add(getClientPanel(preferences),BorderLayout.CENTER);
	  }//Fine costruttore
  
   public String getInputFileName()
    {return inputText.getText();} 
	
   public String getOutputFileName()
    {return outputText.getText();} 
   
  private JPanel getOkCancPanel()
	 {
	  JPanel okCancPanel=new JPanel();
	  okCancPanel.setLayout(new FlowLayout());
	  String str;
	  if(compression==true)
		 str="Compress";
	  else
		 str="Decompress";
	  okButton=new JButton(str);
	  okButton.addActionListener(this);
	  okCancPanel.add(okButton);
	  
	  cancelButton=new JButton("Cancel");
	  cancelButton.addActionListener(this);
	  okCancPanel.add(cancelButton);
	  return okCancPanel; 	
	 }//Fine getSaveCancPanel
	 
	private JPanel getClientPanel(Preferences preferences) 
	 {
	  JPanel clientPanel=new JPanel();
	  clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
	  
	  String strIn;
	  String strOut;
	  String typeIn;
	  String typeOut;
	  
	  if(compression==true)
	   { 
		strIn="File to compress (xml):";
		strOut="Compressed file (gz): ";
		typeIn="tracefile";
		typeOut="compressed";
	   }	
	  else
	   {  
		strIn="File to decompress (gz):";
		strOut="Decompressed file (xml):";
		typeIn="compressed";
		typeOut="tracefile";
	   }	 
	  
	  clientPanel.add(new JLabel(strIn));
	  inputText=new JTextField(30);
	  clientPanel.add(inputText);
		
	  openInputChooserButton=new JButton("Browse...");
	  openInputChooserButton.addActionListener(this);
	  clientPanel.add(openInputChooserButton);
	  
	  inputFc=new JFileChooser();
	  inputFc.setCurrentDirectory(new File(preferences.getTraceFileDir()));
	  inputFc.setDialogTitle("Select a trace-file ");
	  inputFc.setFileFilter(new MomoseFileFilter(typeIn));
	  
	  clientPanel.add(new JLabel(" "));
	  
	  clientPanel.add(new JLabel(strOut));
	  outputText=new JTextField(30);
	  clientPanel.add(outputText);
	  
	  openOutputChooserButton=new JButton("Browse...");
	  openOutputChooserButton.addActionListener(this);
	  clientPanel.add(openOutputChooserButton);
	  
	  outputFc=new JFileChooser();
	  outputFc.setCurrentDirectory(new File(preferences.getTraceFileDir()));
	  outputFc.setDialogTitle("Select a trace-file ");
	  outputFc.setFileFilter(new MomoseFileFilter(typeOut));
	  
	   
	  return clientPanel;
	 }//Fine getDefOptPanel

	public  void actionPerformed(ActionEvent e)
	 {
	  if(e.getSource()==cancelButton)
	   { this.dispose(); }
	  if(e.getSource()==okButton)
		 {
		  if(ctrlEmptyField())
		   {  
		    compDecFunc();
		    this.dispose();
		   } 
		 }	
		
	  if(e.getSource()==openInputChooserButton)
	   {
	    int returnVal=inputFc.showOpenDialog(this);
	    if(returnVal==JFileChooser.APPROVE_OPTION)
	     {
	      File file=inputFc.getSelectedFile();
		  inputText.setText(file.getPath());
		 } 	
	    } 
	  
	  if(e.getSource()==openOutputChooserButton)
	   {
	    int returnVal=outputFc.showOpenDialog(this);
	    if(returnVal==JFileChooser.APPROVE_OPTION)
	     {
	      File file=outputFc.getSelectedFile();
		  outputText.setText(file.getPath());
		 } 	
	    }  
  }//Fine actionPerformed
	
private boolean ctrlEmptyField()
 {
	
	
  if(inputText.getText().equals(""))
   {
	JOptionPane.showMessageDialog(this,"Input file field empty!",
                                  "Warning",JOptionPane.WARNING_MESSAGE);
	return false;
   } 
  
  if(outputText.getText().equals(""))
   {
	JOptionPane.showMessageDialog(this,"Output file field empty!",
                                 "Warning",JOptionPane.WARNING_MESSAGE);
	return false;
   } 
  return true;	
 }//Fine ctrlEmptyField	
	
	
 private void compDecFunc()
  {
   String operation;	 
   if(compression==true)
	 operation="Compression";
    else
	 operation="Decompression";   
	 
   File inputFile=new File(inputText.getText());
   
   if((inputFile.isDirectory())||(!inputFile.canRead())||(!inputFile.canWrite()))
    {
	 JOptionPane.showMessageDialog(this,"Unable to open file "+inputText.getText()+"!\n" +
                                   operation+" aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE);
	} 
   else
   {  
    File outputFile=new File(outputText.getText());
    if(outputFile.isDirectory())
     {
      JOptionPane.showMessageDialog(this,"Unable to create file "+outputText.getText()+"\n" +
                                     operation+" aborted!",
                                      "Error",JOptionPane.ERROR_MESSAGE);
     }
    else
     {
      if(compression==true)
        {   
    	 boolean res=TraceFileCompressor.compress(inputFile, outputFile);
    	 if(res==true)
    	   JOptionPane.showMessageDialog(this,operation+" of "+inputText.getText()+" completed!",
                     operation,JOptionPane.INFORMATION_MESSAGE);	   	 
    	 else	 
    	   JOptionPane.showMessageDialog(this,"Error in "+operation+" "+inputText.getText()+"\n" +
                      "Operation aborted!",
                      "Error",JOptionPane.ERROR_MESSAGE);	
    	} 
       else
        {
    	 boolean res=TraceFileCompressor.decompress(inputFile,outputFile);
       	 if(res==true)
       	   JOptionPane.showMessageDialog(this,operation+" of "+inputText.getText()+" completed!",
                        operation,JOptionPane.INFORMATION_MESSAGE);	   	 
       	 else	 
       	   JOptionPane.showMessageDialog(this,"Error in "+operation+" "+inputText.getText()+"\n" +
                         "Operation aborted!",
                         "Error",JOptionPane.ERROR_MESSAGE);	
        }  
      }	
    }
  }//Fine comDecFunc
}//Fine CompressTraceFileDlg