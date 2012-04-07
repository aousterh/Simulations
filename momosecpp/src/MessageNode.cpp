#include <stdio.h>

#include "MessageData.h"
#include "MessageNode.h"


MessageNode::MessageNode(Point2D pos, const float &radius, int nodeId, SimTime *time): Node(pos,radius)
{
  this->time = time;
  this->nodeId = nodeId;
  this->nextMessageId = 0;
  this->MAX_MESSAGES = 100;  // TODO: make this larger, small is good for debugging

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
