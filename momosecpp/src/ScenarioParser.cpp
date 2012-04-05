#include "ScenarioParser.h"

#include "Point2D.h"
#include "ScenRectangle.h"
#include "ScenCircle.h"
#include "HotSpot.h"

ScenarioParser::ScenarioParser()
 {
   scenario=NULL; 
   scenarioFile=false;	 
 }//Fine costruttore

ScenarioParser::~ScenarioParser(){}


void ScenarioParser::OnStartElement(const XML_Char* name,const XML_Char** attrs)
 {
     string str=xmlCharVectToString(name);	 
	 
    //E' un file di scenario??
    if(str=="ScenarioInfo") 
       scenarioFile=true;	
	  
    //Creo lo SCENARIO
	if(str=="Scenario")
	 { createScenario(attrs);  }  
    //Aggiungo il ScenRectangle allo scenario
       if(str=="Rect")
	 { createScenRectangle(attrs);  } 
	//Aggiungo il ScenCircle allo scenario
	if(str=="Circle")
	  { createScenCircle(attrs);  } 
	//Aggiungo il testo
	if(str=="Text")
	  { createScenText(attrs);  }   
	//Aggiungo l'hotSpot allo scenario
	 if(str=="HotSpot")
	  { createHotSpot(attrs);  } 
	//Aggiungo la connessione tra gli hotspot 
	 if(str=="Connection")
	  { createConnection(attrs);  } 
 }//Fine OnStartElement

 
void ScenarioParser::createScenario(const XML_Char** attrs) 
  {
    string widthStr=getAttrFromName(attrs,"width");	  
    float width=stringToFloat(widthStr);
    
    string heightStr=getAttrFromName(attrs,"height");	  	  
    float height=stringToFloat(heightStr);
	  
    string borderStr=getAttrFromName(attrs,"border");	  	  
    int borderType=stringToInt(borderStr);
   
    //Creo lo scneario
   scenario=new Scenario(width,height,borderType); 
  }//Fine createScenario 
 
 
 void ScenarioParser::createScenText(const XML_Char** attrs) 
  {
    //Estraggo gli elementi necessari
    string xStr=getAttrFromName(attrs,"x");	  
    float x=stringToFloat(xStr);	  
   
    string yStr=getAttrFromName(attrs,"y");	  
    float y=stringToFloat(yStr);	  	
	  
   string str=getAttrFromName(attrs,"str");	  	  
   
   
   //Creo il testo
   ScenText *newText=new ScenText(Point2D(x,y),str);
   
	  
   //Colore del punto
   Color pointColor=getColor(getAttrFromName(attrs,"pointColor"),Color(-1,-1,-1));
   newText->setPointColor(pointColor);  
   
   //Colore del testo
   Color strColor=getColor(getAttrFromName(attrs,"strColor"),Color(0,0,0));
   newText->setStrColor(strColor);
   
   //Angolo di rotazione
   string strRot=getAttrFromName(attrs,"rotation");	  
   if(strRot!="")
   {	   
     float rot=stringToFloat(strRot);	  
     if(rot!=0)
       newText->setRotation(rot);	     
   }
   
   
   //Scale del testo
   string strScale=getAttrFromName(attrs,"scale");	  
   if(strScale!="")
    {	   
      float scale=stringToFloat(strScale);	  
      if(scale>0)
        newText->setScale(scale);	     
    }
   
   //Aggiungo il ScenText allo scenario
   if(scenario!=NULL)
    { scenario->addScenText(newText); }
  }//Fine createScenText 
  
  
void ScenarioParser::createScenCircle(const XML_Char** attrs) 
  {
     //Estraggo gli elementi necessari
    string xStr=getAttrFromName(attrs,"x");	  
    float x=stringToFloat(xStr);	  
   
    string yStr=getAttrFromName(attrs,"y");	  
    float y=stringToFloat(yStr);	  	
	  
   string radiusStr=getAttrFromName(attrs,"radius");	  	  
   float radius=stringToFloat(radiusStr);	
   
   
   //Creo l'oggetto ScenRectangle
   ScenCircle *newCircle=new ScenCircle(Point2D(x,y),radius);
   
   //Estraggo e setto i dati opzionali
   //Nome del building
    string name=getAttrFromName(attrs,"name");
	  
  if(name!="")
    newCircle->setName(name);   
   //Valore di attenuazione
   string attStr=getAttrFromName(attrs,"attenuation");	
  
   //Colore di riempimento
   Color fill=getColor(getAttrFromName(attrs,"fill"),Color(128,128,128));
   newCircle->setFillColor(fill);  
   
   //Colore del bordo
   Color border=getColor(getAttrFromName(attrs,"border"),Color(255,0,0));
   newCircle->setBorderColor(border);
  
   //Attenuazione
   float attenuation=stringToFloat(attStr);	  	
   newCircle->setAttenuation(attenuation);
   
   //Aggiungo il ScenRectangle allo scenario
   if(scenario!=NULL)
    { scenario->addBuilding(newCircle); }	 
 }//Fine createScenCircle
  
 
 
 void ScenarioParser::createScenRectangle(const XML_Char** attrs) 
  {
    //Estraggo gli elementi necessari
    string xStr=getAttrFromName(attrs,"x");	  
    float x=stringToFloat(xStr);	  
   
    string yStr=getAttrFromName(attrs,"y");	  
    float y=stringToFloat(yStr);	  	  
   
   string widthStr=getAttrFromName(attrs,"width");	  
   float width=stringToFloat(widthStr);	  	  	  
   
   string heightStr=getAttrFromName(attrs,"height");	  
   float height=stringToFloat(heightStr);	  	  	  	  
      
   //Creo l'oggetto ScenRectangle
   ScenRectangle *newRectangle=new ScenRectangle(Point2D(x,y),width,height);
   
   //Valore di attenuazione
   string attStr=getAttrFromName(attrs,"attenuation");	
   float attenuation=stringToFloat(attStr);	  	  	  	  
   newRectangle->setAttenuation(attenuation);	  
	  
   //Estraggo i dati opzionali
   //Nome del building
   string name=getAttrFromName(attrs,"name");
   if(name!="")
    newRectangle->setName(name);   
   
   
   //Angolo di rotazione
   string rotStr=getAttrFromName(attrs,"rotation");	  
   if(rotStr!="")
   {
     float rotAngle=stringToFloat(rotStr);	  	 
     newRectangle->setRotAngle(rotAngle);   	   
   }	   
	   
   //Colore di riempimento
   Color fill=getColor(getAttrFromName(attrs,"fill"),Color(128,128,128));
   newRectangle->setFillColor(fill);  
   
   //Colore del bordo
   Color border=getColor(getAttrFromName(attrs,"border"),Color(255,0,0));
   newRectangle->setBorderColor(border);
   
   
   //Aggiungo il ScenRectangle allo scenario
   if(scenario!=NULL)
    { scenario->addBuilding(newRectangle); }
  }//Fine createScenRectange
 
  
  
  
 void ScenarioParser::createHotSpot(const XML_Char** attrs) 
 {
   //Estraggo gli elementi necessari	 
   string xStr=getAttrFromName(attrs,"x");	  
    float x=stringToFloat(xStr);	  
   
    string yStr=getAttrFromName(attrs,"y");	  
    float y=stringToFloat(yStr);	  	
	  
   string radiusStr=getAttrFromName(attrs,"radius");	  	  
   float radius=stringToFloat(radiusStr);	
	  
   string idStr=getAttrFromName(attrs,"ID");	 
   int ID=stringToInt(idStr);	

   //Creo l'oggetto HotSpot
   HotSpot *newSpot=new HotSpot(Point2D(x,y),radius,ID);    

   //Aggiungo il ScenRectangle allo scenario
   if(scenario!=NULL)
    { scenario->addHotSpot(newSpot); }	
 }//Fine create HotSPot 
 
 
 void ScenarioParser::createConnection(const XML_Char** attrs) 
  {
    //Estraggo gli attributi	
    string startStr=getAttrFromName(attrs,"startSpot");	 
    int startId=stringToInt(startStr);
	  
    string endStr=getAttrFromName(attrs,"endSpot");	 
    int endId=stringToInt(endStr);	  
	  
   string weightStr=getAttrFromName(attrs,"weight");	
   float weight=stringToFloat(weightStr);
  
   if(scenario!=NULL)
    {
         //Estraggo nodo iniziale
	 HotSpot *startSpot=scenario->getHotSpot(startId);
	 HotSpot *endSpot=scenario->getHotSpot(endId);
	 
	 //Cerco Aggiungo la connessione
	 if((startSpot!=NULL)&&(endSpot!=NULL))
	  {
	   Connection *newConn=new Connection(startSpot,endSpot,weight);
	   //Aggiungo la connessione al nodo di partenza
	   startSpot->addConnection(newConn);
	  } 	 
	}   
 }//Fine createConnection
 
 
 
 Color ScenarioParser::getColor(string str,Color basicColor)
  {
     if(str=="")  
	  return basicColor; 
	else
	 {
	   string strValue=extractValue(str,0);
	   int total=0;
	    
	   int red=stringToInt(strValue);
	   total+=strValue.length()+1;  
	    
	  
	    strValue=extractValue(str,total);
	    int green=stringToInt(strValue);
	    total+=strValue.length()+1;  
	  
	  
	    strValue=extractValue(str,total);
	    int blue=stringToInt(strValue);
	  
	    return Color(red,blue,green);
	 }	
  }//Fine getColor
 
 string ScenarioParser::extractValue(string str,int startIndex)
  {
    string strValue;	  
   bool found=false;
   char c;
   int count=0;
   
   while((!found)&&((startIndex+count)<str.length()))
    {
	 c=str[startIndex+count];
	 if(c==',')
	  found=true;	 
	 else
	  strValue+=c; 
	 
	 count++;
	}	 
   
   return strValue;
  }//Fine extraceValue
 
 
 
void ScenarioParser::OnCharacterData(const XML_Char* data, int len){}
void ScenarioParser::OnEndElement(const XML_Char* name){}
 

 
  
 
 
 
