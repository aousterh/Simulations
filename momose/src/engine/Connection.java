package engine;



public class Connection 
 {
  HotSpot startSpot;	
  HotSpot endSpot;
  float weight;
  
  public Connection(HotSpot startSpot,HotSpot endSpot)
   { 
	this.startSpot=startSpot;  
	this.endSpot=endSpot;
	weight=0;
   }//Fine costruttore
  
  public Connection(HotSpot startSpot,HotSpot endSpot,float weight)
  { 
   this(startSpot,endSpot);
   this.weight=weight;
  }//Fine costruttore
  
  public void setStartSpot(HotSpot startSpot)
  { this.startSpot=startSpot; }

 public HotSpot getStartSpot()
  { return startSpot; }
  
  public void setEndSpot(HotSpot endSpot)
   { this.endSpot=endSpot; }

  public HotSpot getEndSpot()
   { return endSpot; }
  
  public void setWeight(int weight)
   { this.weight=weight; }

  public float getWeight()
   { return weight; }
  
  public String toXml()
   {
	return new String(" <Connection startSpot=\""+startSpot.getID()+"\" endSpot=\""+endSpot.getID()+"\" weight=\""+weight+"\"/>\n");  
   }//Fine toXml
 }//Fine classe connection
