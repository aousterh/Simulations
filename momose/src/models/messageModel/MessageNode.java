package models.messageModel;

import java.util.ArrayList;

import math.Point2d;
import models.Node;


public abstract class MessageNode extends Node
{
    int id;
    ArrayList<MessageData> outgoingMessages = new ArrayList<MessageData>();
    ArrayList<MessageData> receivedMessages = new ArrayList<MessageData>();
    
    private class MessageData {
        long uuid;
        float latency; // time for message to arrive here (0 for outgoing messages)
        float creationTime;
        
        private MessageData(long uuid, float latency, float creatTime) {
            this.uuid = uuid;
            this.latency = latency;
        }
    }
    
    public MessageNode(Point2d pos, float radius, int id, SimTime time) 
    {
        super(pos,radius);
        this.id = id;
        
        // generate one message, created now
        // create message id by concatenating the uuid and message id
        long uuid = ((long) id) * Integer.MAX_VALUE + 0;
        MessageData msg = new MessageData(uuid, 0, time.getTime());
        outgoingMessages.add(msg);
    }//end constructor

}//end MessageNode

