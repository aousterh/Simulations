#include"EraModelBuilder.h"

#include"EraModel.h"

Model* EraModelBuilder::create() 
  {
   EraModel *eraModel=new EraModel(configParser->getNumNodes(),configParser->getNodeRadius(),
			           configParser->getAntennaRadius(),configParser->getPhysicalProp(),
			           configParser->getPauseTime(),configParser->getColInterval(),
			           configParser->getVMin(),configParser->getVMax());  
   //Ritorno il modello
   return eraModel; 
  }//Fine create
