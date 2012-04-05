#include <stdlib.h>
#include"Utils.h"

Utils::Utils() {}
	
Utils::~Utils() {}	
 
	
char* Utils::convertStrToCharVect(std::string str)
 {
  //Riservo la memoria per contenere il vettore di dati	
  char *charVect=(char*)malloc(str.size()+1);
  if(charVect==NULL)
   {
     std::cerr<<"\nERROR: Memory allocation error... Simulation aborted!!! ";
     exit(1);
   }
  
  //Copio i caratteri della stringa nel vettore
  char *index=charVect; 
  for(int i=0;i<str.size();i++)
   {
   	*index=str[i];
   	index+=1;
   }
  
  //Chiudo la stringa 
  *index='\0';   
  
  //Ritorno il puntatore al vettore di caratteri 
  return charVect;
 }//Fine convertFilePath   	
