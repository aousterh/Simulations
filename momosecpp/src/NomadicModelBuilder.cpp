#include"NomadicModelBuilder.h"

#include"NomadicModel.h"

Model* NomadicModelBuilder::create() 
  {
   NomadicModel *nomadicModel=new NomadicModel();
   //Setto i parametri il modello
   nomadicModel->setModel(configParser->getNumNodes(),configParser->getNodeRadius(),
		               configParser->getAntennaRadius(),configParser->getAlpha(),
		               configParser->getVMin(),configParser->getVMax(),
		               configParser->getPauseTime(),configParser->getPhysicalProp());
   //Ritorno il modello
   return nomadicModel; 
  }//Fine create
