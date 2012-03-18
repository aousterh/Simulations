package gui;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public abstract class ConfigDlg extends JDialog implements ActionListener
 {
  private Container cp;//ContentPane della finestra
  private JButton closeButton; //Modificare con okButton
  private ConfigSimulationWnd parent;
 
  
  public ConfigDlg() 
   {
	//Setto le dimensioni della finestra
	setSize(200,100);
	//Centro la finestra
	//setLocationRelativeTo(getParent());
	//Setto il nome di default
	setTitle("Config dialog");
	//E' una finestra modale
    setModal(true);
    //Non puo' essere ridimensionata
    setResizable(true);
    //Sempre in primo piano
    setAlwaysOnTop(true);
    
    //Setto il ritorno di default
    //result=false;
	  
	//Preparo i due pannelli principali
	this.setLayout(new BorderLayout());
	add(createClosePanel(),BorderLayout.SOUTH);
	add(createClientPanel(),BorderLayout.CENTER);
   }//Fine ConfigDlg
  
 public void setParent(ConfigSimulationWnd parent)
  {this.parent=parent;}
  
 public JButton getCloseButton()
   {return closeButton;}
 
 public JPanel createClosePanel()
   {
	JPanel closePanel=new JPanel();
	closePanel.setLayout(new FlowLayout());
		
	closeButton=new JButton("Close");
	closeButton.addActionListener(this);
	closePanel.add(closeButton);
			
    return closePanel; 
   }//Fine createPanel
	  
  public  void actionPerformed(ActionEvent e)
   {
    if(e.getSource()==closeButton)
	   {this.setVisible(false);}
   }//Fine actionPerformed
   
  public abstract JPanel createClientPanel();
 }//Fine ConfigDlg
