package engine;

public class SimTime
{
 protected float T; 
 protected float dt;
 protected int N;
 
 protected int loop;
 protected float time;
 
 public SimTime(float T,float dt,int N)
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
 
 public int getLoop()
  { return loop; }
 
 public float getTime()
  { return time; }
 
 protected void upTime()
  {
   setTime(time+dt);
   loop++;
  }//Fine upTime
 
 protected void downTime()
  {
   setTime(time-dt);
   loop--;
  }//Fine downTime
 
 public void reset()
  { time=0.0f; loop=0; }
 
 protected synchronized void setTime(float t)
  {
   //Arrotondo il valore a due cifre decimali	 
   time=(float)(Math.round(t*100.0f)/100.0f);
  }//Fine roundTime
 
 protected synchronized void setTime(int loop)
  {this.loop=loop;}
 
 public String toString()
  {return new String("T="+T+" dt="+dt+" N="+N);}
 
 public String dynamicTimetoXml()
  {
   return new String(" <SimTime Time=\""+time+"\" dt=\""+dt+"\" Loops=\""+loop+"\"/>\n");	 
  }//Fine dynamicTimetoXml
 
 public String staticTimeToXml()
  {
   return new String(" <SimTime Time=\""+T+"\" dt=\""+dt+"\" Loops=\""+N+"\"/>\n");	 
  }//Fine staticTimeToXml
 
}//Fine SimTime
