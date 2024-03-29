#include "MessageRecorder.h"
#include "Color.h"
#include "Point2D.h"

#include"MessageNode.h"
#include "Utils.h"

#include <map>
#include <stdlib.h>

MessageRecorder::MessageRecorder(): outputFile()
 {
  setName("MessageRecorder");	
  nodes=new vector<Node*>();
  simTime=NULL;
  outputFilePath="/u/aousterh/IndependentWork/Simulations/momosecpp/output/messageRecorderOutput.xml";
  compressOutput=false;
 } // end constructor

MessageRecorder::~MessageRecorder(){}

void MessageRecorder::setup(vector<Model*>* models, Scenario *scenario, SimTime *simTime)
{
  // create the list of nodes
  setupNodes(models);  
	
  // create the CSV file to store output data
  createCsvFile(simTime);
	
  // write static info to the file
  writeStaticInfo(simTime, scenario);
	
  // get a reference to time
  this->simTime=simTime;
} // end setup
 
void MessageRecorder::setupNodes(vector<Model*>* models)
{
  vector<Model*>::iterator i;
  for(i=models->begin();i!=models->end();i++)
    {
      // pull the nodes from the model and add them
      addNodesToVector(nodes,(*i)->getNodes());
    }	
} // end setupNodes

void MessageRecorder::addNodesToVector(vector<Node*>  *nodes,vector<Node*> *nodIter) 
{
  vector<Node*>::iterator i;
  for(i=nodIter->begin();i!=nodIter->end();i++)
    {nodes->push_back(*i);}
} // end addNodesToVector
 
void MessageRecorder::createCsvFile(SimTime *simTime)
{
  // convert the string into an array of characters
  char *filePathVect=Utils::convertStrToCharVect(outputFilePath);
  
  // create the file to save the simulation results in
  outputFile.open(filePathVect,ios::out); 
  if(!outputFile)
    {
      cerr<<outputFilePath;
      cerr<<"\nERROR: Impossible to open file... Simulation aborted!!! ";
      exit(1);
    }
  free(filePathVect); 
} // end createCsvFile
  
void MessageRecorder::writeStaticInfo(SimTime *simTime, Scenario *scenario) 
{
  // write some of the scenario info
  outputFile<<"width: "<<scenario->getWidth()<<", height: "<<scenario->getHeight()<<endl;
	
  // write base node info
  writeBaseNode();
  char *filePathVect=Utils::convertStrToCharVect(outputFilePath); // ??
} // end writeStaticInfo
  
void MessageRecorder::writeBaseNode() 
{
  num_adversaries = 0;
  vector<Node*>::iterator it;
  for(it = nodes->begin(); it != nodes->end(); it++)
    {
      MessageNode *node = (MessageNode*) (*it);
      if (node->getType() == ADVERSARY)
	num_adversaries++;
    }

  // write the number of nodes
  outputFile<<"Num nodes, "<<nodes->size()<<", "<< num_adversaries<<endl;
  
} // end writeBaseNode
 

void MessageRecorder::record(SimTime *simTime)
{
  // do nothing
} // end record
 
void MessageRecorder::close()
{	
  // write the message info
  writeMessageInfo();

  // clear the buffer and close the file
  outputFile.flush();
  outputFile.close();
	 
  // compress the output if necessary
  if(compressOutput==true)	 
    {
      //rendo il path del file da comprimere	    
      char *filePathVect=Utils::convertStrToCharVect(outputFilePath); 
      //Scopro se esiste l'applicazione di compressione e comprimo
      FILE *fGzip=fopen("/bin/gzip","r");
      
      if(fGzip!=NULL)
        {
	  fclose(fGzip);	
          execl("/bin/gzip","-f",filePathVect,0);
	} 	
    }    
  
} // end close
 
void MessageRecorder::writeMessageInfo()
{
  outputFile<<"uuid, latency"<<endl;

  float max_creation_time = 0;
  vector<Node*>::iterator it;
  // calculate the max msg creation time
  for (it = nodes->begin(); it != nodes->end(); it++)
    {
      MessageNode *node = (MessageNode *) *it;

      map<long, MessageData*> *message_map = node->getMessageMap();
      map<long, MessageData*>::iterator map_it;
      for (map_it = message_map->begin(); map_it != message_map->end(); map_it++)
	{
	  MessageData *msg = (*map_it).second;
	  if (msg->getCreationTime() > max_creation_time)
	    max_creation_time = msg->getCreationTime();
	}
    }

  float min_time = 0.2 * max_creation_time;
  float max_time = 0.8 * max_creation_time;

  // only care about messages received by collaborators
  for (it = nodes->begin() + num_adversaries; it != nodes->end(); it++)
    {
      MessageNode *node = (MessageNode *) *it;
 
      map<long, MessageData*> *message_map = node->getMessageMap();
      map<long, MessageData*>::iterator map_it;
      for (map_it = message_map->begin(); map_it != message_map->end(); map_it++)
	{
	  MessageData *msg = (*map_it).second;
	  // record this msg if it was not sent by this node
	  if (!msg->wasOutgoing() && (msg->getCreationTime() > min_time &&
				      msg->getCreationTime() < max_time)) {
	    MessageNode *sender = msg->getSender();
	    // output the msg uuid, latency, sender id, recipient id
	    outputFile<<msg->getUuid()<<", "<< msg->getLatency() <<", "<< msg->getSender()->getNodeId() <<", "<<node->getNodeId() <<endl;
	  }
	}

    }

  /*
  int numNodes = nodes->size();

  // map from uuids to lists of latencies
  map<long, *list<float>> latencies;

  vector<Node*>::iterator it;
  map<long, list<float>>::iterator mapit;	    
  for (it = nodes->begin(); it != nodes->end(); it++)
    {
      MessageNode *node = (MessageNode*) *it;
      
      // add latency to the map
      for (int i = 0; i < node->numTotalMessages(); i++)
	{
	  MessageData *msg = node->getMessage(i);
	  if (!msg->wasOutgoing()) {
	    mapit = latencies.find(msg->getUuid());
	    
	    if (mapit == latencies.end()) {
	      // we need to add this element
	      list<float> new_list;
	      new_list.push_back(msg->getLatency());
	      latencies.insert(pair<long, list<float>>(msg->getUuid(), &new_list));
	    } else {
	      // we add the latency to an existing array
	      list<float> *existing_list = (*mapit).second;
	      existing_list->push_back(msg->getLatency());
	    }
	  }
	}
    }

  //  a list of coordinates, alternating x_coord list then y_coord list
  list<float> coordinates[2*numNodes];

  for (mapit = latencies.begin(); mapit != latencies.end(); mapit++)
    {
      list<float> *l_list = (*mapit).second;
      l_list->sort();
    }

  // TODO: create y coordinates, then y coordinate cumulative, then output everything
  */
}
