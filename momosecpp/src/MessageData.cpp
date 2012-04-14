#include "MessageData.h"

MessageData::MessageData(long uuid, MessageNode *sender, float latency, float createTime, bool outgoing)
{
  this->uuid = uuid;
  this->sender = sender;
  this->latency = latency;
  this->creationTime = createTime;
  this->outgoing = outgoing;
}

MessageData::~MessageData(){}

long MessageData::getUuid()
{
  return uuid;
}

MessageNode *MessageData::getSender()
{
  return sender;
}

float MessageData::getLatency()
{
  return latency;
}

float MessageData::getCreationTime()
{
  return creationTime;
}

bool MessageData::wasOutgoing()
{
  return outgoing;
}
