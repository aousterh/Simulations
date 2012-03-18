package engine;

import javax.media.opengl.GL;
import math.Point2d;
import viewer.SimArenaGL;

public abstract class ScenarioElement 
 { 
  protected Point2d pos;	
  
  public ScenarioElement(Point2d pos)
   {this.pos=pos;}
    
  public Point2d getPosition()
   {return pos;}
  
   //Draw per l'ArenaSwing
  //public abstract void draw(Graphics g,SimArena arena);
  //Draw Per l'arenaGL
  public abstract void draw(GL gl,SimArenaGL arena);
  
  public abstract String toXml();
 }//Fine interfaccia GraphElement
