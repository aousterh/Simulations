package models.messageModel;

import java.util.Iterator;
import java.util.Vector;

import engine.SimTime;
import math.Point2d;
import models.Node;


public abstract class MessageNode extends Node
{
    protected int nextMessageId = 0;
    private int nodeId;
    protected Vector messages;
    SimTime time;
    private int MAX_MESSAGES = 100;  // should be Integer.MAX_VALUE eventually
    
    public MessageNode(Point2d pos, float radius, int nodeId, SimTime time) 
    {
        super(pos, radius);
        this.time = time;
        this.nodeId = nodeId;
        
        messages = new Vector();
        
        // generate one message, created now
        // create message id by concatenating the node id and message id
        long uuid = ((long) nodeId) * MAX_MESSAGES + nextMessageId++;
        MessageData msg = new MessageData(uuid, 0, time.getTime());
        messages.add(msg);
    } // end constructor
    
    public int getNodeId()
    {
        return nodeId;
    }
    
    public int numTotalMessages()
    {
        return messages.size();
    }
    
    public int numReceivedMessages()
    {
        int i = 0;
        Iterator it = messages.iterator();
        while (it.hasNext())
        {
            MessageData msg = (MessageData) it.next();
            
            // increment count for incoming messages
            if (!msg.wasOutgoing())
                i++;
        }
        
        return i;
    }
    
    public MessageData getMessage(int index)
    {
        return (MessageData) messages.elementAt(index);
    }
    
    public float distanceTo(MessageNode that) {
        return Point2d.distance(this.pos, that.pos);
    }
    
    // updates that so that its messages are a superset of this's
    public void exchangeWith(MessageNode that) {
        Iterator i = messages.iterator();
        while (i.hasNext())
        {
            MessageData msg = (MessageData) i.next();
            if (!that.hasReceivedMessage(msg.getUuid())) {
                that.ReceiveMessage(msg);
            }
        }
    }
    
    public void ReceiveMessage(MessageData msg) {
        if (messages.size() >= MAX_MESSAGES)
            System.out.println("ERROR: cannot receive this message");
        
        MessageData newMsg = new MessageData(msg.getUuid(), time.getTime() - msg.getCreationTime(),
                                             msg.getCreationTime());
        messages.add(newMsg);
    }
    
    public boolean hasReceivedMessage(long uuid) {
        Iterator i = messages.iterator();
        while (i.hasNext())
        {
            MessageData msg = (MessageData) i.next();
            if (msg.getUuid() == uuid) {
                return true;
            }
        }
        return false;
    }
    
    abstract public void think(SimTime time);
} // end MessageNode