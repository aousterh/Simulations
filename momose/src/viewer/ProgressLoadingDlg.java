package viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressLoadingDlg extends JDialog 
 {
  private JLabel textLabel;
  private JProgressBar progressBar;
 	
  public ProgressLoadingDlg(String fileName)
   {
    this.setLayout(new BorderLayout());
	JPanel mainPanel=new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	this.add(mainPanel,BorderLayout.CENTER);
	
	//Setto le proprietà della finestra
	setSize(new Dimension(300,75));
	setTitle("Loading...");
	setResizable(false);
    setLocationRelativeTo(getParent());
			
	//Creo il testo
	JPanel textPanel=new JPanel(new BorderLayout());
    this.textLabel=new JLabel(fileName);
    textPanel.add(textLabel,BorderLayout.WEST);
    mainPanel.add(textPanel);
    
    //Creo la barra di progressione
	progressBar=new JProgressBar(0,100);
	progressBar.setSize(100,25);
	progressBar.setIndeterminate(true);
	progressBar.setString("Loading "+fileName+" in progress..."); 
	//Aggiungo la barra al pannello principale
	mainPanel.add(progressBar);
   }//Fine costruttore
 }//Fine ProgressBarDialog
