package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import engine.Scenario;

import utils.MomoseFileFilter;
import utils.Preferences;
import utils.ScenarioGenerator;


public class ScenarioGeneratorDlg extends JDialog implements ActionListener
 {
  private JTextField svgText; 	
  private JFileChooser svgFc;
  private JButton openSvgChooserButton;
  private JFileChooser scenarioFc;
  private JButton openScenarioChooserButton;
  private JTextField scenarioText;
  
  private JButton okButton;
  private JButton cancelButton;
  
  JRadioButton boundedRadio;
  JRadioButton boundlessRadio;
	
  public ScenarioGeneratorDlg(Preferences preferences)
   {
	setSize(450,240);
	setTitle("Scenario generator dialog");
	
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
	  
 private JPanel getOkCancPanel()
	 {
	  JPanel okCancPanel=new JPanel();
	  okCancPanel.setLayout(new FlowLayout());
	  
	  okButton=new JButton("Generate scenario");
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
	  
	  clientPanel.add(new JLabel("Svg file to convert (svg):"));
	  svgText=new JTextField(30);
	  clientPanel.add(svgText);
		
	  openSvgChooserButton=new JButton("Browse...");
	  openSvgChooserButton.addActionListener(this);
	  clientPanel.add(openSvgChooserButton);
	  
	  svgFc=new JFileChooser();
	  svgFc.setCurrentDirectory(new File(preferences.getScenariDir()));
	  svgFc.setDialogTitle("Select a svg file ");
	  svgFc.setFileFilter(new MomoseFileFilter("svg"));
	  
	  clientPanel.add(new JLabel(" "));
	  
	  clientPanel.add(new JLabel("Scenario to generate (xml):"));
	  scenarioText=new JTextField(30);
	  clientPanel.add(scenarioText);
	  
	  openScenarioChooserButton=new JButton("Browse...");
	  openScenarioChooserButton.addActionListener(this);
	  clientPanel.add(openScenarioChooserButton);
	  
	  scenarioFc=new JFileChooser();
	  scenarioFc.setCurrentDirectory(new File(preferences.getScenariDir()));
	  scenarioFc.setDialogTitle("Select a scenario-file ");
	  scenarioFc.setFileFilter(new MomoseFileFilter("scenario"));
	  
	  
	  //Pannello bordo
	  JPanel borderPanel=new JPanel(new FlowLayout());
	  clientPanel.add(borderPanel);
	  JLabel borderLabel=new JLabel("Border type:");
	  borderPanel.add(borderLabel);
	  boundedRadio=new JRadioButton("Bounded");
	  boundedRadio.setSelected(true);
	  boundedRadio.addActionListener(this);
	  borderPanel.add(boundedRadio);
	  boundlessRadio=new JRadioButton("Boundless");
	  boundlessRadio.setSelected(false);
	  boundlessRadio.addActionListener(this);
	  borderPanel.add(boundlessRadio);
	  
	  ButtonGroup borderModGroup=new ButtonGroup();
	  borderModGroup.add(boundedRadio);
	  borderModGroup.add(boundlessRadio);
	 
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
	  GenerateScenario();
	  this.dispose();
	 } 
	}	
			
 if(e.getSource()==openSvgChooserButton)
   {
    int returnVal=svgFc.showOpenDialog(this);
    if(returnVal==JFileChooser.APPROVE_OPTION)
     {
      File file=svgFc.getSelectedFile();
	  svgText.setText(file.getPath());
	 } 	
    } 
  
  
  if(e.getSource()==openScenarioChooserButton)
   {
    int returnVal=scenarioFc.showOpenDialog(this);
    if(returnVal==JFileChooser.APPROVE_OPTION)
     {
      File file=scenarioFc.getSelectedFile();
      scenarioText.setText(file.getPath());
	 } 	
   }  
  }//Fine actionPerformed
 
private boolean ctrlEmptyField()
 {
  if(svgText.getText().equals(""))
   {
	JOptionPane.showMessageDialog(this,"Svg-file field empty!",
                                  "Warning",JOptionPane.WARNING_MESSAGE);
	return false;
   } 
  
  if(scenarioText.getText().equals(""))
   {
	JOptionPane.showMessageDialog(this,"Scenario field empty!",
                                 "Warning",JOptionPane.WARNING_MESSAGE);
	return false;
   } 
  return true;	
 }//Fine ctrlEmptyField
		
private void GenerateScenario()
  {
   File svgFile=new File(svgText.getText());
   
   if((svgFile.isDirectory())||(!svgFile.canRead()))
    {
	 JOptionPane.showMessageDialog(this,"Unable to open file "+svgFile.getPath()+"\n" +
                                   "Operation aborted!",
                                   "Error",JOptionPane.ERROR_MESSAGE);
   } 
  else
   {  
    File scenarioFile=new File(scenarioText.getText());
    if(scenarioFile.isDirectory())
     {
      JOptionPane.showMessageDialog(this,"Unable to create file "+scenarioFile.getPath()+"\n" +
                                    "Operation aborted!",
                                     "Error",JOptionPane.ERROR_MESSAGE);
     }
    else
     {	
      int borderType;
      if(boundedRadio.isSelected())
        borderType=Scenario.BOUNDED;
       else
        borderType=Scenario.BOUNDLESS;  
      boolean res=false;
	  try 
		{
		 res=ScenarioGenerator.generate(svgFile,scenarioFile,borderType);
		} 
	   catch (FileNotFoundException e) {
	  res=false; 
	  JOptionPane.showMessageDialog(this,"Error in generation of "+scenarioText.getText()+"\n" +
                                          "Operation aborted!",
                                          "Error",JOptionPane.ERROR_MESSAGE);
	   } 
	  catch (IOException e) 
	   {
	  	res=false;	  
		JOptionPane.showMessageDialog(this,"Error in generation of "+scenarioText.getText()+"\n" +
                                          "Operation aborted!",
                                          "Error",JOptionPane.ERROR_MESSAGE);
	   }
	      
      if(res==true)
   	     JOptionPane.showMessageDialog(this,"Generation of "+scenarioText.getText()+" completed!",
                                          "Scenario generator",JOptionPane.INFORMATION_MESSAGE);	   	 
   	   else	 
   	    JOptionPane.showMessageDialog(this,"Error in generation of "+scenarioText.getText()+"\n" +
                                      "Operation aborted!",
                                      "Error",JOptionPane.ERROR_MESSAGE);
         
     }	
    }
  }//Fine GenerateScenario
 }//Fine ScenarioGeneratorDlg
