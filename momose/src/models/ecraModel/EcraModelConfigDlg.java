package models.ecraModel;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import models.ModelConfigDlg;

public class EcraModelConfigDlg extends ModelConfigDlg
 {
  protected JSpinner spinnerVMin;
  protected JSpinner spinnerVMax;
  protected JSpinner spinTargetAttr;	
  protected JSpinner spinnerColoringInterval;	
  protected JSpinner spinnerTargetZones;
  protected JSpinner spinnerRoles;
  protected JCheckBox checkMobileRefPoint;
	
  public EcraModelConfigDlg()
  {
   setSize(350,300);  
   setTitle("ECRA model config dialog");
  }//Fine costruttore
  
  public void setVMin(float v)
   {spinnerVMin.setValue((double)v);}

  public void setVMax(float v)
   {spinnerVMax.setValue((double)v);}

  public void setTargetAttr(float t)
   {spinTargetAttr.setValue((double)t);}
  
  public void setColoringInterval(float t)
  {spinnerColoringInterval.setValue((double)t);}
  
  public void setTargetZones(int z)
  {spinnerTargetZones.setValue((int)z);}

 public void setRoles(int r)
  {spinnerRoles.setValue((int)r);}
 
 public void setMobileRefPoint(boolean flag)
  {checkMobileRefPoint.setSelected(flag);}
 
  public float getVMin()
   {return (float)(((Double)spinnerVMin.getValue()).doubleValue());}
	  
  public float getVMax()
   {return (float)(((Double)spinnerVMax.getValue()).doubleValue());}
	  
  public float getColoringInterval()
   {return (float)(((Double)spinnerColoringInterval.getValue()).doubleValue());}
  
  public float getSpinTargetAttr()
  {return (float)(((Double)spinTargetAttr.getValue()).doubleValue());}
  
  public boolean getMobileRefPoint()
   {return checkMobileRefPoint.isSelected();}
  
  public int getTargetZones()
   {
	Integer intValue=(Integer)spinnerTargetZones.getValue();
	int value=intValue.intValue();  
    //int value=(int)(((Integer)spinnerTargetZones.getValue()).intValue());
    if(value<4)
     return 4;
    else
	 return value; 
   }//fine getTargetValue

 public int getRoles()
  {
   Integer intValue=(Integer)spinnerRoles.getValue();
   int value=intValue.intValue();
   //int value=(int)(((Integer)spinnerRoles.getValue()).intValue());
   if((value<2)||(value>3))
	return 2;
   else
	return value;
  }//fine getRoles
  
 public JPanel createClientPanel() 
   {
	JPanel clientPanel=new JPanel();
	clientPanel.setLayout(new BoxLayout(clientPanel,BoxLayout.Y_AXIS));
	
	//Creo il pannello con i parametri comuni
	clientPanel.add(getCommonPanel(false));		
    //Creo il pannello con i parametri di default 
    
		
	return clientPanel;
   }//Fine createClientPanel
  
  public JPanel getCommonPanel(boolean withBorder)
   {
	//Pannello delle opzioni principali  
	JPanel commonPanel=new JPanel();
	commonPanel.setLayout(new BoxLayout(commonPanel,BoxLayout.Y_AXIS));
	if(withBorder==true)
	 commonPanel.setBorder(BorderFactory.createTitledBorder("Common settings"));
	
	//Numero di nodi 
	commonPanel.add(getNumNodesPanel());
	
	//Ragggio del nodo e dell'antenna
	JPanel radiusPanel=new JPanel(new FlowLayout());
	commonPanel.add(radiusPanel);
	radiusPanel.add(getNodeRadiusPanel());
	radiusPanel.add(getAntennaRadiusPanel());
		
	//physical thinker panel
	JPanel pausePhyPanel=new JPanel(new FlowLayout());
	pausePhyPanel.add(new JLabel("Target attraction:"));
	SpinnerNumberModel modelInterval=new SpinnerNumberModel(0.5,0.0,1,0.1);
	modelInterval.setMaximum(null);
	spinTargetAttr=new JSpinner(modelInterval);
	((JSpinner.DefaultEditor)spinTargetAttr.getEditor()).getTextField().setColumns(4);
	pausePhyPanel.add(spinTargetAttr);
	
	commonPanel.add(pausePhyPanel);
	pausePhyPanel.add(getPhysicalCheckBox());
	
	
	
    //Pannello velocita'
	JPanel velPanel=new JPanel(new FlowLayout());
	commonPanel.add(velPanel);
	
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
	
    //coloringInterval time panel
	JPanel coloringPanel=new JPanel(new FlowLayout());
	commonPanel.add(coloringPanel);
	
	coloringPanel.add(new JLabel("Coloring interval:"));
	SpinnerNumberModel coloringModel=new SpinnerNumberModel(1.0,0.0,100.0,0.1);
	modelInterval.setMaximum(null);
	spinnerColoringInterval=new JSpinner(coloringModel);
	((JSpinner.DefaultEditor)spinnerColoringInterval.getEditor()).getTextField().setColumns(4);
	coloringPanel.add(spinnerColoringInterval);
	
	
    //coloringInterval time panel
	JPanel numPanel=new JPanel(new FlowLayout());
	commonPanel.add(numPanel);
	
	numPanel.add(new JLabel("roles:"));
	SpinnerNumberModel numModel=new SpinnerNumberModel(2,2,3,1);
	spinnerRoles=new JSpinner(numModel);
	((JSpinner.DefaultEditor)spinnerRoles.getEditor()).getTextField().setColumns(4);
	numPanel.add(spinnerRoles);
	
	numPanel.add(new JLabel("target zones:"));
	SpinnerNumberModel refModel=new SpinnerNumberModel(4,4,100,2);
	refModel.setMaximum(null);
	refModel.setStepSize(2);
	spinnerTargetZones=new JSpinner(refModel);
	((JSpinner.DefaultEditor)spinnerTargetZones.getEditor()).getTextField().setColumns(4);
	numPanel.add(spinnerTargetZones);
	
	
	JPanel refPanel=new JPanel(new FlowLayout());
	commonPanel.add(refPanel);
	checkMobileRefPoint=new JCheckBox("Mobile reference point");
	checkMobileRefPoint.setSelected(true);
	refPanel.add(checkMobileRefPoint);
	
	
	//Ritorno il pannello
	return commonPanel;
  }//Fine default
  
 }//Fine classe EraModelConfigDlg
