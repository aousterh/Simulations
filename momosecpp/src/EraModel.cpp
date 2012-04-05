#include "EraModel.h"

#include<stdlib.h>
#include<time.h>

#include"EraNode.h"
#include"Node.h"



//Costruttore e distruttore
EraModel::EraModel(int numNodes,float nr,float ar,bool isPhy,float pauseTime,
		   float coloringInterval,float vMin,float vMax)
 { 
  setName("EraModel");
  //Imposto il seme per generare i numeri casuali
    srand((unsigned)time(NULL));
  
  this->numNodes=numNodes;
  this->nodeRadius=nr;
  this->antennaRadius=ar;  	  
	  
  setPhysicalProp(isPhy);
  setThinkerProp(true);
	
  //Variabili generali modello
  localModelTime=0;
  scenario=NULL;
  this->coloringInterval=coloringInterval;//Intevallo di tempo tra due colorazioni
   
      
  //Variabili modello PursueHide
   referencePoint.set(0,0);
   this->vMax=vMax;//Velocita'  massima
   this->vMin=vMin;//velocita' minima
   this->pauseTime=pauseTime;//Pausa del punto di referimento per l'HidePursueModel
   pause=true;
   target.set(0,0);
   localTime=0; 
 }//Fine costruttore

EraModel::~EraModel(){}



void EraModel::setup(Scenario *scenario,SimTime *simtTime) 
 {
  Node *newNode;
  for(int  i=0;i<numNodes;i++)
    {
     //float p=2+i*1.5f;  
	 float p=5+i;
	
     newNode=new EraNode(Point2D(p,p),0.5f,&referencePoint,scenario->getHotSpots());
     newNode->setAntennaRadius(antennaRadius);
     nodes.push_back(newNode);
   } 	
   
   //Salvo un rierimento allo scenario	
   this->scenario=scenario;
   //Calcolo punto medio
   Point2D middle=middlePoint(nodes);
   //Assegno punto medio al referencePoint
   referencePoint.set(middle.x,middle.y); 
	
    //Salvo un riferimento agli hot spot
    hotSpots=scenario->getHotSpots();	
   
   //Imposto per la prima volta i ruoli
   roleAssignament();
  
 }//Fine setup 


Point2D EraModel::middlePoint(vector<Node*> localNodes)
  {
   float xMin=0,xMax=0,yMin=0,yMax=0;  
	
   Node *node;
   vector<Node*>::iterator i;
   if(localNodes.size()>0)
    {
      node=localNodes.at(0);
     Point2D position=node->getPosition();
     xMin=xMax=position.x;
     yMin=yMax=position.y;
	  
     for(i=localNodes.begin();i!=localNodes.end();i++)
      {
       node=*i;
       Point2D nextPos=node->getPosition();
	    
       if(nextPos.x<xMin)
	   xMin=nextPos.x;  
       if(nextPos.x>xMax)
	   xMax=nextPos.x; 
	     
       if(nextPos.y<xMin)
	   yMin=nextPos.y;  
       if(nextPos.y>yMax)
	   yMax=nextPos.y;
      }		
     }
	
     //Calcolo coordiante medie
     float middleX=(xMin+xMax)/2;
     float middleY=(yMin+yMax)/2;
	
     //Ritorno il punto medio
     return Point2D(middleX,middleY);  
   }//Fine middlePoint

 vector<Node*> EraModel::cloneVector(vector<Node*> inputNodes)
  {
   vector<Node*> outVect;
   Node *newNode;
   vector<Node*>::iterator i;
   for(i=nodes.begin();i!=nodes.end();i++)
    {
     outVect.push_back(*i); 
    }   

   return outVect; 
  }//Fine cloneVector

vector<Node*> EraModel::removeFrom(vector<Node*> from,vector<Node*> inputVect)
 {
   vector<Node*> outVect;
   vector<Node*>::iterator i;
   vector<Node*>::iterator j;
   bool found=false;
   for(i=from.begin();i!=from.end();i++)
     {
       found=false; 
       j=inputVect.begin();
       while((j!=inputVect.end())&&(found==false))
	{
	  if(*i==*j)
	    found=true;   
	 j++;
        } 
       if(found==false)
         outVect.push_back(*i); 
     }
   
  return outVect;
 }//Fine removeFrom 
 


void EraModel::roleAssignament()
 {
   vector<Node*> V=cloneVector(nodes);
   if(V.size()>0)
    {   
     //Trovo il primo insieme indipendente massimale 	 
     vector<Node*> I=generateIndMaximalSet(V,0);
     //Tolgo dall'insieme principale l'insime massimale
     if(I.size()>0)
      {
	V=removeFrom(V,I);
       //Assegno il tipo all'insieme I
       setType(I,1);
      }
   
     if(isIndMaximal(V)==false)
      {  
       //Trovo il secondo insieme indipendente massimale I1 
       vector<Node*> I1=generateIndMaximalSet(V,0);
       if(I1.size()>0)
         { 	
	   V=removeFrom(V,I1);
          setType(I1,2);
         } 
   } 
  
   //Assegno il tipo all'insieme V-I-I1
   setType(V,3);
   }
 }//Fine roleAssignment 
 
 void EraModel::think(SimTime *simTime)
  {
   //Eseguo la funzione di "coloring"	 
   coloring(simTime);  	
   
   //Eseguo il movimento del modello PursueHideModel
   moveReferencePoint(simTime);  
  }//Fine think 


void EraModel::coloring(SimTime *simTime)
 {
  if(localModelTime>coloringInterval)
   {   
    roleAssignament();
    localModelTime=0;
   } 
   //Incremento il tempo locale  
   localModelTime+=simTime->getDT();
 }//Fine coloring



 bool EraModel::isVisible(Node *node,Node *targetNode)
  {
   float distance=Point2D::distance(node->getPosition(),targetNode->getPosition());
   if(distance<=node->getAntennaRadius())
      return true;
     else
      return false;  
  }//Fine isVisible

void EraModel::setType(vector<Node*> inputSet,int type)
  {
    vector<Node*>::iterator i; 
    for(i=inputSet.begin();i!=inputSet.end();i++)
      ((EraNode*)*i)->setType(type);  
  }//Fine setType





vector<Node*> EraModel::generateIndMaximalSet(vector<Node*> inputSet,int index)
 {
   vector<Node*> indMaximalSet;
  
   if(inputSet.size()==0)
     return indMaximalSet;
   
  if((index<0)||(index>=inputSet.size()))
	index=0;
  
  //Metto il primo elemento nel vettore
  indMaximalSet.push_back(inputSet.at(index));

  bool found; int j;
  Node *inputNode;
  Node *indMaximalNode;
  
  //Faccio un ciclo per determinare l'insieme massimale
  for(int i=0;i<inputSet.size();i++)
   {
    j=0;
    found=false;
	
    if(i!=index)
    { 	
	 while((j<indMaximalSet.size())&&(found==false))
	  {
	   inputNode=inputSet.at(i);
	   indMaximalNode=indMaximalSet.at(j);
	   found=isVisible(indMaximalNode,inputNode);
	   j++;
	  }
	
     if(found==false)
      {indMaximalSet.push_back(inputSet.at(i));}
    }
   }	  
  return indMaximalSet;
 }//Fine generateIndMaxSet






bool EraModel::isIndMaximal(vector<Node*> inputSet)
  {
   if(inputSet.size()==0)
     return false;
   
   bool found=false;
   int i=0,j=0;
   Node *node;
   Node *targetNode;
  while((i<inputSet.size())&&(found==false))
    {
     node=inputSet.at(i);
     j=0;
	 
     while((j<inputSet.size())&&(found==false))
      {
       if(j!=i)
	{  
	 targetNode=inputSet.at(j);
	 found=isVisible(node,targetNode);
	}
        j++;
     }	 
     i++;
    } 
   return !found;
  }//Fine isIndMaximal
















////////////////////////////
//Metodi per il nomadicModel
void EraModel::moveReferencePoint(SimTime *simTime)
  {
   if(pause==true)
      {
       if(localTime>pauseTime)	  
       {
	pause=false;
	//Calcolo un nuovo target
	generateTarget();
        moveToTarget(simTime);//Mi muovo verso il target
       }	 
     }
    else
     {moveToTarget(simTime);} //Mi muovo verso il target  

   //Incremento il tempo locale
   localTime+=simTime->getDT();  	 
  }//Fine moveReferencePoint


void EraModel::moveToTarget(SimTime *simTime) 
  {
   //Creo un vettore verso il target
   Vector2D toTarget=target-referencePoint;
   //Normalizzo il vettore verso il target
   Vector2D actV=toTarget.unitVector();
   //Calcolo la velocita'  e la direzione del nodo
   float velocity=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin);
   actV*=velocity;
   //Rapporto la velocita'  all'intervallo di tempo scelto
   actV*=simTime->getDT();
   
	 
   //Calcolo la distanza dal target	 
   float distance=Point2D::distance(referencePoint,target); 
	 
   if(distance<=actV.getLength())
    {referencePoint=referencePoint+toTarget;}  
   else
    {referencePoint=referencePoint+actV;} 
	
   float finalDist=Point2D::distance(referencePoint,target);
   //Se sono arrivato a destinazione mi fermo
   if(finalDist<=1)
    {
     pause=true;
     localTime=0;
    }   
  }//Fine moveToTarget

 void EraModel::generateTarget()
  {
   target.set((float)(((float)rand()/(float)(RAND_MAX))*scenario->getWidth()),
	      (float)(((float)rand()/(float)(RAND_MAX))*scenario->getHeight()));  
     
  }//Fine generate Target  
