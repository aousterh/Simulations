#ifndef HOTSPOT_H_
#define HOTSPOT_H_

#include<iostream>
#include<vector>

#include"ScenarioElement.h"
#include"Point2D.h"

class HotSpot;

class Connection
{
 public:	
	 Connection(HotSpot *startSpot,HotSpot *endSpot);
	 Connection(HotSpot *startSpot,HotSpot *endSpot,float weight);
	 virtual ~Connection();
	 
	 void toXml(std::ostream &out);
	 
 private:	 
	 HotSpot *startSpot;
 	 HotSpot *endSpot;
 	 float weight;
 	
  public: 
 	 //Metodi inline
 	 inline void setStartSpot(HotSpot *startSpot)
      { this->startSpot=startSpot; }

     inline HotSpot* getStartSpot()
      { return startSpot; }
  
     inline void setEndSpot(HotSpot *endSpot)
      { this->endSpot=endSpot; }

     inline HotSpot* getEndSpot()
      { return endSpot; }
  
     inline void setWeight(int weight)
      { this->weight=weight; }

     inline float getWeight()
      { return weight; }
      
};





class HotSpot:public ScenarioElement
{
 public:
	HotSpot(const Point2D &pos,const float &radius,const int &ID);
	virtual ~HotSpot();
	
	void addConnections(vector<Connection*> *newConns);
       void toXml(std::ostream &out);
	
	
 private:
    float radius;
    int ID;
    
    //Vettore delle connessioni
    vector<Connection*> connections;
   
   //Metodi inline 
 public:
   inline Point2D getPosition()
    {return p;}
 
   inline float getRadius()
    {return radius;}
  
   inline void setID(const int &ID)
    { this->ID=ID; }
 
   inline int getID()
    { return ID; }   
   
   inline void createConnection(HotSpot *hotSpot,float weight)
   { connections.push_back(new Connection(this,hotSpot,weight)); }
  
  inline void createConnection(HotSpot *hotSpot)
   { connections.push_back(new Connection(this,hotSpot)); } 
   
  inline void addConnection(Connection *newConn)
   {connections.push_back(newConn);}
  
  inline vector<Connection*>* getConnections()
   {return &connections;} 
   
};

#endif /*HOTSPOT_H_*/
