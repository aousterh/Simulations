#include "MessageData.h"

MessageData::MessageData(long uuid, float latency, float createTime)
{
  this->uuid = uuid;
  this->latency = latency;
  this->creationTime = createTime;
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
  return (latency == 0); // TODO: THIS MAKES SOME ASSUMPTIONS?
}

MessageData::~MessageData(){}
