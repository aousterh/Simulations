#include <stdio.h>
#include <stdlib.h>

#include "MessageData.h"
#include "MessageNode.h"


MessageNode::MessageNode(Point2D pos, const float &radius, int nodeId, SimTime *simTime): Node(pos,radius)
{
  this->simTime = simTime;
  this->vMax = 5;
  this->vMin = 0;
  this->pauseTime = 1;
  this->nodeId = nodeId;
  this->nextMessageId = 0;
  this->MAX_MESSAGES = 100000;  // TODO: make this larger, small is good for debugging
  this->trust_distances = NULL;  // must be initialized by calling function

  messages = new vector<MessageData*>();
  message_set = new set<long>();

  // generate one message, created now, at the start
  createNewMessage();
}

MessageNode::~MessageNode(){}

void MessageNode::setType(node_type type, double probability)
{
  this->type = type;
  this->p = probability;
}

node_type MessageNode::getType()
{
  return type;
}

void MessageNode::createNewMessage()
{
  // create msg id by concatenating the node id and the message id
  long uuid = ((long) nodeId) * this->MAX_MESSAGES + nextMessageId++;
  insertMessage(new MessageData(uuid, this, 0, simTime->getTime(), true));
}

void MessageNode::insertMessage(MessageData *msg)
{
  messages->push_back(msg);
  message_set->insert(msg->getUuid());
  return;
}

int MessageNode::getNodeId()
{
  return nodeId;
}

int MessageNode::numTotalMessages()
{
  return messages->size();
}

double MessageNode::getP()
{
  return p;
}

int MessageNode::numReceivedMessages()
{
  int i = 0;
  vector<MessageData*>::iterator it;
  for (it = messages->begin(); it != messages->end(); it++)
    {
      MessageData *msg = (MessageData*) *it;

      if (!msg->wasOutgoing())
	i++;
    }

  return i;
}

MessageData *MessageNode::getMessage(int index)
{
  return (MessageData *) messages->at(index);
}

float MessageNode::distanceTo(MessageNode *that)
{
  return this->pos.distance(that->getPosition());
}

void MessageNode::pushMessagesTo(MessageNode *that, int msg_exchange_num, 
				 int msg_trust_distance)
{
  // this Node sends messages to that Node
  // send the most recent msg_exchange_num messages from senders
  // within trust distance msg_trust_distance of that
  int sent = 0;
  vector<MessageData*>::reverse_iterator rit;
  // iterate in reverse order to examine newest first
  for (rit = messages->rbegin(); rit <  messages->rend(); ++rit)
    {
      if (sent == msg_exchange_num)
	break;

      MessageData *msg = (MessageData*) *rit;

      MessageNode *sender = msg->getSender();
      // don't check to see if received yet - just send it
      if (that->trustDistance(sender) <= msg_trust_distance) {
	that->ReceiveMessage(msg);
	sent++;
      }
    }
}

void MessageNode::ReceiveMessage(MessageData *msg)
{
  if (messages->size() >= this->MAX_MESSAGES)
    printf("ERROR: cannot receive messages\n");
  if (!this->hasReceivedMessage(msg->getUuid()))
    {
      MessageData *newMsg = new MessageData(msg->getUuid(), msg->getSender(), 
					    simTime->getTime() - msg->getCreationTime(),
					    msg->getCreationTime(), false);
      insertMessage(newMsg);
    }
}

bool MessageNode::hasReceivedMessage(long uuid)
{
  set<long>::iterator it;
  it = message_set->find(uuid);
  return (it != message_set->end());
}

void MessageNode::initFriendships(int numNodes)
{
  this->numNodes = numNodes;
  this->trust_distances = (int *) calloc(numNodes, sizeof(int));
  for (int i = 0; i < numNodes; i++)
      trust_distances[i] = -1;
  trust_distances[nodeId] = 0;  // at a trust distance of 0 from yourself
}

void MessageNode::setFriendship(int friend_num, bool status)
{
  trust_distances[friend_num] = 1;
}

int MessageNode::numFriends()
{
  // returns the number of people at trust distance 1

  int numfriends = 0;
  for (int i = 0; i < numNodes; i++)
    {
      if (trust_distances[i] == 1)
	numfriends++;
    }
  return numfriends;
}

int MessageNode::trustDistance(MessageNode *that)
{
  return trust_distances[that->getNodeId()];
}
