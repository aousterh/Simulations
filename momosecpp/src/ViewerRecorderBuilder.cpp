#include"ViewerRecorderBuilder.h"

#include"ViewerRecorder.h"

DataRecorder* ViewerRecorderBuilder::create() 
  {
    ViewerRecorder *viewerRecorder=new ViewerRecorder();
    viewerRecorder->setOutputFilePath(configParser->getFilePath());
    viewerRecorder->setCompressOutput(configParser->getCompressOutput());
    return viewerRecorder;  
  }//Fine create
	 
  
  