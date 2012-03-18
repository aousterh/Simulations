package models;




import java.util.Vector;

import engine.Scenario;
import engine.SimTime;
import engine.ThinkerObject;


public abstract class Model implements ThinkerObject
 {
  private String name;	
  protected Vector nodes;	
  protected boolean isPhysical;
  protected boolean isThinker;
	
  public Model()
   {
	name=new String("");  
    nodes=new Vector();
    isPhysical=false;
    isThinker=false;
   }//Fine costruttore
  
  public void setName(String name)
   {this.name=name;}
  
  public String getName()
   {return name;}
  
  public Vector getNodes()
  {return nodes;}
  
  public boolean isPhysical()
   {return isPhysical;}
  
  public void setPhysicalProp(boolean flag)
   {isPhysical=flag;}
  
  public boolean isThinker()
   {return isThinker;}
 
 public void setThinkerProp(boolean flag)
  {isThinker=flag;}
  
  public abstract void setup(Scenario scenario,SimTime time);
 }//Fine classe Model
