package models.messageModel;

import models.Model;
import engine.SimTime;

public abstract class MessageModel extends Model
{
    protected int numNodes;
    protected float nodeRadius;
    protected float antennaRadius;
    protected boolean isPhysical;
    
    public MessageModel()
    {
        setModel(10, 0.5f, false, true);
    }//End constructor
    
    public void setModel(int numNodes, float nodeRadius) {
        this.numNodes = numNodes;
        this.nodeRadius = nodeRadius;
        this.antennaRadius = nodeRadius*10;  
        
        isPhysical = false;  
        setThinkerProp(true);
    }
    
    public void setup(Scenario scenario, SimTime time) 
    {
        float px;
        float py;
        
        for(int i=0; i < numNodes; i++)
        {
            // generate random starting coordinates for each node
            px = (float)(Math.random()*scenario.getWidth());
            py = (float)(Math.random()*scenario.getHeight());  
            
            Node newNode=new MessageNode(new Point2d(px,py), nodeRadius, i, time);
            newNode.setAntennaRadius(antennaRadius);
            nodes.add(newNode);
        } 
    }
    
    public void think(SimTime time)
    {
        // see if any nodes can exchange messages
        for (int i = 0; i < numNodes - 1; i++)
        {
            MessageNode node1 = nodes.elementAt(i);
            for (int j = i + 1; j < numNodes; j++) 
            {
                MessageNode node2 = nodes.elementAt(j);
                // TODO COMPARE DISTANCE
                // TODO EXCHANGE MESSAGES IF CLOSE ENOUGH
            }
        }
    }
    
}//End class MessageModel