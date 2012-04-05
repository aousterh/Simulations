#include"HotSpotModelBuilder.h"

#include"HotSpotModel.h"

Model* HotSpotModelBuilder::create() 
  {
   HotSpotModel *hotSpotModel=new HotSpotModel();
   //Setto i parametri il modello
   hotSpotModel->setModel(configParser->getNumNodes(),configParser->getNodeRadius(),
			  configParser->getAntennaRadius(),configParser->getPauseTime(),
			  configParser->getVMin(),configParser->getVMax(),
		          configParser->getPhysicalProp());
   //Ritorno il modello
   return hotSpotModel; 
  }//Fine create
