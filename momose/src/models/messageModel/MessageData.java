/*
 * MessageData objects store the information associated with a message. 
 */

package models.messageModel;

public class MessageData {
    private long uuid;
    private float latency; // time it took for message to arrive here
    // (0 for outgoing messages)
    private float creationTime;
    
    public MessageData(long uuid, float latency, float createTime) {
        this.uuid = uuid;  // uniquely identifies message and sender
        this.latency = latency;
        this.creationTime = createTime;
    }
    
    public long getUuid()
    {
        return uuid;
    }
    
    public float getLatency()
    {
        return latency;
    }
    
    public float getCreationTime()
    {
        return creationTime;
    }
    
    public boolean wasOutgoing()
    {
        // this assumes that messages cannot be received in the same
        // time step that they are created
        return (latency == 0);
    }
} // end MessageData