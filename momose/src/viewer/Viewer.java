package viewer;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class Viewer extends JPanel implements ChangeListener,ItemListener
 {
  protected JSpinner scaleSpinner;
  protected JCheckBox drawAntennaBox;
  protected JCheckBox drawGraphBox;
  protected JCheckBox drawHotSpotBox; 
  protected JCheckBox drawNodeIdBox; 
  
  //Altezze e larghezze della simArena
  protected SimArena simArena;
  protected float scale;
  
  protected String name;
  
  public Viewer(String name,float scale)
   {
    this.scale=scale;
    this.name=name;
    
    //Creo gli elementi dell'interfaccia
    setInteface();
  }//Fine costruttore
  
 public SimArena getArena()
  {return simArena;} 
 
 public String getName()
  {return name;} 
 
 private void setInteface()
  {
   //Setto il layout	 
   setLayout(new BorderLayout());
   
   //Aggiungo i pannelli 
   addPanels();
   
   //Aggiungo il pannello di simulazione
   this.add(BorderLayout.CENTER,getSimPanel());
   
   //SetInitialScale
   //setInitialScale(simPanel,100,100);
  }//Fine setInterface
 
 //Metodi astratti
 protected abstract void addPanels(); 
 protected abstract JPanel getSimPanel();
 protected abstract void windowClosing();
 protected abstract JPanel getOptionPanel();
 
protected void setInitialScale(JPanel simPanel,float sWidth,float sHeight)
 {
  //Setto la scala iniziale
  float pWidth=simPanel.getWidth();
  float pHeight=simPanel.getHeight();
  	
  float x=pWidth/sWidth;
  float y=pHeight/sHeight;
		
  System.out.println("pWidth="+pWidth+"pHeight="+pHeight);
  System.out.println("sWidth="+sWidth+"sHeight="+sHeight);
  System.out.println("x="+x+"y="+y);
		
  if(x<y)
	{
	 if(x>1)	
	  scale=x;
	 else
	  scale=1;	  
	}		
  else
	{
	 if(y>1)	
	  scale=x;
   else
	  scale=1;	
   }	

  scaleSpinner.setValue(new Double(scale));
  System.out.println("scale="+scale);
  simArena.changeScale(scale); 
 }//Fine setInitialScale
 
 
 public void stateChanged(ChangeEvent e) 
  {
   if(e.getSource()==scaleSpinner)
   {
	Double value=(Double)scaleSpinner.getValue();  
	simArena.changeScale(value.floatValue()); 
   }
 }//Fine stateChanged

public void itemStateChanged(ItemEvent e) 
 {
  if(e.getSource()==drawAntennaBox)
   {
	simArena.setDrawAntenna(drawAntennaBox.isSelected());
	simArena.reDraw();
   }  
  
 if(e.getSource()==drawGraphBox)
   {
	simArena.setDrawGraph(drawGraphBox.isSelected());
	simArena.reDraw();
   }   
 
 if(e.getSource()==drawHotSpotBox)
  {
   simArena.setDrawHotSpot(drawHotSpotBox.isSelected());
   simArena.reDraw();
  }  
 
 if(e.getSource()==drawNodeIdBox)
  {
   simArena.setDrawNodeId(drawNodeIdBox.isSelected());
   simArena.reDraw(); 
  }  
 
 }//Fine itemStateCHanged
}//Fine classe Viewer
