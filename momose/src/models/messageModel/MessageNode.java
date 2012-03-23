package models.messageModel;

import java.util.ArrayList;

import engine.SimTime;
import math.Point2d;
import models.Node;


public abstract class MessageNode extends Node
{
    protected int nodeId;
    protected int nextMessageId = 0;
    protected ArrayList<MessageData> messages = new ArrayList<MessageData>();
    SimTime time;
    
    private class MessageData {
        long uuid;
        float latency; // time it took for message to arrive here
                       // (0 for outgoing messages)
        float creationTime;
        
        private MessageData(long uuid, float latency, float createTime) {
            this.uuid = uuid;  // uniquely identifies message and sender
            this.latency = latency;
            this.creationTime = createTime;
        }
    }
    
    public MessageNode(Point2d pos, float radius, int id, SimTime time) 
    {
        super(pos, radius);
        nodeId = id;
        this.time = time;
        
        // generate one message, created now
        // create message id by concatenating the node id and message id
        long uuid = ((long) nodeId) * Integer.MAX_VALUE + nextMessageId++;
        MessageData msg = new MessageData(uuid, 0, time.getTime());
        messages.add(msg);
    } // end constructor
    
    public float distanceTo(MessageNode that) {
        return Point2d.distance(this.pos, that.pos);
    }

    // updates that so that its messages are a superset of this's
    public void exchangeWith(MessageNode that) {
        for (int i = 0; i < messages.size(); i++) {
            MessageData msg = messages.get(i);
            if (!that.hasReceivedMessage(msg.uuid)) {
                that.ReceiveMessage(msg);
            }
        }
    }
    
    public void ReceiveMessage(MessageData msg) {
        MessageData newMsg = new MessageData(msg.uuid, time.getTime() - msg.creationTime,
                                             msg.creationTime);
        messages.add(newMsg);
    }
    
    public boolean hasReceivedMessage(long uuid) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).uuid == uuid)
                return true;
        }
        return false;
    }
    
    abstract public void think(SimTime time);
} // end MessageNode