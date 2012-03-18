package engine;

import java.util.Iterator;
import java.util.Vector;

import math.Point2d;
import math.Ray2d;
import math.Vector2d;
import models.Node;


public class Scenario 
 {
  //Attributi statici
  public static int BOUNDED=0;  
  public static int BOUNDLESS=1;
  public static int BOUNDLESSREFL=2; 
	
  //Variabili scenario	
  private Vector buildings;
  private Vector hotSpots;
  private Vector texts;
  private float width;
  private float height;
  private int borderType;
  	
  public Scenario(float width,float height,int borderType)
   {
	buildings=new Vector(); 
	hotSpots=new Vector(); 
	texts=new Vector(); 
	this.width=width;
	this.height=height;
	this.borderType=borderType;
   }//Fine costruttore
  
  public Vector getBuildings()
  {return buildings;}
  
  public Vector getHotSpots()
   {return hotSpots;}
  
  public Vector getTexts()
   {return texts;}
  
  public void setBorderType(int borderType)
   { this.borderType=borderType; }
  
  public void addBuilding(Building newBuilding)
   { buildings.add(newBuilding); }
  
  public void addHotSpot(HotSpot newSpot)
   { hotSpots.add(newSpot); }
  
  public void addText(ScenText newText)
   { texts.add(newText); }
  
  public float getWidth()
   {return width;}
  
  public float getHeight()
  {return height;}
  
  public int getBorderType()
  { return borderType; }
  
  public HotSpot getHotSpot(int id)
   {
	Iterator i=hotSpots.iterator();
	while(i.hasNext())
	 {
	  HotSpot nextSpot=(HotSpot)i.next();
	  if(nextSpot.getID()==id)
	    return nextSpot;
	 }
	return null;
   }//Fine getHotSpot
  
  public Building pointOverlap(Point2d p)
   {
    Iterator i=buildings.iterator();
    while(i.hasNext())
     {
	  Building building=(Building)i.next();
	  if(building.pointOverlap(p)==true)
		 return building;
	 }  
    return null;	  
   }//Fine pointOverlap
  
 public Building nodeOverlap(Node node)
  {
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	  Building building=(Building)i.next();
	  if(building.nodeOverlap(node)==true)
		 return building;
	 }  
   return null;	  
  }//Fine pointOverlap
 
 
 private boolean calcCollision(Building building,Ray2d ray)
  {
   //Testo la collisione
   float intersect=building.rayIntersect(ray);
   if((0<=intersect)&&(intersect<=1))
	{return true;}
   else
    {return false;}   
  }//Fine calcCollision
 
 public boolean isCollided(Ray2d ray)
  {
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	 Building nextBuilding=(Building)i.next();
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {return true;}	  
    }  
  return false;
}//Fine getCollision
 
  
 public boolean collision(Ray2d ray)
  {
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	 Building nextBuilding=(Building)i.next();
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {return true;}	  
    }  
   return false;
  }//Fine collision
 
 public Vector getCollisions(Ray2d ray)
  {
   Vector buildColl=new Vector();	 
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	 Building nextBuilding=(Building)i.next();
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {buildColl.add(nextBuilding);}	  
    }  
  return buildColl;
 }//Fine getCollision
 
 public float getAttenuation(Point2d start,Point2d end)
  {
   Ray2d ray=new Ray2d(start,new Vector2d(start,end));	
   float attenuation=0;
   
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	 Building nextBuilding=(Building)i.next();
	 //Testo la collisione
	 if(calcCollision(nextBuilding,ray)==true)
	  {attenuation+=nextBuilding.getAttenuation();}
	}    
   return attenuation<1?attenuation:1;	 
  }//Fine getAttenuation
 
 public Building getFirstCollision(Ray2d ray)
  {
   Iterator i=buildings.iterator();
   float tMin=1;
   Building first=null;
   
   while(i.hasNext())
	{
	 Building nextBuilding=(Building)i.next();
	 //Testo la collisione
	 float intersect=nextBuilding.rayIntersect(ray);
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
 
 
 public String toString()
  {return new String("width="+width+" height="+height+" borderType="+borderType);}
 
  public String toXml()
   {
   String scenarioXml=new String();
   //Salvo le info sullo scenario
   scenarioXml="<Scenario width=\""+width+"\" height=\""+height+"\" border=\""+borderType+"\">\n";
   
   //Salvo i building
   scenarioXml+=buildingToXml();
   //salvo gli hotSPot
   scenarioXml+=hotSpotToXml();
   //salvo le stringhe
   scenarioXml+=textsToXml();
   
   
   //Chiudo la sezione dello scenario
   scenarioXml+="</Scenario>\n";
   return scenarioXml;	  
  }//Fine toXml
  
 public String buildingToXml()
  {
   String buildingXml=new String();	 
   Iterator i=buildings.iterator();
   while(i.hasNext())
    {
	 Building nextBuilding=(Building)i.next();
	 //Salvo le info in xml
	 buildingXml+=nextBuilding.toXml();
    }   
   return buildingXml;	 
  }//Fine buildingToXml
 
 public String hotSpotToXml()
 {
  Vector connections=new Vector();
  
  //Salvo le info sugli hot Spot
  String hotSpotXml=new String();	 
  Iterator i=hotSpots.iterator();
  while(i.hasNext())
   {
	HotSpot nextSpot=(HotSpot)i.next();
	//Salvo le info in xml
	hotSpotXml+=nextSpot.toXml();
	//Prendo le connessioni dell'hotSpot
	connections.addAll(nextSpot.getConnections());
   }
  
  //Salvo le info sulle connessioni
  i=connections.iterator();
  while(i.hasNext())
   {
	Connection nextConn=(Connection)i.next();
	//Salvo le info in xml
	hotSpotXml+=nextConn.toXml();
   }
  
  return hotSpotXml;	 
 }//Fine buildingToXml
 
 public String textsToXml()
 {
  String textsXml=new String();	 
  Iterator i=texts.iterator();
  while(i.hasNext())
   {
	ScenText nextText=(ScenText)i.next();
	//Salvo le info in xml
	textsXml+=nextText.toXml();
   }   
  return textsXml;	 
 }//Fine buildingToXml

}//Fine classe Scenario
