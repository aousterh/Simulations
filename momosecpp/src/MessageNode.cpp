#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <algorithm>

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
  this->MAX_MESSAGES = INT_MAX;  // TODO: make this larger, small is good for debugging
  this->trust_distances = NULL;  // must be initialized by calling function
  this->temp_messages = NULL;

  messages = new vector<long>();
  message_map = new map<long, MessageData*>();

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
  // inserts a msg into necessary data structures - assumes that msg's uuid
  // isn't alreay in the messages map

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

void MessageNode::pushMessagesTo(MessageNode *that, int msg_exchange_num, bool use_friendships)
{
  // this Node sends messages to that Node
  // send the most recent msg_exchange_num messages
  // first send messages from your own friends (in order received)
  // then send msgs from adversaries if you can still send more
  vector<MessageData*> *that_temp_msgs = new vector<MessageData*>();
  int sent = 0;
  // iterate in reverse order to examine newest first
  // start by sending messages from friends
  for (int i = messages->size() - 1; i >= 0; i--)
    {
      if (sent == msg_exchange_num)
	break;

      long uuid = (*messages)[i];

      MessageData *msg = (MessageData*) (*message_map)[uuid];
      MessageNode *sender = msg->getSender();
      // printf("%d, %d\n", uuid, sender->getNodeId());
      if (this->trustDistance(sender) != -1 || !use_friendships)
	{
	  that_temp_msgs->push_back(msg);
	  sent++;
	}

    }


  if (this->type != ADVERSARY && use_friendships)
    {
      // send msgs from adversaries if there is leftover capacity
      for (int i = messages->size() - 1; i >= 0; i--)
	{
	  if (sent == msg_exchange_num)
	    break;
	  
	  long uuid = (*messages)[i];
	  
	  MessageData *msg = (MessageData*) (*message_map)[uuid];
	  MessageNode *sender = msg->getSender();
	  // printf("%d, %d\n", uuid, sender->getNodeId());
	  if (this->trustDistance(sender) == -1)  // TODO: change this if you vary probabilities!
	    {
	      that_temp_msgs->push_back(msg);
	      sent++;
	    }
	  
	}
    }

  // assign messages to "that" to receive at the end of the timestep
  that->assignTempMessages(that_temp_msgs);
}

void MessageNode::assignTempMessages(vector<MessageData*> *new_temp_messages)
{
  if (temp_messages == NULL)
    temp_messages = new vector<MessageData*>(); 

  // add all msgs to the end of the list
  for (int i = 0; i < new_temp_messages->size(); i++)
    {
      MessageData *msg = (MessageData*) (*new_temp_messages)[i];
      temp_messages->push_back(msg);
    }
}

void MessageNode::mergeMessages()
{
  if (temp_messages != NULL)
    {
      // shuffle order of all msgs so that no sender is priortized
      random_shuffle(temp_messages->begin(), temp_messages->end());

      //     for (int i = 0; i < temp_messages->size(); i++)
      for (int i = temp_messages->size() - 1; i > 0; i--)
	{
	  MessageData *msg = (MessageData *) (*temp_messages)[i];
	  this->ReceiveMessage(msg);
	} 
      delete temp_messages;
      temp_messages = NULL;
    }
}

void MessageNode::ReceiveMessage(MessageData *msg)
{
  if (messages->size() >= this->MAX_MESSAGES)
    printf("ERROR: cannot receive messages\n");
  // if msg has been received before, remove it from its old place in the list
  if (this->hasReceivedMessage(msg->getUuid()))
    {
      for (int i = messages->size() - 1; i >= 0; i--)
	{
	  long uuid = (*messages)[i];
	  if (uuid == msg->getUuid())
	    {
	      messages->erase(messages->begin() + i);
	      break;
	    }
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
