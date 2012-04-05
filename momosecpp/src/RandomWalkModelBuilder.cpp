#include"RandomWalkModelBuilder.h"

#include"RandomWalkModel.h"

Model* RandomWalkModelBuilder::create() 
  {
   RandomWalkModel *randomWalkModel=new RandomWalkModel();
   //Setto i parametri il modello
   randomWalkModel->setModel(configParser->getNumNodes(),configParser->getNodeRadius(),
						   configParser->getAntennaRadius(),configParser->getPauseTime(),
						   configParser->getVMin(),configParser->getVMax(),
		                                   configParser->getPhysicalProp());
   //Ritorno il modello
   return randomWalkModel; 
  }//Fine create