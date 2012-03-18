package models.randomWalkModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import models.ModelConfigDlg;

public class RandomWalkConfigDlg extends ModelConfigDlg
 {
  protected JSpinner spinnerVMin;
  protected JSpinner spinnerVMax;
  protected JSpinner spinnerPauseTime;	
	
  public RandomWalkConfigDlg()
   {
	setSize(350,220);  
	setTitle("Random Walk model config dialog");
   }//Fine costruttore
  
  public void setVMin(float v)
   {spinnerVMin.setValue((double)v);}
 
  public void setVMax(float v)
   {spinnerVMax.setValue((double)v);}
 
  public void setPauseTime(float t)
   {spinnerPauseTime.setValue((double)t);}
  
 
  public float getVMin()
   {return (float)(((Double)spinnerVMin.getValue()).doubleValue());}
  
  public float getVMax()
   {return (float)(((Double)spinnerVMax.getValue()).doubleValue());}
  
  public float getPauseTime()
   {return (float)(((Double)spinnerPauseTime.getValue()).doubleValue());}
  
  public JPanel createClientPanel() 
   {
	JPanel clientPanel=new JPanel();
	clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
	
	//Creo il pannello con i parametri di default 
	clientPanel.add(getNumNodesPanel());
	
	JPanel radiusPanel=new JPanel(new BorderLayout());
	clientPanel.add(radiusPanel);
	radiusPanel.add(getNodeRadiusPanel(),BorderLayout.WEST);
	radiusPanel.add(getAntennaRadiusPanel(),BorderLayout.EAST);
	
	JPanel physPanel=new JPanel(new FlowLayout());
	clientPanel.add(physPanel);
	physPanel.add(getPhysicalCheckBox());
	
    //Creo il pannello con i parametri di default 
	clientPanel.add(createRandomWalkPanel()); 
	
	return clientPanel;
   }//Fine createClientPanel
  
  public JPanel createRandomWalkPanel()
   {
	JPanel randomWalkPanel=new JPanel();
	randomWalkPanel.setLayout(new BoxLayout(randomWalkPanel,BoxLayout.Y_AXIS));
	
	
    //Pannello velocita'
	JPanel velPanel=new JPanel(new FlowLayout());
	randomWalkPanel.add(velPanel);
	
	velPanel.add(new JLabel("Min velocity:"));
	SpinnerNumberModel modelMin=new SpinnerNumberModel(1.0,0.0,100.0,0.1);
	modelMin.setMaximum(null);
	spinnerVMin=new JSpinner(modelMin);
	((JSpinner.DefaultEditor)spinnerVMin.getEditor()).getTextField().setColumns(3);
	velPanel.add(spinnerVMin);
	
	velPanel.add(new JLabel("Max velocity:"));
	SpinnerNumberModel modelMax=new SpinnerNumberModel(1.0,0.0,100.0,0.1);
	modelMax.setMaximum(null);
	spinnerVMax=new JSpinner(modelMax);
	((JSpinner.DefaultEditor)spinnerVMax.getEditor()).getTextField().setColumns(3);
	velPanel.add(spinnerVMax);
	  
    //interval time panel
	JPanel intervalPanel=new JPanel(new FlowLayout());
	randomWalkPanel.add(intervalPanel);
	
	intervalPanel.add(new JLabel("Pause time:"));
	SpinnerNumberModel modelInterval=new SpinnerNumberModel(1.0,0.0,100.0,0.1);
	modelInterval.setMaximum(null);
	spinnerPauseTime=new JSpinner(modelInterval);
	((JSpinner.DefaultEditor)spinnerPauseTime.getEditor()).getTextField().setColumns(4);
	intervalPanel.add(spinnerPauseTime);
	
	return randomWalkPanel;  
   }//Fine createRandomWalkPanel
 }//Fine classe RandomWalkConfigDlg
