#include"RandomWalkMessageModelBuilder.h"
#include"RandomWalkMessageModel.h"

Model* RandomWalkMessageModelBuilder::create() 
{
  RandomWalkMessageModel *randomWalkMessageModel=new RandomWalkMessageModel();
  randomWalkMessageModel->setModel(configParser->getNumNodes(), configParser->getNodeRadius(),
				   configParser->getAntennaRadius(), configParser->getPauseTime(),
				   configParser->getVMin(), configParser->getVMax(),
				   configParser->getPhysicalProp(), configParser->getProbability(),
				   configParser->getNodeTrustDistance(), configParser->getMsgTrustDistance(),
				   configParser->getNodeExchangeNum(), configParser->getMsgExchangeNum(),
				   configParser->getPercentAdversaries(), configParser->getAdversaryProbability(),
				   configParser->getAdversaryMsgCreationProbability(),
				   configParser->getCollaboratorMsgCreationProbability(),
				   configParser->getUseFriendships());
  return randomWalkMessageModel; 
}
