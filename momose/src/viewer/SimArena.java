package viewer;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JPanel;
import viewer.GraphNode;
import engine.Scenario;
import engine.ScenarioElement;

public abstract class SimArena extends JPanel
 {
  //Coordinate di simulazione
  protected float sWidth;
  protected float sHeight;	
	
  //Scala dell'arena
  protected float scale;
  
  //elementi da visualizzare
  protected Scenario scenario;
  protected ScenarioElement[] scenElemArray;
  protected Vector nodes;
	    
  //Variabili dell'arena
  protected boolean drawAntennaRadius;
  protected boolean drawGraph;
  protected boolean drawHotSpot;
  protected boolean drawNodeId;
  
  
  public SimArena(float scale)
   {
    this.scale=scale;

    this.sWidth=100;
    this.sHeight=100;
    
    //Impostazioni vettori di visualizzazione
	scenario=null;
	scenElemArray=new ScenarioElement[0];
	nodes=new Vector();
	
	//Variabili dell'arena
	drawAntennaRadius=false;
	drawGraph=false;
	drawHotSpot=false;
	drawNodeId=false;
   }//Fine costruttore
  
  public void setScenario(Scenario scenario)
   {
	this.scenario=scenario;
	setSimDimension(scenario.getWidth(),scenario.getHeight());
	setScenElementArray(scenario);
	
	//Setto la scala iniziale
	//setInitialScale();
     	
    //Applico i cambiamenti
	applyChange();
   }//Fine setScenario
  
  private void setScenElementArray(Scenario scenario) 
   {
	//Conto il numero di elementi totali  
	int countElem=scenario.getBuildings().size();
	countElem+=scenario.getHotSpots().size();
	countElem+=scenario.getTexts().size();
	
	//Creo l'array degli elelemnti dello scenario
	scenElemArray=new ScenarioElement[countElem];
	
	int count=0;
	//Riempo l'array con i building
	Iterator i=scenario.getBuildings().iterator();
	count=addArrayElem(i,count);
	
    //Riempo l'array con gli hotSpot
	i=scenario.getHotSpots().iterator();
	count=addArrayElem(i,count);
	
    //Riempo l'array con i testi
	i=scenario.getTexts().iterator();
	count=addArrayElem(i,count);	
  }//Fine SetScenElementArray
 
private int addArrayElem(Iterator i,int count)
 {
  while(i.hasNext())
  {
	ScenarioElement nextElem=(ScenarioElement)i.next();
	scenElemArray[count]=nextElem;
	count++;
  }		
 return count;	
}//Fine addArrayElem
  
  public void setNodes(Vector nodes)
   { 
	this.nodes=nodes;
	
	//Assegno gli Id ai nodi
	int count=1;
	Iterator i=nodes.iterator();
	while(i.hasNext())
	 {
	  GraphNode node=(GraphNode)i.next();
	  //Disegno il nodo
	  node.setId(count);
	  count++;
	 }		 
  }//Fine setNodes
 
  public void setSimDimension(float width,float height)
   { this.sWidth=width; this.sHeight=height; }
 
  public void setDrawAntenna(boolean flag)
   {drawAntennaRadius=flag;}
 
  public void setDrawGraph(boolean flag)
   {drawGraph=flag;}
 
  public void setDrawHotSpot(boolean flag)
   {drawHotSpot=flag;}
  
  public boolean getDrawHotSpot()
  {return drawHotSpot;}
  
  public void setDrawNodeId(boolean flag)
   {drawNodeId=flag;}
 
  public boolean getDrawNodeId()
  {return drawNodeId;}
  
  
  
  
  protected Color calcAttenuation(GraphNode node, GraphNode target) 
   {
    float alpha=scenario.getAttenuation(node.getPosition(), target.getPosition());
    return new Color(alpha,(1-alpha)*0.7f,0);
   }//Fine calcAttenuation
  
  public abstract void updateNodes(Vector nodes);
   
  
  public void changeScale(float scale) 
   {
    this.scale=scale;
    applyChange();
   }//Fine changeScale
  
  //Metodi astratti 
  public abstract void reDraw();
  protected abstract void applyChange();
 
}//Fine classe SimArena