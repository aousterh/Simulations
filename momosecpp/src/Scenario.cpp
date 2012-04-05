#include "Scenario.h"

#include<vector>

Scenario::Scenario(float width,float height,int borderType)
   {
	this->width=width;
	this->height=height;
	this->borderType=borderType;
   }//Fine costruttore

Scenario::~Scenario()
 {
  buildings.clear();
  hotSpots.clear();	
 }//Fine distruttore

//Overloading degli operatori
ostream& operator<<(ostream& output,const Scenario& scenario) 
 {
  output<<"width="<<scenario.width<<" height="<<scenario.height<<" borderType="<<scenario.borderType
        <<" #building="<<scenario.buildings.size()
        <<" #hotSpot="<<scenario.hotSpots.size();
  return output;
 }//Fine overloading I/O  


HotSpot* Scenario::getHotSpot(int id)
 {
  vector<HotSpot*>::iterator iSpot;
  for(iSpot=hotSpots.begin();iSpot!=hotSpots.end();iSpot++)
	{
	 HotSpot *nextSpot=*iSpot;
	 if(nextSpot->getID()==id)
	   return nextSpot;
	}
	return NULL;
   }//Fine getHotSpot
   
Building* Scenario::pointOverlap(const Point2D &p)
   {
    vector<Building*>::iterator i;
    for(i=buildings.begin();i!=buildings.end();i++)
     {
	  Building *building=*i;
	  if(building->pointOverlap(p)==true)
		 return building;
	 }  
    return NULL;	  
   }//Fine pointOverlap   
   
Building* Scenario::nodeOverlap(Node &node)
  {
   vector<Building*>::iterator i;
   for(i=buildings.begin();i!=buildings.end();i++)
    {
	 Building *building=*i;
	 if(building->nodeOverlap(node)==true)
		 return building;
	 }  
   return NULL;	  
  }//Fine nodeOverlap  
  

bool Scenario::calcCollision(Building *building,Ray2D &ray)
  {
   //Testo la collisione
   float intersect=building->rayIntersect(ray);
   if((0<=intersect)&&(intersect<=1))
	{return true;}
   else
    {return false;}   
  }//Fine calcCollision  
  
vector<Building*>* Scenario::getCollisions(Ray2D &ray)
  {
   vector<Building*>*buildColl=new vector<Building*>();	 
   vector<Building*>::iterator i;
   for(i=buildings.begin();i!=buildings.end();i++)
    {
     Building *nextBuilding=*i;
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {buildColl->push_back(nextBuilding);}	  
    }  
   return buildColl;  
  }//Fine getCollisions  
  

float Scenario::getAttenuation(Point2D &start,Point2D &end)
  {
   Ray2D ray(start,Vector2D(start,end));	
   float attenuation=0;
   
   vector<Building*>::iterator i;
   for(i=buildings.begin();i!=buildings.end();i++)
    {
	 Building *nextBuilding=*i;
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {attenuation+=nextBuilding->getAttenuation();}
	}    
   return attenuation<1?attenuation:1;	 
  }//Fine getAttenuation  
  
Building* Scenario::getFirstCollision(Ray2D &ray)
  {
   vector<Building*>::iterator i;
   float tMin=1;
   Building *first=NULL;
   
   for(i=buildings.begin();i!=buildings.end();i++)
	{
	 Building *nextBuilding=*i;
	 //Testo la collisione
	 float intersect=nextBuilding->rayIntersect(ray);
	 if((0<=intersect)&&(intersect<=1))
	  {
	   if(intersect<=tMin)
		{
		 first=nextBuilding;
		 tMin=intersect;
		} 
	  }
     }    
   return first;	 
  }//Fine getFirstCollision  
  
 
void Scenario::toXml(ostream &out)
 {
  //Salvo le info sullo scenario
  out<<"<Scenario width=\""<<width<<"\" height=\""<<height<<"\" border=\""
     <<borderType<<"\">\n";
   
  //Salvo i building
  buildingToXml(out);
  //salvo gli hotSPot
  hotSpotToXml(out);
   //salvo gli Texts
  textsToXml(out);	 
   
  //Chiudo la sezione dello scenario
  out<<"</Scenario>\n\n";
 }//Fine toXml 
  
  
void Scenario::buildingToXml(ostream &out)
  {
   Building *nextBuilding;	
   vector<Building*>::iterator i;
   for(i=buildings.begin();i!=buildings.end();i++)
    {
	 nextBuilding=*i;
	 //Salvo le info in xml
	 nextBuilding->toXml(out);
    }   
  }//Fine buildingToXml  
  
  
void Scenario::textsToXml(ostream &out)
  {
   ScenText *nextText;	
   vector<ScenText*>::iterator i;
   for(i=texts.begin();i!=texts.end();i++)
    {
	 nextText=*i;
	 //Salvo le info in xml
	 nextText->toXml(out);
    }   
  }//Fine buildingToXml    
  
void Scenario::hotSpotToXml(ostream &out)
 {
  vector<Connection*>* connections=new vector<Connection*>();
  HotSpot *nextSpot;
  //Salvo le info sugli hot Spot
  vector<HotSpot*>::iterator i;
  for(i=hotSpots.begin();i!=hotSpots.end();i++)
   {
	nextSpot=*i;
	//Salvo le info in xml
	nextSpot->toXml(out);
	//Prendo le connessioni dell'hotSpot
	addConnectionsXml(connections,nextSpot);
   }
  
  //Salvo le info sulle connessioni
  Connection *nextConn;
  vector<Connection*>::iterator iConn;
  for(iConn=connections->begin();iConn!=connections->end();iConn++)
   {
	nextConn=*iConn;
	//Salvo le info in xml
	nextConn->toXml(out);
   }
 }//Fine buildingToXml  
 
void Scenario::addConnectionsXml(vector<Connection*>* connections,HotSpot *hotSpot)
 {
  vector<Connection*>* connects=hotSpot->getConnections();
  vector<Connection*>::iterator iConn; 
  for(iConn=connects->begin();iConn!=connects->end();iConn++)
   {connections->push_back(*iConn);}
 }//Fine addConnections
  
   
