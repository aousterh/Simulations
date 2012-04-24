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

  messages = new vector<long>();
  message_map = new map<long, MessageData*>();

  // generate one message, created now, at the start
  //  createNewMessage();
}

MessageNode::~MessageNode(){}

void MessageNode::setType(node_type type)
{
  this->type = type;
}

node_type MessageNode::getType()
{
  return type;
}

void MessageNode::createNewMessage()
{
  // create msg id by concatenating the node id and the message id
  long uuid = ((long) nodeId) * this->MAX_MESSAGES + nextMessageId++;
  insertMessage(new MessageData(uuid, this, 0, simTime->getTime(), true), 0);
}

void MessageNode::insertMessage(MessageData *msg, float latency)
{
  messages->push_back(msg->getUuid());
  message_map->insert(make_pair(msg->getUuid(), msg));
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
  map<long, MessageData*>::iterator map_it;
  for (map_it = message_map->begin(); map_it != message_map->end(); map_it++)
    {
      MessageData *msg = (MessageData*) (*map_it).second;

      if (!msg->wasOutgoing())
	i++;
    }

  return i;
}

map<long, MessageData*> *MessageNode::getMessageMap()
{
  return message_map;
}

float MessageNode::distanceTo(MessageNode *that)
{
  return this->pos.distance(that->getPosition());
}

void MessageNode::pushMessagesTo(MessageNode *that, int msg_exchange_num)
{
  // this Node sends messages to that Node
  // send the most recent msg_exchange_num messages
  // first send messages from your own friends (in order received)
  // then send msgs from adversaries if you can still send more
  vector<MessageData*> msgs_to_send; 
  //printf("sending msgs, %d -> %d\n", this->getNodeId(), that->getNodeId());
  int sent = 0;
  vector<long>::reverse_iterator rit = messages->rbegin();
  // iterate in reverse order to examine newest first
  // start by sending messages from friends
  while (rit < messages->rend() && sent < msg_exchange_num)
    {
      long uuid = (long) *(rit++);
      MessageData *msg = (MessageData*) (*message_map)[uuid];
      MessageNode *sender = msg->getSender();
      // printf("%d, %d\n", uuid, sender->getNodeId());
      if (this->trustDistance(sender) != -1)
	{
	  msgs_to_send.push_back(msg);
	  sent++;
	}
    }

 
  // send msgs from adversaries if there is leftover capacity
    while (rit < messages->rend() && sent < msg_exchange_num)
    {
      long uuid = (long) *(rit++);
      MessageData *msg = (MessageData*) (*message_map)[uuid];
      MessageNode *sender = msg->getSender();
      if (this->trustDistance(sender) == -1)
	{
	  msgs_to_send.push_back(msg);
	  sent++;
	}
    }


  // send all msgs in reverse order so that their importance order is preserved
  for (int i = msgs_to_send.size() - 1; i >= 0; i--)
    {
      MessageData *msg = (MessageData *) msgs_to_send[i];
      that->ReceiveMessage(msg);
      //      printf("sending: %d, %d -> %d\n", msg->getUuid(), this->getNodeId(), that->getNodeId());
    }

}

void MessageNode::ReceiveMessage(MessageData *msg)
{
  if (messages->size() >= this->MAX_MESSAGES)
    printf("ERROR: cannot receive messages\n");
  // if msg has been received before, remove it from its old place in the list
  if (this->hasReceivedMessage(msg->getUuid()))
    {
      int i = messages->size() - 1;
      vector<long>::reverse_iterator rit = messages->rbegin();
      while (rit < messages->rend())
	{
	  long uuid = (long) *(rit++);
	  if (uuid == msg->getUuid())
	    {
	      messages->erase(messages->begin() + i);
	      break;
	    }
	  
	  i--;
	}
    }
  // add msg to front of list
  float latency = simTime->getTime() - msg->getCreationTime(); 
  MessageData *newMsg = new MessageData(msg->getUuid(), msg->getSender(), latency,
					msg->getCreationTime(), false);
  insertMessage(newMsg, latency);
}

bool MessageNode::hasReceivedMessage(long uuid)
{
  map<long, MessageData*>::iterator it;
  it = message_map->find(uuid);
  return (it != message_map->end());
}

void MessageNode::initFriendships(int numNodes, int num_adversaries)
{
  // initialize this node to be friends with only other adversaries (if it's an adversary)
  // or only other collaborators (if it's a collaborator)
  this->numNodes = numNodes;

  this->trust_distances = (int *) calloc(numNodes, sizeof(int));
  if (this->type == ADVERSARY)
    {
      for (int i = 0; i < num_adversaries; i++)
	trust_distances[i] = 1;  // friends with adversaries
      for (int i = num_adversaries; i < numNodes; i++)
	trust_distances[i] = -1; // not friends with collaborators
    }
  else
    {
      for (int i = 0; i < num_adversaries; i++)
	trust_distances[i] = -1;  // not friends with adversaries
      for (int i = num_adversaries; i < numNodes; i++)
	trust_distances[i] = 1;  // friends with collaborators
    }
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
