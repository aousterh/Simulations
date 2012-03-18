package viewer;

import java.awt.event.WindowEvent;

import javax.swing.JPanel;

public class PlayerGL extends Player 
 {
  public PlayerGL(int scale) 
   {super("Simulation player (OpenGL)",scale);}

  protected JPanel getSimPanel() 
   { 
	simArena=new SimArenaGL(scale); 
	return  simArena;
   }//Fine getSimPanel
 }//Fine classe PlayerGL
