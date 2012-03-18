package viewer;

import java.awt.Color;
import java.util.Vector;
import math.Point2d;


public class ViewNode extends GraphNode
  {
   private Vector timeInfo;
      
   public ViewNode(float radius)
    {
	 super(new Point2d(0,0),radius);  
	 timeInfo=new Vector();
    }//Fine viewNode 
   
   public ViewNode(float radius,float antennaRadius,Color color)
    {
	 this(radius);
	 this.antennaRadius=antennaRadius;
	 this.color=color;
	}//Fine viewNode
   
  public Vector getTimeInfo()
   { return timeInfo; } 
  
  public int  getTimeInfoSize()
   { return timeInfo.size(); }
  
  
  public void addNodeTimeInfo(NodeTimeInfo newInfo)
   { timeInfo.add(newInfo); }
  
  public NodeTimeInfo getTimeInfo(int index)
   {
	if((index>=0)&&(index<timeInfo.size()))
	 { return (NodeTimeInfo)timeInfo.elementAt(index);}
	else
	 { return new NodeTimeInfo(new Point2d(0,0),1,Color.BLUE);} 
   }//Fine setPosition 
  
public void setActPosition(float fIndex,int iIndex) 
 {
  //Calcolo il parametro per l'interpolazione  
  float alpha=fIndex-iIndex;
	
  //Prelevo le info  del nodo nei due istanti successivi
  NodeTimeInfo info=getTimeInfo(iIndex);
  NodeTimeInfo infoP1=getTimeInfo(iIndex+1);
  
  //Prendo le posizioni
  Point2d prevPos=info.getPos();
  Point2d nextPos=infoP1.getPos();
	
  //Calcolo le posizioni dei nodi al tempo attuale
  pos.x=(1-alpha)*prevPos.x+(alpha)*nextPos.x;
  pos.y=(1-alpha)*prevPos.y+(alpha)*nextPos.y;

  //Aggiorno il colore e il raggio dell'antenna 
  antennaRadius=info.getAntennaRadius();
  color=info.getColor();
 }//Fine setActPosInter
}//Fine classe viewnode
