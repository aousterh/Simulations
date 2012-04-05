#include "RandomWalkNode.h"

#include <stdlib.h>
#include <time.h>

RandomWalkNode::RandomWalkNode(Point2D pos,const float &radius): SimpleNode(pos,radius) 
   {
	localTime=0;
	//Imposto il seme per generare i numeri casuali
	srand((unsigned)time(NULL));
	//Velocità iniziale
	//setVelocity(Vector2D(1.0f,0.0f));
   }//Fine costruttore

RandomWalkNode::~RandomWalkNode(){}

void RandomWalkNode::think(SimTime *simTime) 
   {
    //time->dynamicTimetoXml(cout);	
	//cout<<"Sto pensando..."<<endl;  
	//Movimento di prova
	//testMove(simTime);
    //Movimento random
	randomMove(simTime);	
  }//Fine think
   
void RandomWalkNode::testMove(SimTime *simTime)
   {
	setVelocity(Vector2D(1.0f,0.0f));  
	//cout<<"Node="<<*this<<endl;
	collided=true;
	if(collided==true)
	{
	 Vector2D tv=getVelocity();
	 setVelocity(Vector2D((-tv.x),-(tv.y)));
	} 
   }//Fine testMove  
   
void RandomWalkNode::randomMove(SimTime *simTime)
   {
   	if((localTime>=pauseTime)||(collided==true)) 
	 {	
	  //Calcolo il nuovo vettore velocità	
      v.x=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
	  v.y=(float)((((float)rand()/(float)(RAND_MAX))*(vMax-vMin))+vMin)
   	                *(rand()%2<1?(-1):(1));//Segno
      //cout<<"v="<<v<<endl;
      
	  //Rapporto la velocità all'intervallo di tempo 
	  //v.x=v.x*simTime->getDT();
	  //v.y=v.y*simTime->getDT();
	  v*=simTime->getDT();
	  
	  //Azzero il tempo locale
	  localTime=0;  
	 }	
  
  //Incremento il tempo locale  
  localTime+=simTime->getDT();    
 }//Fine randomMove   
