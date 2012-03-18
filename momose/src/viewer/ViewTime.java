package viewer;

import engine.SimTime;

public class ViewTime extends SimTime 
 {
  public float baseTime;	
	
  public ViewTime(float T,float dt,int N)
   {
	super(T,dt,N);
	baseTime=0.01f;
   }//Fine costruttore
  
 public void enableBaseTime()
  {dt=baseTime;}
	 
 public synchronized void impTime(float t)
  { 
	//Setto il tempo  
	setTime(t);
	
	//Calcolo il ciclo
	if(dt!=0)
	 loop=(int)(getTime()/getDT());
	else
	 loop=0; 	
  }//Fine impTime
	 
  protected void upTime()
   { 
    impTime(time+dt);
    //Controllo che il tempo non vada fuori dai limiti
    if(time<0)
  	  impTime(0);
    else
     if(time>T)
	  impTime(T);	 
   }//Fine upTime

  public void upTime(int v)
   { 
    impTime(time+(dt*v));
    //Controllo che il tempo non vada fuori dai limiti
    if(time<0)
     impTime(0);
    else
     if(time>T)
	  impTime(T);
   }//Fine upTime
}//Fine ViewTime



//Vecchia classe
/*public class ViewTime 
{
 private float T; 
 private float dt;
 private int N;
	 
 int loop;
 float time;
	 
 public ViewTime(float T,float dt,int N)
  {
   this.T=T;
   this.dt=dt;
	this.N=N;
	  
   this.time=0;
   this.loop=0;
  }//Fine costruttore
	 
 public float getT()
  { return T; }
 
 public float getDT()
  { return dt; }
	 
 public int getN()
  { return N; }
  
 public float getTime()
  { return time; } 
 
 public int getLoop()
  { return loop; }
 
 private void setTime(float t)
  {
   //Arrotondo il valore a due cifre decimali	 
   time=(float)(Math.round(t*100.0f)/100.0f);	 
  }//Fine roundTime
 
 public synchronized void impTime(float t)
  { 
	//Setto il tempo  
	setTime(t);
	
	//Calcolo il ciclo
	if(dt!=0)
	 loop=(int)(getTime()/getDT());
	else
	 loop=0; 	
  }//Fine impTime
	 
protected void upTime()
 { 
  impTime(time+dt);
  //Controllo che il tempo non vada fuori dai limiti
  if(time>T)
	  impTime(T);	 
 }//Fine upTime

protected void upTime(int v)
 { 
  impTime(time+(dt*v));
  //Controllo che il tempo non vada fuori dai limiti
  if(time>T)
	 impTime(T);
 }//Fine upTime
}//Fine ViewTime*/


