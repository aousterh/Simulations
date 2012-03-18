package datarecorders.btgRecorder;

import java.util.Iterator;
import java.util.Vector;

import math.Point2d;
import models.Node;


public class BTGVert 
 {
  Node node;
  Vector setN;
  Vector setC;
	
  public BTGVert(Node node)
   {
	this.node=node;
	setN=new Vector();
	setC=new Vector();
   }//Fine costruttore
 
  public Vector getCset()
   {return setC;}
  
  public Vector getNset()
   {return setN;}
  
  
  public void addEdgeToN(Node newNode)
   {setN.add(newNode); }
  
  public void addEdgeToC(Node newNode)
   {setC.add(newNode); }
  
  public Node getNode()
   { return node;}
  
  public int getID()
  {return Integer.parseInt(node.getId());}
  
  public float getNodeAR()
   { return node.getAntennaRadius();}
  
  public Point2d getNodePosition()
   { return node.getPosition();}
  
  
 public void createCedges(int c,Vector verts)
  {
   Vector tempC=new Vector();
   Vector tempN=new Vector();
   
   //Inizializzo tempN
   for(int k=0;k<setN.size();k++)
    tempN.add(setN.elementAt(k));
   
   int i=0;
   int j=0;
		
   Node cNode=null;
   Node nNode=null;
		
   boolean found=false;
   int cSize=0;
   int cCount=0;
		  
	while((i<setC.size())&&(cSize<c))
	 {
	  //Estraggo il prossimo nodo da C
	  cNode=(Node)setC.elementAt(i);	
		
	  found=false;
	  while((j<setN.size())&&(found==false))
	   {
		//Estraggo il prossimo nodo da N  
		nNode=(Node)setN.elementAt(j); 
			
		//Se il nodo era gia' presente in C ce lo lascio
		if(cNode==nNode)
		 {
		  found=true;
		  
		  if(!searchID(Integer.parseInt(cNode.getId())))///
		  {  ///
		   tempC.add(cNode);
		   createEdge(Integer.parseInt(cNode.getId()),verts,node);
		   cCount++;////
		  } ////
		  
		  tempN.remove(nNode); 
		  cSize++; 
		 }
		j++;
	   }
	  i++;
	 }		
		
    //Riempo l'insieme C con gli elementi rimasti in N
	setC=addLastNEdges(tempN,tempC,cCount,c);	
   }//Fine searchCElems
 
 private void createEdge(int id,Vector verts,Node node)
  {
   Iterator i=verts.iterator();
   BTGVert nextVert=null;
   while(i.hasNext())
    {
	 nextVert=(BTGVert)i.next();
	 if(nextVert.getID()==id)
		nextVert.addEdgeToC(node); 
    }  
  }//Fine createEdge
 
 private Vector addLastNEdges(Vector tempN,Vector tempC,int cCount,int c)
  {
   int nSize=tempN.size();
   int i=cCount;//int i=tempC.size();
   
   while((i<c)&&(nSize>0))
    {
	 //Estraggo un elemento da tempN e lo metto in tempC 
	 int index=(int)(Math.random()*(nSize));
	 tempC.add(tempN.remove(index));
	 //Decremento il numero di elementi di tempN
	 nSize--;
	 i++;
	}
   
   //Ritorno il nuovo insieme C
   return tempC;
  }//Fine addNElems 
 
 private boolean searchID(int id)
 {
  Iterator i=getCset().iterator();
  Node nextNode=null;
  while(i.hasNext())
   {
	nextNode=(Node)i.next();
	if(Integer.parseInt(nextNode.getId())==id)
	 return true;	
   }
  return false;
 }//Fine searchNode
 
 
 }//Fine GBTVert