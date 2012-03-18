package models;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ModelParser extends DefaultHandler
 {
  protected static int NUM_NODES=0;	
  protected static int NODE_RADIUS=1;
  protected static int ANTENNA_RADIUS=2;
  protected static int IS_PHYSICAL=3;
  protected static int IS_THINKER=4;
  
  protected int numNodes;
  protected float nodeRadius;
  protected float antennaRadius;
  protected boolean isPhysical;
  protected boolean isThinker;
  
  protected int actTag; 
  
  public ModelParser()
   {
	numNodes=0;
	nodeRadius=1;
	antennaRadius=10;
	isPhysical=false;
	isThinker=false;
	
    actTag=-1;
   }//Fine costruttore
	
  public int getNumNodes()
   { return numNodes; } 
  
  public float getNodeRadius()
   { return nodeRadius; }		  
  
  public float getAntennaRadius()
   { return antennaRadius; } 
  
  public boolean getThinkerProp()
   { return isThinker; }
  
  public boolean getPhysicalProp()
   { return isPhysical; } 
	
  public void startElement(String uri, String name,String qName, Attributes atts)
   {
    //Numero nodi //<nn>value</nn>
	if(qName.equals("nn")) //<nn>value</nn>
	 { actTag=NUM_NODES;  }  
		
	//Raggio dei nodi //<nr>value</nr>
	if(qName.equals("nr"))
	 { actTag=NODE_RADIUS; }
		
	//Raggio antenna
	if(qName.equals("ar")) //<ar>value</ar>
	 { actTag=ANTENNA_RADIUS; }
		
	//Nodi fisici
	if(qName.equals("isPhy")) //<isPhy>true|false</isPhy>
	 { actTag=IS_PHYSICAL; }
		
	//Modello "pensante"
	 if(qName.equals("isThink")) //<isThink>true|false</isThink>
	  { actTag=IS_THINKER; }
  }//Fine startElement
  
 public void characters(char[] ch,int start,int length)
  {
   String str=new String(ch,start,length); 
   
   //Numero nodi //<nn>value</nn>
   if(actTag==NUM_NODES) //<nn>value</nn>
	 {
      numNodes=Integer.valueOf(str);
      actTag=-1;
     }  
		
	//Raggio dei nodi //<nr>value</nr>
	if(actTag==NODE_RADIUS)
	 { 
	  nodeRadius=Float.valueOf(str);
	  actTag=-1;
	 }
		
	//Raggio antenna
	if(actTag==ANTENNA_RADIUS) //<ar>value</ar>
	 { 
	  antennaRadius=Float.valueOf(str);
	  actTag=-1;
	 }
		
	//Nodi fisici
	if(actTag==IS_PHYSICAL) //<isPhy>true|false</isPhy>
	 { 
	  if(str.equals("true"))
	   isPhysical=true;
	  else
	   isPhysical=false;	  
	  actTag=-1;
	 }
		
	//Modello "pensante"
	if(actTag==IS_THINKER) //<isThink>true|false</isThink>
	 { 
	  if(str.equals("true"))
	   isThinker=true;
	  else
	   isThinker=false;	  
	  actTag=-1;
	 } 
  }//Fine characters
}//Fine ModelParser
