package engine;

import java.util.Iterator;
import java.util.Vector;

import models.Model;

public class AiManager 
 {
  private Vector thinkers; 	
  private Vector thinkersModels; 
	
  public AiManager()
   {
	thinkers=new Vector();
	thinkersModels=new Vector();  
   }//Fine costruttore
  
  public void addThinkers(Vector newThinkers)
   {thinkers.addAll(newThinkers); }
  
  public void addThinkerModel(Model newThinkerModel)
   {thinkersModels.add(newThinkerModel); }
  
  public int getNumThinkers()
   {return thinkers.size(); }
 
  public int getNumModThinkers()
   {return thinkersModels.size(); }
  
  
  public void animateAI(SimTime time)
   {
	//Faccio pensare i modelli registrati
	Iterator j=thinkersModels.iterator();
	while(j.hasNext())
	 {
	  ThinkerObject thinker=(ThinkerObject)j.next();
	  //Faccio pensare il thinker
	  thinker.think(time);
	 } 	  
	  
	//Faccio pensare i nodi registrati  
	Iterator i=thinkers.iterator();
	while(i.hasNext())
	 {
	  ThinkerObject thinker=(ThinkerObject)i.next();
	  //Faccio pensare il thinker
	  thinker.think(time);
	 } 	
   }//Fine animateAI
  
 }//Fine AIMAnager
