package models.ecraModel;


import java.util.Iterator;
import java.util.Vector;
import engine.Scenario;
import engine.SimTime;

import math.Point2d;
import models.Model;
import models.Node;



public class EcraModel extends Model
 {
  private int numNodes;
  private float nodeRadius;
  private float antennaRadius;
  
  //Variabili modello	
  private Scenario scenario;	
  private float localModelTime;
  private float coloringInterval;
  
  private float vMax;
  private float vMin; 	 
  private float targetAttr;
  
  private int roles;
  private int numRefPoint;
  private Point2d refPoint1;
  private Point2d refPoint2;
  private Point2d refPoint3;
  private Point2d refPoints[][];
  private boolean estractRefPoint;
  
  
  public EcraModel()
   {setModel(10,0.5f,10.0f,false,5.0f,30.0f,0,0.5f,0.8f,2,4,true); }//Fine cotruttore 
  
  public void setModel(int numNodes,float nr,float ar,boolean isPhy,float pauseTime,
		               float coloringInterval,float vMin,float vMax,float targetAttr,
		               int roles,int targetAreas,boolean mobileRefPoint)
  {
   this.numNodes=numNodes;
   this.nodeRadius=nr;
   this.antennaRadius=ar; 
   
   this.roles=roles;
   this.numRefPoint=targetAreas;
   
   refPoints=null;
   
   setPhysicalProp(isPhy);
   setThinkerProp(true);
	
   localModelTime=0;
   scenario=null;
   this.coloringInterval=coloringInterval;//Intevallo di tempo tra due colorazioni (modificabile)
   
   refPoint1=new Point2d(25,25);
   refPoint2=new Point2d(75,75);
   refPoint3=new Point2d(75,25);
   this.vMin=vMin;
   this.vMax=vMax;
   this.targetAttr=targetAttr;
   estractRefPoint=mobileRefPoint;
  }//Fine cotruttore 
  
  
 public void setup(Scenario scenario,SimTime time) 
  {
   float px;
   float py;
   
   for(int i=0;i<numNodes;i++)
    {
     //float p=2+i*1.5f;  
	 //float p=5+i;
	 px=(float)(Math.random()*scenario.getWidth());
	 py=(float)(Math.random()*scenario.getHeight());  
	
	 Node newNode=new EcraNode(new Point2d(px,py),nodeRadius,scenario,vMin,vMax,targetAttr);
	 newNode.setAntennaRadius(antennaRadius);
     nodes.add(newNode);
    } 	
   
   //Salvo un rierimento allo scenario	
   this.scenario=scenario;
   
	//Crea i reference point 
    createRefPoints();
    
    //Assegna i ref point di default
    setDefaultRefPoint((int)(numRefPoint/2));
    
    //Seleziona i ref point
    if(estractRefPoint)
     estractRefPoints();
    
   //Imposto per la prima volta i ruoli
   roleAssignament();
  }//Fine generateNodes
 
 
 
 private void estractRefPoints()
  {
   int n=(int)(numRefPoint/2);
   boolean cicle=true;
   int loops=20;
   int i=0;
   
   do
    {
     refPoint1=estract(n);	   
     refPoint2=estract(n);	   
     refPoint3=estract(n);
	 
	 if((refPoint1!=refPoint2)&&
	    (refPoint1!=refPoint3)&&
	    (refPoint2!=refPoint3))
	   cicle=false; 
	 
	 i++;
    }   
   while((cicle==true)&&(i<loops));
   
   if(cicle==true)
	 setDefaultRefPoint(n);  
 }//Fine esctractRefPoint
 
 private void  setDefaultRefPoint(int n)
  {
   refPoint1=refPoints[0][0];
   refPoint2=refPoints[n-1][n-1];
   refPoint3=refPoints[0][n-1];	 
  }//Fine setDefaultRefPoint
 
 private Point2d estract(int num)
  {
   int refi=(int)(Math.random()*num);
   int refj=(int)(Math.random()*num);
   return refPoints[refi][refj];
  }//Fine estractRefPoints
 
 private void createRefPoints()
  {
   if((numRefPoint%2)!=0)
	 numRefPoint++;
   
   int n=(int)(numRefPoint/2);
   
   refPoints=new Point2d[n][n];
      
   float startW=(int)(scenario.getWidth()/numRefPoint);
   float startH=(int)(scenario.getHeight()/numRefPoint);
   float stepW=startW*2;
   float stepH=startH*2;
   
   Point2d newRefPoint=null;
     
   for(int i=0;i<n;i++)
    {
	 for(int j=0;j<n;j++)
	  {
	   newRefPoint=new Point2d(startW+(i)*stepW,startH+(j)*stepH);	 
	   refPoints[j][i]=newRefPoint;	 
	  } 
    }   
   
   /*for(int i=0;i<n;i++)
   {
	for(int j=0;j<n;j++)
	 {
	  System.out.print(refPoints[i][j]); 
	 }
	System.out.print("\n"); 
   }*/   
  }//Fine createRefPoints

 public void think(SimTime time)
  {
   //Eseguo la funzione di "coloring"	 
   coloring(time);  	
  }//Fine think
 
 private void coloring(SimTime time)
  {
   if(localModelTime>=coloringInterval)
	 {
	  //Assegno i ruoli 
	  roleAssignament();
	  
	  //Assegno i reference point
      if(estractRefPoint)
	    estractRefPoints();
	  
	  //resetto il timer
	  localModelTime=0;
	 } 
   //Incremento il tempo locale  
   localModelTime+=time.getDT();		 
  }//Fine coloring
 
 private void roleAssignament()
  {
   Vector V=(Vector)nodes.clone();
   
   if((V!=null)&&(V.size()>0))
    {   
     //Trovo il primo insieme indipendente massimale 	 
     Vector I=generateIndMaximalSet(V,0);
     //Tolgo dall'insieme principale l'insime massimale
     if(I!=null)
      {
       V.removeAll(I);
       //Assegno il tipo all'insieme I
       setType(I,1);
      }
   
     //System.out.println("E' massimale="+isIndMaximal(V));
     if(isIndMaximal(V)==false)
      {  
       //Trovo il secondo insieme indipendente massimale I1 
       Vector I1=generateIndMaximalSet(V,0);
       if(I1!=null)
         { 	
          V.removeAll(I1);
          setType(I1,2);
      } 
   } 
  
   //Assegno il tipo all'insieme V-I-I1
   //setType(V,3);  
   if(roles==2)  
    setType(V,2);
   else
	setType(V,3);  
  }
 }//Fine roleAssignement
 
 private Vector generateIndMaximalSet(Vector inputSet,int index)
 {
  Vector indMaximalSet=new Vector();
  
  if((inputSet.size()==0)||(inputSet==null))
   return null;
   
  if((index<0)||(index>=inputSet.size()))
	index=0;
  
  //Metto il primo elemento nel vettore
  indMaximalSet.add(inputSet.elementAt(index));

  boolean found; int j;
  
  //Faccio un ciclo per determinare l'insieme massimale
  for(int i=0;i<inputSet.size();i++)
   {
    j=0;
    found=false;
	
    if(i!=index)
    { 	
	 while((j<indMaximalSet.size())&&(found==false))
	  {
	   Node inputNode=(Node)inputSet.elementAt(i);
	   Node indMaximalNode=(Node)indMaximalSet.elementAt(j);
	   found=isVisible(indMaximalNode,inputNode);
	   j++;
	  }
	
    if(found==false)
     {
	  indMaximalSet.add(inputSet.elementAt(i));
	  //System.out.println("InputSet.index="+i);
	 }
    }
   }	  
  return indMaximalSet;
 }//Fine fine generateIndMaxSet
 
 private boolean isVisible(Node node,Node targetNode)
  {
   float distance=Point2d.distance(node.getPosition(),targetNode.getPosition());
   if(distance<=node.getAntennaRadius())
	  return true;
     else
	  return false;  
  }//Fine isVisible
 
 private void setType(Vector inputSet,int type)
  {
   Point2d rp=null;	 
   if(type==1)	 
    rp=refPoint1;
   if(type==2)	 
	rp=refPoint2;
   if(type==3)	 
	rp=refPoint3;
   
   Iterator i=inputSet.iterator();	 
   while(i.hasNext())
	 ((EcraNode)i.next()).setType(type,rp);  
  }//Fine setColor
 
 private boolean isIndMaximal(Vector inputSet)
  {
   if(inputSet.size()==0)
	   return false;
   
   boolean found=false;
   int i=0,j=0;
   
  while((i<inputSet.size())&&(found==false))
    {
	 Node node=(Node)inputSet.elementAt(i);
	 j=0;
	 
	 while((j<inputSet.size())&&(found==false))
	  {
	   if(j!=i)
	    {  
	     Node targetNode=(Node)inputSet.elementAt(j);
	     found=isVisible(node,targetNode);
		}
	   j++;
	  }	 
	 i++;
	} 
   return !found;
  }//Fine isIndMaximal
}//Fine classe EraModel
