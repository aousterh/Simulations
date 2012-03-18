package datarecorders;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import viewer.NodeTimeInfo;

import math.Point2d;
import models.Node;


public class RecNode 
 {
  private Node node;	
  private Vector timeInfo;
  
  public RecNode(Node node)
   {
    this.node=node;
    timeInfo=new Vector();
   }//Fine RecNode
  
  public void recPosition() 
   {
	//System.out.println(node.getPosition().toString());
	timeInfo.add(new NodeTimeInfo(node.getPosition().clonate(),
			                       node.getAntennaRadius(),
			                       node.getColor()));
   }//Fine recPosition
  
  public Vector getTimeInfo()
   { return timeInfo; }
  
  public int getTimeInfoSize()
   { return timeInfo.size(); }
  
  public String toXml()
   {
	String strXml=new String();
	//Scrivo le info sul nodo
	strXml+=infoNodeToXml();  
	//Scrivo le info sulle posizioni del nodo
	strXml+=timeInfoToXml();
	//Chiudo il tag del nodo
	strXml+="</Node>";
    return strXml;
   }//Fine toXml
  
  public String infoNodeToXml()
   {
	String infoNode=new String();
	infoNode+="<Node radius=\""+node.getRadius()+
	          "\" antenna=\""+node.getAntennaRadius()+
	          "\" color=\""+node.getColor().getRGB()+
	          "\">\n";
	return infoNode;  
   }//Fine infoNode
  
 public String timeInfoToXml()
  {
   String strPos=new String();	 
   Iterator i=timeInfo.iterator();
   while(i.hasNext())
	{
	 //Point2d nextPoint=(Point2d)i.next();
	 //strPos+=" <Pos x=\""+nextPoint.x+"\" y=\""+nextPoint.y+"\"/>\n" ;
	 NodeTimeInfo nextInfo=(NodeTimeInfo)i.next();  
	 strPos+=nextInfo.toXml();
	}
      
   //System.out.println(strPos);
   return strPos;
  }//positionsToXml
}//Fine classe RecNode
