/*
 * A MessageModel provides the basic functions that allow nodes to
 * exchange messages. This class should be extended with different
 * mobility models.
 */

package models.messageModel;

import models.Model;
import engine.Scenario;
import engine.SimTime;

public abstract class MessageModel extends Model
{
    protected int numNodes;
    protected float nodeRadius;
    protected float antennaRadius;
    protected boolean isPhysical;
    protected int lastNodeId = -1;  // last used node id
    protected int EXCHANGE_DISTANCE = 5; // should read this in from parser??
    
    // extensions should create whatever contructor and setModel they need
    
    public void setup(Scenario scenario, SimTime time) 
    { 
        for(int i=0; i < numNodes; i++)
         {  
            MessageNode newNode = CreateMessageNode(scenario, time);
            newNode.setAntennaRadius(antennaRadius);
            nodes.add(newNode);
         }
    }

    // create a node, as specified by this mobility model
    // extensions of this MUST call super after they have finished their
    // work
    public MessageNode CreateMessageNode(Scenario scenario, SimTime time) {
        lastNodeId++;
        return null;
    }
    
    public void think(SimTime time)
    {
        // see if any nodes can exchange messages
        for (int i = 0; i < numNodes - 1; i++)
        {
            MessageNode node1 = (MessageNode) nodes.elementAt(i);
            for (int j = i + 1; j < numNodes; j++) 
            {
                MessageNode node2 = (MessageNode) nodes.elementAt(j);
                if (node1.distanceTo(node2) >= EXCHANGE_DISTANCE) {
                    node1.exchangeWith(node2);
                    node2.exchangeWith(node1);
                }
            }
        }
    }
    
}// End class MessageModel