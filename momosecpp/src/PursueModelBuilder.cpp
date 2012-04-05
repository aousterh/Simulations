#include"PursueModelBuilder.h"

#include"PursueModel.h"

Model* PursueModelBuilder::create() 
  {
   PursueModel *pursueModel=new PursueModel();
   //Setto i parametri il modello
   pursueModel->setModel(configParser->getNumNodes(),configParser->getNodeRadius(),
		               configParser->getAntennaRadius(),configParser->getAlpha(),
		               configParser->getVMin(),configParser->getVMax(),
		               configParser->getTargetRadius(),configParser->getPhysicalProp());
   //Ritorno il modello
   return pursueModel; 
  }//Fine create
