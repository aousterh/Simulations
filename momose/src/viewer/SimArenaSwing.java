package viewer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.Iterator;
import java.util.Vector;



import engine.Building;
import engine.HotSpot;
import engine.ScenText;

import math.Point2d;



public class SimArenaSwing extends SimArena
 {
  public SimArenaSwing(float scale) 
   {
	super(scale);
    this.setPreferredSize(new Dimension((int)scale*100,(int)scale*100));
	revalidate();
    //Abilito il double buffer per questo pannello
	this.setDoubleBuffered(true);
   }//Fine costruttore
  
  public void reDraw() 
   {repaint();}
  
  public void updateNodes(Vector nodes)
   {
	this.nodes=nodes;  
	reDraw();
   }//Fine updateNodes

  public void paintComponent(Graphics g)
   {
	super.paintComponent(g);  
	drawBackground(g);
	//Disegno i building
	drawBuildings(g);
	//Disegno gli HotSpot
	drawHotSpots(g);
	
	//Disegno il testo
	drawText(g);
	
	//Disegno i nodi
	drawNodes(g);
   }//Fine paintComponent
  
 private void drawText(Graphics g)
  {
   if(scenario!=null)
	{		
	 Iterator i=scenario.getTexts().iterator();	
	 while(i.hasNext())
	  {
	   ScenText next=(ScenText)i.next();
	   //next.draw(g,this);
	  }
	}	 
	 
  }//Fine drawText
 
 private void drawNodes(Graphics g)
  {
	if(nodes.size()>0)
	{	
	 if(drawAntennaRadius==true)
	  {
	     	
	    for(int i=0;i<nodes.size();i++)
	     {
	      GraphNode node=(GraphNode)nodes.elementAt(i);
	      drawAntennaRadius(g,node);	
	     }
	  } 
	    
	 if(drawGraph==true)
	  { drawAntennaGraph(g,nodes); }	  
	 
	
	//System.out.println("Stampo i nodi...");
	for(int i=0;i<nodes.size();i++)
	 {
	  GraphNode node=(GraphNode)nodes.elementAt(i);
	  drawNode(g,node);	
	 }	
	}
 }//Fine drawNodes
 
 
  private void drawAntennaGraph(Graphics g, Vector nodes) 
   {
    for(int i=0;i<nodes.size();i++)
     {
	 GraphNode node=(GraphNode)nodes.elementAt(i);
	 for(int j=0;j<nodes.size();j++)
	   {
		GraphNode nodeTarget=(GraphNode)nodes.elementAt(j);
		if(i!=j)
		 {
		  Point2d nodePos=node.getPosition();
		  Point2d targetPos=nodeTarget.getPosition();
	      if(Point2d.distance(nodePos,targetPos)<=node.getAntennaRadius())
	       {
	    	Color color=calcAttenuation(node,nodeTarget);   
	        drawGraphArch(g,nodePos,targetPos,color);
	       }
		 }	
	   }	
	 }	   
  }//Fine drawAntenna Graph

 private void drawGraphArch(Graphics g,Point2d start, Point2d end, Color color)
  {
  Point2d vStart=simToVirtualCoords(start);
  Point2d vEnd=simToVirtualCoords(end);
  g.setColor(color);
  g.drawLine((int)vStart.x,(int)vStart.y,(int)vEnd.x,(int)vEnd.y);	
 }//Fine drawGraphArch


 private void drawAntennaRadius(Graphics g,GraphNode node) 
  {
	Point2d pr=simToVirtualCoords(node.getPosition()); 
	int x=(int)pr.getX();
	int y=(int)pr.getY();
	int virtualAntWidth=simToVirtualWidth(node.getAntennaRadius());
	int virtualAntHeight=simToVirtualHeight(node.getAntennaRadius());
	
	//Stampo l'antenna 
	g.setColor(Color.GREEN);
	g.drawOval(x-virtualAntWidth,
	           y-virtualAntHeight,
			   virtualAntWidth*2,
			   virtualAntHeight*2);	
 }//Fine drawAntennaRadius

private void drawNode(Graphics g,GraphNode node)
  {
	Point2d pr=simToVirtualCoords(node.getPosition()); 
	int x=(int)pr.getX();
	int y=(int)pr.getY();
	
	int virtualRadiusWidth=simToVirtualWidth(node.getRadius());
	int virtualRadiusHeight=simToVirtualHeight(node.getRadius());
	
	g.setColor(node.getColor());
	g.fillOval(x-virtualRadiusWidth,
			   y-virtualRadiusHeight,
			   virtualRadiusWidth*2,
			   virtualRadiusHeight*2);
		
	g.setColor(node.getColor());	
	g.drawOval(x-virtualRadiusWidth,
			   y-virtualRadiusHeight,
			   virtualRadiusWidth*2,
			   virtualRadiusHeight*2);
	
  }//Fine drawNode
 
 private void drawBackground(Graphics g)
  {
   /*g.setColor(Color.WHITE);	
   g.fillRect(0,0,vWidth,vHeight);
   g.setColor(Color.BLACK);	
   g.drawRect(0,0,vWidth,vHeight);*/
  }//Fine drawBackground 
 
 public void drawBuildings(Graphics g)
  {
	if(scenario!=null)
	{		
    Iterator i=scenario.getBuildings().iterator();	
    while(i.hasNext())
     {
	   Building next=(Building)i.next();
	   //next.draw(g,this);
	  }
	}
  }//Fine drawBuildings
 
 public void drawHotSpots(Graphics g)
  {
	if(drawHotSpot==true)
	 {	
    if(scenario!=null)
     { 	 
     Iterator i=scenario.getHotSpots().iterator();	
	  while(i.hasNext())
	   {
	    HotSpot nextSpot=(HotSpot)i.next();
	    //nextSpot.draw(g,this);
	   }
     } 
	} 
  }//Fine drawHotSpot
 
  protected void applyChange()
   {
	///this.setPreferredSize(new Dimension(vWidth,vHeight));	
	revalidate(); reDraw();
   }//Fine applyChange
  
  public Point2d simToVirtualCoords(Point2d simPoint)
   {
   /* Point2d  virtualPoint=new Point2d();
    virtualPoint.setX(simToVirtual(simPoint.getX(),
		                           0,sWidth,
		                           0,vWidth));

    virtualPoint.setY(simToVirtual(simPoint.getY(),
                                 0,sHeight,
                                 0,vHeight));
    return virtualPoint; */
	  return new Point2d();
   }//Fine simToVirtualCoords

  public int simToVirtualWidth(float val)
   { return 0;} //return simToVirtual(val,0,sWidth,0,vWidth); }

 public int simToVirtualHeight(float val)
  { return 0;} //simToVirtual(val,0,sHeight,0,vHeight); }

 public int simToVirtual(float val,float minSim,float maxSim,int minVirtual,int maxVirtual)
  {
   if(maxSim==minSim)
	  maxSim+=1;
   float fat=(1/(maxSim-minSim))*(maxVirtual-minVirtual);
   return (int)(val*fat);
  }//Fine simToVirtual
  
}//Fine SimArenaSwing