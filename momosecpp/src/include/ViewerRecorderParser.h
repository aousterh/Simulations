#ifndef VIEWERRECORDERPARSER_H_
#define VIEWERRECORDERPARSER_H_

#include<iostream>
#include<string>

#include"BaseParser.h"

class ViewerRecorderParser: public BaseParser
 {
   private:
	std::string filePath;  
        bool compressOutput; 
	int actTag;	
         
	 
   public:
        static const int FILE_PATH=0;
        static const int COMPRESS_OUTPUT=1;
  
      ViewerRecorderParser();
      virtual ~ViewerRecorderParser();
   
      inline std::string getFilePath()
       { return filePath; } 
       
      inline bool getCompressOutput()
       { return compressOutput; } 
    
      //Metodi virtuali riscritti
      void OnStartElement(const XML_Char* name, const XML_Char** attrs);
      void OnEndElement(const XML_Char* name);
      void OnCharacterData(const XML_Char* data, int len);
   
     

};

#endif /*VIEWERRECORDERPARSER_H_*/
