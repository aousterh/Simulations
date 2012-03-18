package datarecorders.btgRecorder;


import java.util.Iterator;
import java.util.Vector;

import math.Point2d;
import models.Node;


public class BTGraph 
 {
  Vector verts; 
  int val[];
  int id;
  int nCC;
  int diameter;
  int matrixDiameter;
  int maxDegree;
  int averageDegree;
  int numNodesCompCon;
  int numNodesLargCompCon;
  
	
  public BTGraph()
   {
	verts=new Vector();
	id=0;
	nCC=0;
	diameter=0;
	maxDegree=0;
	averageDegree=0;
	numNodesLargCompCon=0;
	numNodesLargCompCon=0;
   }//Fine costruttore
  
  public void addVertice(BTGVert newVert)
   {verts.add(newVert);}
  
  public int getNumVertices()
   {return verts.size();}
  
  public Vector getVertices()
   {return verts;}
  
  public int getNumCompCon()
   {return nCC;}
  
  public int getNumNodesLargConComp()
  {return numNodesLargCompCon;}
  
  public int getDiameter()
   {return diameter;}
  
  public int getDiameterMatrix()
   {
	int value=(matrixDiameter-((int)(Math.random()*4)));
	if(value>0)
	 return value;
	else 
	  return 0;
   }
  
  public int getMaxDegree()
   {return maxDegree;}
  
  public int getAverageDegree()
   {return averageDegree;}
  
  private void printValVect()
   {
	System.out.print("val=");
	
	for(int i=0;i<val.length;i++)  
	 {
	  System.out.print("|"+val[i]);	
	  //System.out.print("|"+i);
	 }
	//System.out.print("|");
	System.out.println("|");
   }
   
  
  public int bfsVisit(int k)
   {
	int l=0;
	boolean found=false;
	
	BTGVert t=null;
	numNodesCompCon=0;
	
	IntQueue queue=new IntQueue();
	queue.put(k);
	
	while(!queue.isEmpty())
	 {
	  k=queue.get();
	  val[k]=++id;
	  
	  numNodesCompCon++;
	 	  
	  found=false;
	  
	  t=(BTGVert)verts.elementAt(k);
	  
	  //Prelevo gli elementi dal grafo C
	  Iterator i=t.getCset().iterator();
	  while(i.hasNext())
	   {
		Node nextNode=(Node)i.next();
		int nodeId=Integer.parseInt(nextNode.getId());
		
		if(val[nodeId-1]==-2)
		 {
		  if(found==false)
		   {l+=1; found=true;} 
			
		  queue.put(nodeId-1);
		  val[nodeId-1]=-1;
		 }	
	   }	  
	  
	 }	
	return l;
   }//Fine bfsVisit
  
  
  
  public void bfsSearch(int startIndex)
   {
	int k=0;
	nCC=0;//numero Componenti connesse
	id=0;
	diameter=0;
	numNodesLargCompCon=0;
	
	int liv=0;
	
	//Imposto il vettore per il bsf
	val=new int[verts.size()];
	for(k=0;k<verts.size();k++)
	 val[k]=-2;
	
	//Eseguo l'algoritmo di ricercaBSF
	int numVerts=verts.size();
	for(k=0;k<numVerts;k++)
	 {
	  int index=(k+startIndex)%numVerts;
	  
	  if(val[index]==-2)
	   {
		//Aumento il numero di componenti connesse  
		nCC++;  
			  
		//Faccio la visita bfs
		liv=bfsVisit(k);
				
		//Calcolo il numero di nodi della più grande componente connessa
		if(numNodesCompCon>numNodesLargCompCon)
			 numNodesLargCompCon=numNodesCompCon;	
			
		//Calcolo del diametro 
		if(liv>diameter)
		 diameter=liv;	
	  }  
	 }
 }//Fine bsfSearch
  
  
 public void calcNgraph() 
  {
   for(int i=0;i<verts.size();i++)
    {
	 //Prendo il prossimo vertice del grafo  
	 BTGVert vert=(BTGVert)verts.elementAt(i);
	 //Resetto l'insieme N
	 vert.setN.removeAllElements();
	 
	 //Scorro gli altri vertici
	 for(int j=0;j<verts.size();j++)
	  {
	   BTGVert vertTarget=(BTGVert)verts.elementAt(j);
		if(i!=j)
		 {
          //Calcolo la distanza le posizioni dei nodi	
		  float distance=Point2d.distance(vert.getNodePosition(),vertTarget.getNodePosition());	
           //Grafo orientato	
		  if(distance<=vert.getNodeAR())
		  //Grafo semplice	
		  //if((distance<=vert.getNodeAR())||(distance<=vertTarget.getNodeAR()))
		   {
	    	//Aggiugo il target all'insieme N
	    	vert.addEdgeToN(vertTarget.getNode());  
	       }
		 }	
	   }	
	 }
   }//Fine calcNset
 
 
 public void calcCgraph(int c) 
  {
   maxDegree=0;
   int sumDegree=0;
   
   Iterator i=verts.iterator();
   while(i.hasNext())
    {
     //Prendo il prossimo vertice del grafo  
	 BTGVert nextVert=(BTGVert)i.next();  
	 //Calcolo il suo insieme C
	 nextVert.createCedges(c,verts);
	}
   }//Fine calcCset
 
 
///////////////////////////
 public int calcDiameterWithMartix()
  {
   int mSize=verts.size();
   
   //Stampo i vertici del grafo
   //printVerts();
   
   //Creo la matrice dest
   int dist[][];
   dist=createAdiacetMatrix(mSize);
   
   //System.out.println("\ndist");
   //printMatrix(dist,mSize);
   
   matrixDiameter=0;
   BTGVert vert=null;
   int value=0;
   
   //Inizializzo matrix diameter
   for(int i=0;i<mSize;i++)
	for(int j=0;j<mSize;j++)
	  if(dist[i][j]==1)
       	matrixDiameter=dist[i][j];
   
  //Ciclo principale
  for(int k=0;k<mSize;k++)
   {   
	for(int i=0;i<mSize;i++)
	 {
	  for(int j=0;j<mSize;j++)
	   {
		if((dist[i][k]+dist[k][j]>=0)&&(dist[i][k]+dist[k][j]<=Integer.MAX_VALUE))
		  value=dist[i][k]+dist[k][j];
		 else
		  value=Integer.MAX_VALUE;  
		
		//System.out.println(" ");
		//System.out.println("dist["+i+"]["+j+"]="+dist[i][j]); 
		
        //if((dist[i][k]+dist[k][j])<dist[i][j])
		if((value)<dist[i][j])
         {
          //System.out.println("---");	
          //System.out.println("dist["+i+"]["+j+"]="+dist[i][j]); 	
          //System.out.println("dist["+i+"]["+k+"]+dist["+k+"]["+j+"]="+(dist[i][k]+dist[k][j]));
          
          dist[i][j]=dist[i][k]+dist[k][j];
                   
          //System.out.println("Updated entry: ["+i+"]["+j+"]="+dist[i][j]);
         }
       }                            
	 }
   }
   
  //System.out.println("\ndist");
  //printMatrix(dist,mSize); 
  
  //Estraggo il cammino massimo
  for(int i=0;i<mSize;i++)
   for(int j=0;j<mSize;j++)
   {   
    if((matrixDiameter<dist[i][j])&&(dist[i][j]<Integer.MAX_VALUE))
  	  matrixDiameter=dist[i][j];
   } 
  
   
   return matrixDiameter;
  }//Fine calcDiameterWithMartix
 
 public int[][] createAdiacetMatrix(int mSize)
  {
   int adiacentMatrix[][];
   adiacentMatrix=new int[mSize][mSize];	 
   BTGVert vert=null;
   
   for(int i=0;i<mSize;i++)
	    for(int  j=0;j<mSize;j++)
	      {	
	    	adiacentMatrix[i][j]=Integer.MAX_VALUE;
	    	if(i==j)
	    	 adiacentMatrix[i][j]=0;
	      }	
   
   Node nextNode=null;
   
   for(int i=0;i<mSize;i++)
	{ 	
	 vert=(BTGVert)verts.get(i);
	 Iterator j=vert.getCset().iterator();
	   while(j.hasNext())
		{
		 nextNode=(Node)j.next(); 
		 adiacentMatrix[i][Integer.parseInt(nextNode.getId())-1]=1;	 
		}	 	 
	  } 
   return adiacentMatrix;
  }//Fine createAdiacent Matrix
 
 
 
 public void printMatrix(int matrix[][],int mSize)
 {
  System.out.print("  ");
  for(int k=0;k<mSize;k++)
   System.out.print(" "+k+"  ");    
  System.out.print("\n");   
  
  for(int i=0;i<mSize;i++)
   {	  
	System.out.print(i+" ");
	
    for(int j=0;j<mSize;j++)
     {
      if(matrix[i][j]!=Integer.MAX_VALUE)	
	   System.out.print("["+matrix[i][j]+"] ");
      else
       System.out.print("[ ] ");  
     }
    System.out.print("\n");  
   } 
 }//Fine printMAtrix
  
 
  public void printVerts()
  {
   Iterator i=verts.iterator();
   BTGVert vert;
   Node nextNode;	   
   
   while(i.hasNext())
    {
	 vert=(BTGVert)i.next();
	 System.out.print(vert.toString()+" -> ");
	 Iterator j=vert.getCset().iterator();
	 while(j.hasNext())
	  {
	   nextNode=(Node)j.next(); 
	   System.out.print((Integer.parseInt(nextNode.getId())-1)+"-");	 
	  }	 
	 System.out.print("\n");
	}  
  }//Fine printVerts 
//////////////////////
  
 public int calcMaxDegree()
  {
   Iterator i=verts.iterator();
   BTGVert nextVert=null; 
   int degree=0;
   int maxDegree=0;
   while(i.hasNext())
    {
 	nextVert=(BTGVert)i.next();
 	degree=countExtEdge(nextVert);
 	if(degree>maxDegree)
 		maxDegree=degree;
    }	   
   return maxDegree;	
  }//Fine calcMaxDegree   


  
 private boolean searchID(BTGVert vert,int id)
  {
   Iterator i=vert.getCset().iterator();
   Node nextNode=null;
   while(i.hasNext())
    {
 	nextNode=(Node)i.next();
 	if(Integer.parseInt(nextNode.getId())==id)
 	 return true;	
    }
   return false;
  }//Fine searchNode

 private int countExtEdge(BTGVert refVert)
  {
   BTGVert nextVert=null;
   int count=0;
   Iterator i=verts.iterator();
   while(i.hasNext())
    {
 	nextVert=(BTGVert)i.next();
 	
 	if(refVert!=nextVert)
 	 if(searchID(refVert,nextVert.getID())||(searchID(nextVert,refVert.getID())))
 	   count++; 
    }	
   return count;	
  }//Fine countExtEdge  
 
}//Fine classe GTBGraph
