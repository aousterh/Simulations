#include"RandomWaypointMessageModelBuilder.h"
#include"RandomWaypointMessageModel.h"

#include <stdio.h>

Model* RandomWaypointMessageModelBuilder::create() 
{
  RandomWaypointMessageModel *randomWaypointMessageModel=new RandomWaypointMessageModel();
  randomWaypointMessageModel->setModel(configParser->getNumNodes(), configParser->getNodeRadius(),
				       configParser->getAntennaRadius(), configParser->getPauseTime(),
				       configParser->getVMin(), configParser->getVMax(),
				       configParser->getPhysicalProp(), configParser->getProbability(),
				       configParser->getNodeTrustDistance(), configParser->getMsgTrustDistance(),
				       configParser->getNodeExchangeNum(), configParser->getMsgExchangeNum(),
				       configParser->getPercentAdversaries(), configParser->getAdversaryProbability(),
				       configParser->getAdversaryMsgCreationProbability(),
				       configParser->getCollaboratorMsgCreationProbability(),
				       configParser->getUseFriendships());
  return randomWaypointMessageModel; 
}
