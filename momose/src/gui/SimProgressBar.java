package gui;

import javax.swing.JProgressBar;

public class SimProgressBar extends JProgressBar 
 {
  private float minValue;
  private float maxValue;
    
  public SimProgressBar()
   {
	super(0,100);  
	this.minValue=0;
	this.maxValue=100;
	setSize(500,20);
	setStringPainted(true);
	setIndeterminate(false);
   }//Fine costruttore
  
  public void set(float minValue,float maxValue)
   {
	this.minValue=minValue;
    this.maxValue=maxValue;
   }//Fine setSetupFase
  
  public void setValue(float value)
   {
	float alpha=value/maxValue;
	int valueCent=(int)(alpha*100);
	super.setValue(valueCent);
	setString("Simulation progress ("+valueCent+"%)");
	//setString(valueCent+"%");
   }//SetSimulation 
  
  public void reset(String text)
   {
    reset();	
    setString("End simulation");
   }//Fine reset
  
  public void reset()
   {
	this.minValue=0;
    this.maxValue=100;
    setValue(0);
    setMinimum(0);
    setMaximum(100);
    setString("System ready");
   }//Fine reset
 }//Fine classe SimProgressBar
