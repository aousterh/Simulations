package models.pursueModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import models.ModelConfigDlg;

public class PursueConfigDlg extends ModelConfigDlg
{
 protected JSpinner spinnerVMin;
 protected JSpinner spinnerVMax;
 protected JSpinner spinnerAlpha;	
 protected JSpinner spinnerTargetRadius;
	
 public PursueConfigDlg()
  {
	setSize(400,220);  
	setTitle("Pursue model config dialog");
  }//Fine costruttore
 
 public void setVMin(float v)
  {spinnerVMin.setValue((double)v);}

 public void setVMax(float v)
  {spinnerVMax.setValue((double)v);}

 public void setAlpha(float t)
  {spinnerAlpha.setValue((double)t);}
 
 public void setTargetRadius(float t)
  {spinnerTargetRadius.setValue((double)t);}
 

 public float getVMin()
  {return (float)(((Double)spinnerVMin.getValue()).doubleValue());}
 
 public float getVMax()
  {return (float)(((Double)spinnerVMax.getValue()).doubleValue());}
 
 public float getAlpha()
  {return (float)(((Double)spinnerAlpha.getValue()).doubleValue());}
 
 public float getTargetRadius()
  {return (float)(((Double)spinnerTargetRadius.getValue()).doubleValue());}
 
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
	clientPanel.add(createPursuePanel()); 
	
	return clientPanel;
  }//Fine createClientPanel
 
 public JPanel createPursuePanel()
  {
	JPanel pursuePanel=new JPanel();
	pursuePanel.setLayout(new BoxLayout(pursuePanel,BoxLayout.Y_AXIS));
	
	
   //Pannello velocita'
	JPanel velPanel=new JPanel(new FlowLayout());
	pursuePanel.add(velPanel);
	
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
	JPanel targetPanel=new JPanel(new FlowLayout());
	pursuePanel.add(targetPanel);
	
	targetPanel.add(new JLabel("Target radius:"));
	SpinnerNumberModel modelNodes=new SpinnerNumberModel(0.5f,0.01f,100.0f,0.1f);
	modelNodes.setMaximum(null);
	spinnerTargetRadius=new JSpinner(modelNodes);
	((JSpinner.DefaultEditor)spinnerTargetRadius.getEditor()).getTextField().setColumns(4);
	targetPanel.add(spinnerTargetRadius);  
	
	targetPanel.add(new JLabel("Target attraction:"));
	SpinnerNumberModel modelAttraction=new SpinnerNumberModel(0.5f,0.0,1.0f,0.1f);
	spinnerAlpha=new JSpinner(modelAttraction);
	((JSpinner.DefaultEditor)spinnerAlpha.getEditor()).getTextField().setColumns(4);
	targetPanel.add(spinnerAlpha);
	
	return pursuePanel;  
  }//Fine createRandomWalkPanel
}//Fine classe PursueConfigDlg