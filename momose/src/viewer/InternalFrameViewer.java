package viewer;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class InternalFrameViewer extends JInternalFrame implements InternalFrameListener
 {
  private Viewer viewer;
  
  public InternalFrameViewer(Viewer viewer)
   {
	this.viewer=viewer;
	//Aggiungo il viewer come pannello
	add(viewer);
	setTitle(viewer.getName());
	addInternalFrameListener(this);
   }//Fine costruttore
  
   public void internalFrameClosing(InternalFrameEvent e) 
    {viewer.windowClosing();}

   public void internalFrameActivated(InternalFrameEvent e) {}
   public void internalFrameClosed(InternalFrameEvent e) {}
   public void internalFrameDeactivated(InternalFrameEvent e) {}
   public void internalFrameDeiconified(InternalFrameEvent e) {}
   public void internalFrameIconified(InternalFrameEvent e) {}
   public void internalFrameOpened(InternalFrameEvent e) {}
 
 }//Fine classe InternalFrame Viewer
