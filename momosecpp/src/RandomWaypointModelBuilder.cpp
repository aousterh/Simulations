#include"RandomWaypointModelBuilder.h"

#include"RandomWaypointModel.h"

Model* RandomWaypointModelBuilder::create() 
  {
   RandomWaypointModel *randomWaypointModel=new RandomWaypointModel();
   //Setto i parametri il modello
   randomWaypointModel->setModel(configParser->getNumNodes(),configParser->getNodeRadius(),
						   configParser->getAntennaRadius(),configParser->getPauseTime(),
						   configParser->getVMin(),configParser->getVMax(),
		                                   configParser->getPhysicalProp());
   //Ritorno il modello
   return randomWaypointModel; 
  }//Fine create
