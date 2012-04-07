#ifndef MESSAGENODE_H_
#define MESSAGENODE_H_

#include<vector>

#include"MessageData.h"
#include"Node.h"
#include"Point2D.h"
#include"SimTime.h"


class MessageNode: public Node
{
 protected:
  int nextMessageId;
  int nodeId;
  vector<MessageData*> *messages;
  SimTime *time;
  int MAX_MESSAGES;  // should be max value eventually, and static!
	
 public:
  MessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time);
  virtual ~MessageNode();
	
  int getNodeId();
  int numTotalMessages();
  int numReceivedMessages();
  MessageData *getMessage(int index);
  float distanceTo(MessageNode *that);
  void exchangeWith(MessageNode *that);
  void ReceiveMessage(MessageData *msg);
  bool hasReceivedMessage(long uuid);
};

#endif /*MESSAGENODE_H_*/
