#include "PhysicEngine.h"
#include "Point2D.h"
#include "Vector2D.h"
#include "ScenRectangle.h"
#include "float.h"

PhysicEngine::PhysicEngine(Scenario *scenario)
 {
  nodes=new vector<Node*>();
  physicalObjs=new vector<PhysicalObject*>();
  this->scenario=scenario;	
 }//Fine costruttore

PhysicEngine::~PhysicEngine(){}

void PhysicEngine::addPhysicalNodes(vector<Node*> *physicalNodes)
 {
  vector<Node*>::iterator i;
  for(i=physicalNodes->begin();i!=physicalNodes->end();i++)
   {physicalObjs->push_back((PhysicalObject*)*i);}	
 }//Fine addPhysicalObjs
 
void PhysicEngine::addBuildings(vector<Building*> *buildings)
 {
  vector<Building*>::iterator i;
  for(i=buildings->begin();i!=buildings->end();i++)
   {physicalObjs->push_back((PhysicalObject*)*i);}	
 }//Fine addPhysicalObjs 

void PhysicEngine::addNodes(vector<Node*> *newNodes)
 {
  vector<Node*>::iterator i;
  for(i=newNodes->begin();i!=newNodes->end();i++)
   {nodes->push_back(*i);}	
 }//Fine addNodes
 
void PhysicEngine::setBorder(Scenario *scenario)
 {
  if(scenario->getBorderType()==Scenario::BOUNDED)
	{
	 //Aggiungo  i quattro muri esterni
	 //Superiore	
	 physicalObjs->push_back(new ScenRectangle(Point2D(0,-2),
	                                           scenario->getWidth()+2,2));
     //destro	
	 physicalObjs->push_back(new ScenRectangle(Point2D(scenario->getWidth(),0),
			                                   2,scenario->getHeight()+2));
	 //inferiore	
	 physicalObjs->push_back(new ScenRectangle(Point2D(-2,scenario->getHeight()),
			                                   scenario->getWidth()+2,2));
	 //sinistra
	 physicalObjs->push_back(new ScenRectangle(Point2D(-2,-2),
			                                   2,scenario->getHeight()+2));
	}
	//Mettere qui codice per gestire i casi boundless e boundlessRefl
   }//Fine setBorder 
   
void PhysicEngine::animatePhysic()
  {
   //Ciclo che scorre i nodi per calcolare le collisioni
   Node *node;
   vector<Node*>::iterator i;
   for(i=nodes->begin();i!=nodes->end();i++)
    {
	 //Prelevo il prossimo nodo dalla lista dei nodi    
	 node=*i;
	 
	 ////Calcolo le collisioni/////
	 bool collision=collisionDetection(node);
	 ////////////
	 node->setCollision(collision);
	 
     //Controllo la collisione con il bordo 
	 if(scenario->getBorderType()==Scenario::BOUNDED)
	  borderCollision(node);
	}   
  }//Fine animatePhysic   
   
   
bool PhysicEngine::collisionDetection(Node *node)
  {
   //Creo le variabili per l'intersezione
   float t=FLT_MAX;
   //Testo gli oggetti fisici
   PhysicalObject *physicalObj;
   vector<PhysicalObject*>::iterator j;
   for(j=physicalObjs->begin();j!=physicalObjs->end();j++)
	{
	 //Prelevo il prossimo oggetti fisico
	 physicalObj=*j;
	 //Salta se stesso
	 if(node!=physicalObj)
	 {
	  //Calcolo l'intersezione
	  float pt=physicalObj->intersect(node);
	  //System.out.println("pt="+pt);
	  
	  //Se il punto d'intersezione attuale 
	  //è più vicino di quello globale faccio l'aggiornamento
	  if(((pt>=0)&&(pt<=1))&&(pt<t))
		    t=pt;
	 } 	
	}
	 
  //System.out.println("t="+t);
  
  //Controllo il valore di t per determinare se c'e' stata una collisione
  if((t>=0)&&(t<=1))
    {
	 //C'e' stata collisione
	 //Torno un poco indietro (Dell'unit� minima di spostamento)
	 float minVal=0.01f;
	 if(t>minVal)
	    t-=minVal; 
	  else
	    t=0;   
	    
	 //Calcolo il nuovo vettore velocita'  
	 Vector2D v=node->getVelocity();
	 //v.dotMul(t);
	 v*=t;
	 //imposto la nuova posizione del nodo
	 node->setPosition(v);
	 
	 //Ritorno la collisione
	 return true;
    }
  else//Non c'e' sata collisione
    { 
	 node->setPosition(node->getVelocity());//Non collide
	 return false;
    }	
 }//Fine collisionDetection   
   
void PhysicEngine::borderCollision(Node *node)
  {
   Point2D swapPos=node->getPosition();	 
   
   if((node->getPosition()).x<=node->getRadius())
    { swapPos.x=swapPos.x+0.001f; }  
	   
   if((node->getPosition()).y<=node->getRadius())
    { swapPos.y=swapPos.y+0.001f; }
	
   //Aggiorno la posizione del nodo
   node->setPosition(swapPos);
  }//Fine borderCollision   
 
