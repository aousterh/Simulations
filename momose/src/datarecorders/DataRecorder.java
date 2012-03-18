package datarecorders;

import java.util.Vector;

import engine.Scenario;
import engine.SimTime;


public abstract class DataRecorder 
 {
  private String name;	
  
  public DataRecorder()
   { name=new String(""); }
	
  public void setName(String name)
   {this.name=name;}
 
  public String getName()
   {return name;}
 
 public abstract void setup(Vector models,Scenario scenario,SimTime time);
 public abstract void record(SimTime time);
 public abstract void close(); 
}//Fine DataRecorder
