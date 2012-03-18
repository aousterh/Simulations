package models.nomadicModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import models.ModelConfigDlg;

public class NomadicConfigDlg extends ModelConfigDlg
{
 protected JSpinner spinnerVMin;
 protected JSpinner spinnerVMax;
 protected JSpinner spinnerAlpha;	
 protected JSpinner spinnerPauseTime;
	
 public NomadicConfigDlg()
  {
	setSize(400,220);  
	setTitle("Nomadic model config dialog");
  }//Fine costruttore
 
 public void setVMin(float v)
  {spinnerVMin.setValue((double)v);}

 public void setVMax(float v)
  {spinnerVMax.setValue((double)v);}

 public void setAlpha(float t)
  {spinnerAlpha.setValue((double)t);}
 
 public void setPauseTime(float t)
  {spinnerPauseTime.setValue((double)t);}
 

 public float getVMin()
  {return (float)(((Double)spinnerVMin.getValue()).doubleValue());}
 
 public float getVMax()
  {return (float)(((Double)spinnerVMax.getValue()).doubleValue());}
 
 public float getAlpha()
  {return (float)(((Double)spinnerAlpha.getValue()).doubleValue());}
 
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
	clientPanel.add(createNomadicPanel()); 
	
	return clientPanel;
  }//Fine createClientPanel
 
 public JPanel createNomadicPanel()
  {
	JPanel nomadicPanel=new JPanel();
	nomadicPanel.setLayout(new BoxLayout(nomadicPanel,BoxLayout.Y_AXIS));
	
	
   //Pannello velocita'
	JPanel velPanel=new JPanel(new FlowLayout());
	nomadicPanel.add(velPanel);
	
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
	nomadicPanel.add(targetPanel);
	
	targetPanel.add(new JLabel("Pause time:"));
	SpinnerNumberModel modelInterval=new SpinnerNumberModel(1.0,0.0,100.0,0.1);
	modelInterval.setMaximum(null);
	spinnerPauseTime=new JSpinner(modelInterval);
	((JSpinner.DefaultEditor)spinnerPauseTime.getEditor()).getTextField().setColumns(4);
	targetPanel.add(spinnerPauseTime); 
	
	targetPanel.add(new JLabel("Group attraction:"));
	SpinnerNumberModel modelAttraction=new SpinnerNumberModel(0.5f,0.0,1.0f,0.1f);
	spinnerAlpha=new JSpinner(modelAttraction);
	((JSpinner.DefaultEditor)spinnerAlpha.getEditor()).getTextField().setColumns(4);
	targetPanel.add(spinnerAlpha);
	
	return nomadicPanel;  
  }//Fine createNomadicPanel
}//Fine classe NomadicConfigDlg