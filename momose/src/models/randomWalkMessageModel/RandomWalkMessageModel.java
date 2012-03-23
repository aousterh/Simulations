/*
 * A RandomWalkMessageModel extends the message model, and uses
 * the random walk mobility model.
 */

package models.randomWalkMessageModel;

import math.Point2d;
import models.messageModel.MessageModel;
import models.messageModel.MessageNode;
import engine.Scenario;
import engine.SimTime;


public class RandomWalkMessageModel extends MessageModel
{
    protected float vMin;
    protected float vMax;
    protected float pauseTime;
    
    public RandomWalkMessageModel()
    {   
        super();
    }
    
    public void setModel(int numNodes, float nodeRadius, float antennaRadius,
                         float pauseTime, float vMin, float vMax,
                         boolean isPhysical)
    {
        this.numNodes = numNodes;
        this.nodeRadius = nodeRadius;
        this.antennaRadius = antennaRadius;
        this.pauseTime = pauseTime;
        this.vMax = vMax;
        this.vMin = vMin;
        
        isPhysical = true;  
        setThinkerProp(true);
    }   
    
    // create a node, as specified by this mobility model
    public MessageNode CreateMessageNode(Scenario scenario, SimTime time) {
        super.CreateMessageNode(scenario, time);
        
        float px = (float) (Math.random() * scenario.getWidth());
        float py = (float)(Math.random() * scenario.getHeight());
        
        Point2d initialPos = new Point2d(px, py);
        RandomWalkMessageNode messageNode = 
            new RandomWalkMessageNode(initialPos, nodeRadius, lastNodeId, time); 
        
        return messageNode;
    }
    
}// End class RandomWalkMessageModel