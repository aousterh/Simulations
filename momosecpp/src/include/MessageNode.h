#ifndef MESSAGENODE_H_
#define MESSAGENODE_H_

#include <map>
#include <vector>

#include"MessageData.h"
#include"Node.h"
#include"Point2D.h"
#include"SimTime.h"

typedef enum {
  COLLABORATOR,
  ADVERSARY
} node_type;

class MessageNode: public Node
{
 protected:
  int nextMessageId;
  int nodeId;
  float localTime;
  float pauseTime;  
  float vMax;
  float vMin;
  vector<long> *messages;  // list of uuids in order from most recently received to least recent
  map<long, MessageData*> *message_map;  // map from all message uuid's that have been received to the msg
  SimTime *simTime;
  int MAX_MESSAGES;  // should be max value eventually, and static!
  double p;  // the proportion of nodes in the graph that this node should
             // be friends with (approximately)
  int numNodes;
  node_type type;  // collaborator or adversary?

 public:
  int *trust_distances;  // an array storing trust distances to all other nodes
                         // -1 means that this hasn't been computed yet

 public:
  MessageNode(Point2D pos, const float &radius, int nodeId, SimTime *simTime);
  virtual ~MessageNode();
	
  void insertMessage(MessageData *msg, float latency);
  int getNodeId();
  int numTotalMessages();
  int numReceivedMessages();
  map<long, MessageData*> *getMessageMap();
  float distanceTo(MessageNode *that);
  void pushMessagesTo(MessageNode *that, int msg_exchange_num);
  void ReceiveMessage(MessageData *msg);
  bool hasReceivedMessage(long uuid);
  void initFriendships(int numNodes, int num_adversaries);
  void setFriendship(int friend_num, bool status);
  int numFriends();
  int trustDistance(MessageNode *that);
  void setType(node_type type);
  node_type getType();
  double getP();
  void createNewMessage();
};

#endif /*MESSAGENODE_H_*/
