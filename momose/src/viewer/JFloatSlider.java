package viewer;

import javax.swing.*;


public class JFloatSlider extends JSlider
 {
  static final float FLOAT_MINIMUM=0.0f;
  static final float FLOAT_MAXIMUM=100.0f;
  static final int PRECISION_MULTIPLIER=100;
  
  public JFloatSlider()
   {
    super();
    setFloatMinimum(FLOAT_MINIMUM);
    setFloatMaximum(FLOAT_MAXIMUM);
    setFloatValue(0);
   }//Fine costruttore
  
   public void reset()
    {
	 setFloatMinimum(FLOAT_MINIMUM);
	 setFloatMaximum(FLOAT_MAXIMUM);
	 setFloatValue(0);	   
    }//Fine reset

  
  public JFloatSlider(float min, float max, float val)
   {
    super();
    setFloatMinimum(min);
    setFloatMaximum(max);
    setFloatValue(val);
   }//Fine costruttore

  public float getFloatMaximum() 
   { return( getMaximum()/FLOAT_MAXIMUM ); }

  
  public float getFloatMinimum() 
   { return( getMinimum()/FLOAT_MAXIMUM ); }

  
  public float getFloatValue() 
   { return( getValue()/FLOAT_MAXIMUM ); }

  
  public void setFloatMaximum(float max) 
   { setMaximum((int)(max*PRECISION_MULTIPLIER)); }

    public void setFloatMinimum(float min) 
  { setMinimum((int)(min*PRECISION_MULTIPLIER)); }

  public void setFloatValue(float val) 
   { setValue((int)(val*PRECISION_MULTIPLIER)); }
  
  public void setFloatData(float min, float max, float val)
   {
	setFloatMinimum(min);
	setFloatMaximum(max);
	setFloatValue(val);	  
   }//Fine setFloatValues 
  
}//Fine classe jFloatSlider
