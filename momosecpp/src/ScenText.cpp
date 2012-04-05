#include"ScenText.h"

ScenText::ScenText(const Point2D &pos,string str):ScenarioElement(pos),strColor(0,0,0),pointColor(-1,-1,-1)
 { 
   this->str=str;
   rot=0;
   scale=1;	 
  }//Fine costruttore

ScenText::~ScenText(){}

	
void ScenText::toXml(ostream &out)
 {
   out<<" <Text x=\""<<p.x<<"\" y=\""<<p.y<<"\" str=\""<<str<<"\"";
    
  //Colore del punto (Dato opzionale)
  if((pointColor.getRi()!=-1)||
     (pointColor.getGi()!=-1)||
     (pointColor.getBi()!=-1))
    { 
      out<<" pointColor=\""<<pointColor.getRi()<<","
	                                <<pointColor.getGi()<<","
	                               <<pointColor.getBi()<<"\""; 
    }
	   
   //Colore del testo (Dato opzionale)
   if((strColor.getRi()!=0)||
      (strColor.getGi()!=0)||
      (strColor.getBi()!=0))
      { 
	 out<<" strColor=\""<<strColor.getRi()<<","
	                                <<strColor.getGi()<<","
                                        <<strColor.getBi()<<"\""; 
       }  
   
   //Angolo di rotazione (Dato opzionale)
   if(rot!=0)
     { out<<" rotation=\""<<rot<<"\""; }  
     
    
  //Scala del testo (Dato opzionale)
   if(scale!=1)
     { out<<" scale=\""<<scale<<"\""; }       
   
   //Stringa di chiusura tag
   out<<" />\n";	
}//Fine toXml	