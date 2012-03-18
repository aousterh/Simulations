package models;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import gui.ConfigDlg;

public class ModelConfigDlg extends ConfigDlg
 {
  protected JSpinner spinnerNodes;
  protected JSpinner spinnerNR;
  protected JSpinner spinnerAR;
  protected JCheckBox isPhysical;
  protected JCheckBox isThinker;
	
  public ModelConfigDlg()
   {
	setSize(350,200);
	setTitle("Model config dialog");
   }//Fine costruttore
  
  public void setNumNodes(int num)
   {spinnerNodes.setValue((int)num);}
 
  public void setNodeRadius(float radius)
   { spinnerNR.setValue((double)radius);}
  
  public void setAntennaRadius(float radius)
   {spinnerAR.setValue((double)radius);}
 
  public void setPhysical(boolean flag)
   {isPhysical.setSelected(flag);}
  
 
  public void setThinker(boolean flag)
   {isThinker.setSelected(flag);} 
  
  
  public int getNumNodes()
   {return (int)(((Integer)spinnerNodes.getValue()).intValue());}
  
  public float getNodeRadius()
   {return (float)(((Double)spinnerNR.getValue()).doubleValue());}
   
  public float getAntennaRadius()
   {return (float)(((Double)spinnerAR.getValue()).doubleValue());}
  
  public boolean isPhysical()
   {return isPhysical.isSelected();}
  
  public boolean isThinker()
   {return isThinker.isSelected();} 
  
  
  public JPanel getDefaultOptPanel(boolean withBorder)
   {
	//Pannello delle opzioni principali  
	JPanel defOptPanel=new JPanel();
	defOptPanel.setLayout(new BoxLayout(defOptPanel,BoxLayout.Y_AXIS));
	if(withBorder==true)
	 defOptPanel.setBorder(BorderFactory.createTitledBorder("Default settings"));
	
	//Numero di nodi 
    defOptPanel.add(getNumNodesPanel());
	
	//Ragggio del nodo e dell'antenna
	JPanel radiusPanel=new JPanel(new FlowLayout());
	defOptPanel.add(radiusPanel);
	radiusPanel.add(getNodeRadiusPanel());
	radiusPanel.add(getAntennaRadiusPanel());
		
	//physical thinker panel
	JPanel phyThinkPanel=new JPanel(new FlowLayout());
	phyThinkPanel.add(getPhysicalCheckBox());
	phyThinkPanel.add(getThinkerCheckBox());
	defOptPanel.add(phyThinkPanel);
	
	//Ritorno il pannello
	return defOptPanel;
   }//Fine default
  
  public JPanel createClientPanel() 
   {
	JPanel clientPanel=new JPanel();
	clientPanel.add(getDefaultOptPanel(true));
	return clientPanel;
   }//Fine createClientPanel
 
 public JPanel getNumNodesPanel()
  {
	JPanel nodesPanel=new JPanel(new FlowLayout());
	nodesPanel.add(new JLabel("Number of nodes:"));
	SpinnerNumberModel modelNodes=new SpinnerNumberModel(10,0,100,1);
	modelNodes.setMaximum(null);
	spinnerNodes=new JSpinner(modelNodes);
	((JSpinner.DefaultEditor)spinnerNodes.getEditor()).getTextField().setColumns(4);
	nodesPanel.add(spinnerNodes);  
	return nodesPanel;  
  }//Fine createNumNodesPanel
 
 public JPanel getNodeRadiusPanel()
 {
  JPanel nrPanel=new JPanel(new FlowLayout());
  nrPanel.add(new JLabel("Node radius:"));
  SpinnerNumberModel modelNR=new SpinnerNumberModel(1.0,0.01,100.0,0.1);
  modelNR.setMaximum(null);
  spinnerNR=new JSpinner(modelNR);
  ((JSpinner.DefaultEditor)spinnerNR.getEditor()).getTextField().setColumns(4);
  nrPanel.add(spinnerNR);
  return nrPanel;  
 }//Fine createantennaRadiusPanel
 
 public JPanel getAntennaRadiusPanel()
  {
   JPanel arPanel=new JPanel(new FlowLayout());
   arPanel.add(new JLabel("Signal radius:"));
   SpinnerNumberModel modelAR=new SpinnerNumberModel(10.0,1,100.0,0.1);
   modelAR.setMaximum(null);
   spinnerAR=new JSpinner(modelAR);
   ((JSpinner.DefaultEditor)spinnerAR.getEditor()).getTextField().setColumns(4);
   arPanel.add(spinnerAR);
   return arPanel;  
  }//Fine createantennaRadiusPanel
 
 public JCheckBox getPhysicalCheckBox()
  {
   isPhysical=new JCheckBox("Is physical");
   isPhysical.setSelected(false);
	return isPhysical;
  }//Fine getThinkerCheckBox
 
 public JCheckBox getThinkerCheckBox()
  {
	isThinker=new JCheckBox("Is thinker");
	isThinker.setSelected(false);
	return isThinker;
  }//Fine getThinkerCheckBox  
  
  
}//Fine classe ModelConfigDlg
