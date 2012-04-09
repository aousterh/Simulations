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
  double p;  // the proportion of nodes in the graph that this node should
             // be friends with (approximately)
  int numNodes;

 public:
  int *trust_distances;  // an array storing trust distances to all other nodes
                         // -1 means that this hasn't been computed yet

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
  void initFriendships(int numNodes);
  void setFriendship(int friend_num, bool status);
  //  double getP();
  int numFriends();
  int trustDistance(MessageNode *that);
};

#endif /*MESSAGENODE_H_*/
