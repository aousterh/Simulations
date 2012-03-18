package models.ecraModel;

import org.xml.sax.Attributes;

import models.ModelParser;

public class EcraModelParser extends ModelParser
{
  protected static int COL_INTERVAL=5;
  protected static int VMIN=6;
  protected static int VMAX=7;
  protected static int TARGET_ATTR=8;
  protected static int ROLES=9;
  protected static int TARGET_AREAS=10;
  protected static int MOBILE_REF=11;
	
  private float colInterval;
  private float targetAttr;	
  private float vMin;
  private float vMax;
  private int roles;
  private int targetAreas;
  private boolean mobileRefPoint;
      	
  public EcraModelParser() 
   {
    super();
    colInterval=0;
    targetAttr=0;
    vMin=0;
    vMax=5;
    roles=2;
    targetAreas=4;
    mobileRefPoint=true;
    }//Fine costruttore
  
  public float getVMax()
   {
	if(vMax<0)  
	 return 0;
	else
     return vMax;		
   }//Fine getVMax
  
  public float getVMin()
   {
	if(vMin<0)  
	 return 0;
	else
     return vMin;		
   }//Fine getVMax
  
  public float getColInterval()
   {
	if(colInterval<0)  
	 return 0;
	else
     return colInterval;		
   }//Fine getInterval  
  
  public float getTargetAttr()
   {
	if(targetAttr<0)  
	 return 0;
	if(targetAttr>1)  
	 return 1;
    return targetAttr;		
   }//Fine getTargetAttr
  
  public int getTargetAreas()
   {
	if(targetAreas<4)  
	 return 4;
    return targetAreas;		
   }//Fine getTargetAttr
  
  public int getRoles()
  {
	if(roles<2)  
	 return 2;
	if(roles>3)  
	 return 3;
   return roles;		
  }//Fine getTargetAttr
  
  public boolean getMobileRefPoint()
   {return mobileRefPoint;}
  
  
  public void startElement (String uri, String name,String qName, Attributes atts)
   {
    //Richiamo il metodo del padre (controlla numero nodi,raggio nodi e raggio antenna)	  
    super.startElement(uri,name,qName,atts);	  
	
    //Intervallo di tempo <ColInterval>value<ColInterval>
    if(qName.equals("colInterval")) 
	 { actTag=COL_INTERVAL; }
    
    //Velocita' minima <vMin>value</vMin>
    if(qName.equals("vMin")) 
	 { actTag=VMIN; }
    //Velocita' massima <vMax>value</vMax>
    if(qName.equals("vMax")) 
	 { actTag=VMAX; }
   ///attrazione al target
    if(qName.equals("targetAttr")) 
	 { actTag=TARGET_ATTR; }
   //Zone di attrazione
    if(qName.equals("targetAreas")) 
	 { actTag=TARGET_AREAS; } 
    //Ruoli
    if(qName.equals("roles")) 
	 { actTag=ROLES; } 
    
    //Mobile reference point
    if(qName.equals("mobRefPoint")) 
	 { actTag=MOBILE_REF; } 
    
  }//Fine startElement
 
  public void characters(char[] ch,int start,int length)
   {
	//Richiamo il metodo del padre  
	super.characters(ch,start,length);  
	  
    String str=new String(ch,start,length);
    //Intervallo
    if(actTag==COL_INTERVAL) 
	 { 
      colInterval=Float.valueOf(str);
      actTag=-1;
     }
    //Velocita' minima
    if(actTag==VMIN) 
	 {
      vMin=Float.valueOf(str);
      actTag=-1;
     }
	   
    //Velocità massima
    if(actTag==VMAX) 
	 { 
      vMax=Float.valueOf(str);
      actTag=-1;
     }
     
    //attrazione
    if(actTag==TARGET_ATTR) 
	 { 
      targetAttr=Float.valueOf(str);
      actTag=-1;
     }
    
    //zone d'attrazione
    if(actTag==TARGET_AREAS) 
	 { 
      targetAreas=Integer.valueOf(str);
      actTag=-1;
     }
    
    //numero ruoli
    if(actTag==ROLES) 
	 { 
      roles=Integer.valueOf(str);
      actTag=-1;
     }
      
    //mobile ref point
	if(actTag==MOBILE_REF)
	 { 
	  if(str.equals("true"))
	   mobileRefPoint=true;
	  else
	   mobileRefPoint=false;	  
	  actTag=-1;
	 }
    
   }//Fine characters
 }//Fine classe EraModelConfigParser
