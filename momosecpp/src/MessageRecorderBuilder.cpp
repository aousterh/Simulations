#include"MessageRecorderBuilder.h"

#include"MessageRecorder.h"

DataRecorder* MessageRecorderBuilder::create() 
{
  MessageRecorder *messageRecorder=new MessageRecorder();
  messageRecorder->setOutputFilePath(configParser->getFilePath());
  messageRecorder->setCompressOutput(configParser->getCompressOutput());
  return messageRecorder;  
} // end create
	 
  
  
