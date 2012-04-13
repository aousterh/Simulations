#ifndef MESSAGEDATA_H_
#define MESSAGEDATA_H_


class MessageData
{
 private:
  long uuid;
  float latency;
  float creationTime;
  bool outgoing;
	
 public:
  MessageData(long uuid, float latency, float createTime, bool outgoing);
  ~MessageData();

  long getUuid();
  float getLatency();
  float getCreationTime();
  bool wasOutgoing();
};

#endif /*SIMPLENODE_H_*/
