#ifndef MESSAGEDATA_H_
#define MESSAGEDATA_H_

class MessageNode;

class MessageData
{
 private:
  long uuid;
  MessageNode *sender;
  float latency;
  float creationTime;
  bool outgoing;
	
 public:
  MessageData(long uuid, MessageNode *sender, float latency, float createTime, bool outgoing);
  ~MessageData();

  long getUuid();
  MessageNode *getSender();
  float getLatency();
  float getCreationTime();
  bool wasOutgoing();
};

#endif /*SIMPLENODE_H_*/
