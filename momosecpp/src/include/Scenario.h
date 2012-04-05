#ifndef SCENARIO_H_
#define SCENARIO_H_

#include<iostream>
#include<vector>

#include"Building.h"
#include"HotSpot.h"
#include"ScenText.h"
#include"Point2D.h"
#include"Ray2D.h"
#include"Node.h"


class Scenario
{
 //overloading dell'I/O	
 friend ostream& operator<<(ostream& output,const Scenario& scenario); 		
	
 public:
    //Attributi statici
    static const int BOUNDED=0;  
    static const int BOUNDLESS=1;
    
    //Costruttori
	Scenario(float width,float height,int borderType);
	virtual ~Scenario();
	
	//Metodi
	HotSpot* getHotSpot(int id);
	Building* pointOverlap(const Point2D &p);
	Building* nodeOverlap(Node &node);
	vector<Building*>* getCollisions(Ray2D &ray);
	float getAttenuation(Point2D &start,Point2D &end);
	Building* getFirstCollision(Ray2D &ray);
	void toXml(ostream &out);
	
	
   private:	
	bool calcCollision(Building *building,Ray2D &ray);
	void buildingToXml(ostream &out);
	void hotSpotToXml(ostream &out);
        void textsToXml(ostream &out);
	void addConnectionsXml(vector<Connection*>* connections,HotSpot *hotSpot);
	
		
	//Variabili scenario	
    vector<Building*> buildings;
    vector<HotSpot*> hotSpots;
    vector<ScenText*> texts;
    float width;
    float height;
    int borderType;
	
   	
	//Metodi inline
 public:
    inline vector<Building*>* getBuildings()
     {return &buildings;}
  
    inline vector<HotSpot*>* getHotSpots()
     {return &hotSpots;}
     
     inline vector<ScenText*>* getTexts()
     {return &texts;}
  
    inline void setBorderType(int borderType)
     { this->borderType=borderType; }
     
    inline void addBuilding(Building *newBuilding)
     { buildings.push_back(newBuilding); }
  
    inline void addHotSpot(HotSpot *newSpot)
     { hotSpots.push_back(newSpot); }
     
     inline void addScenText(ScenText *newText)
     { texts.push_back(newText); }
     
    inline int getNumBuildings()
     {return buildings.size();} 
     
    inline int getNumHotSpots()
     {return hotSpots.size();}  
     
     inline int getNumTexts()
     {return texts.size();}  
  
    inline float getWidth()
     {return width;}
  
    inline float getHeight()
     {return height;}
  
    inline int getBorderType()
     { return borderType; } 	
};




#endif /*SCENARIO_H_*/
