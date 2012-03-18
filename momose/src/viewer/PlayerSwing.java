package viewer;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayerSwing extends Player 
 {
  public PlayerSwing(int scale) 
   {
	super("Simulation player (Swing)",scale);
   }//Fine PlayerSwing

  protected JPanel getSimPanel()
   {
    //Creo l'area di simulazione
    simArena=new SimArenaSwing(scale);
    simArena.revalidate();
    //Agiungo lo scrollpane
    JScrollPane scrollPane=new JScrollPane(simArena);
    //Creo il pannello di sfondo
    JPanel backgroundPanel=new JPanel(new BorderLayout());
    backgroundPanel.add(scrollPane,BorderLayout.CENTER);
    //Ritorno il pannello
    return backgroundPanel;
   }//Fine getSimPanel
 }//Fine PlayerSwing
