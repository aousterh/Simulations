#include "MessageData.h"

MessageData::MessageData(long uuid, float latency, float createTime, bool outgoing)
{
  this->uuid = uuid;
  this->latency = latency;
  this->creationTime = createTime;
  this->outgoing = outgoing;
}

long MessageData::getUuid()
{
  return uuid;
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

MessageData::~MessageData(){}
