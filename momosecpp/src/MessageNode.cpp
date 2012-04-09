#include <stdio.h>
#include <stdlib.h>

#include "MessageData.h"
#include "MessageNode.h"


MessageNode::MessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time): Node(pos,radius)
{
  this->time = time;
  this->nodeId = nodeId;
  this->nextMessageId = 0;
  this->MAX_MESSAGES = 1000;  // TODO: make this larger, small is good for debugging
  this->trust_distances = NULL;  // must be initialized by calling function

  messages = new vector<MessageData*>();

  // generate one message, created now, at the start
  // create msg id by concatenating the node id and the message id
  long uuid = ((long) nodeId) * this->MAX_MESSAGES + nextMessageId++;
  MessageData *msg = new MessageData(uuid, 0, time->getTime());
  messages->push_back(msg);
}

MessageNode::~MessageNode(){}

int MessageNode::getNodeId()
{
  return nodeId;
}

int MessageNode::numTotalMessages()
{
  return messages->size();
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

void MessageNode::exchangeWith(MessageNode *that)
{
  vector<MessageData*>::iterator it;
  for (it = messages->begin(); it != messages->end(); it++)
    {
      MessageData *msg = (MessageData*) *it;
      if (!that->hasReceivedMessage(msg->getUuid()))
	that->ReceiveMessage(msg);
    }
}

void MessageNode::ReceiveMessage(MessageData *msg)
{
  if (messages->size() >= this->MAX_MESSAGES)
    printf("ERROR: cannot receive messages\n");
  MessageData *newMsg = new MessageData(msg->getUuid(), time->getTime() - msg->getCreationTime(),
					msg->getCreationTime());
  messages->push_back(newMsg);
}

bool MessageNode::hasReceivedMessage(long uuid)
{
  vector<MessageData*>::iterator msg_it;
  for (msg_it = messages->begin(); msg_it < messages->end(); msg_it++)
    {
      MessageData *msg = (MessageData*) *msg_it;
      if (msg->getUuid() == uuid)
	return true;
    } 
  return false;
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

/*
double MessageNode::getP()
{
  return p;
}
*/

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
