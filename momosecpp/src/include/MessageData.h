#ifndef MESSAGEDATA_H_
#define MESSAGEDATA_H_


class MessageData
{
 private:
  long uuid;
  float latency;  // 0 for outgoing messages
  float creationTime;
	
 public:
  MessageData(long uuid, float latency, float createTime);
  ~MessageData();

  long getUuid();
  float getLatency();
  float getCreationTime();
  bool wasOutgoing();
};

#endif /*SIMPLENODE_H_*/
