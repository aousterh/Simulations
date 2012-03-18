package datarecorders.btgRecorder;

import java.io.File;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class BTGRecorderParser extends DefaultHandler
 {
  private static int FILE_PATH=0;
  private static int COMPRESS_OUTPUT=1;
  private  int actTag;
	
  private String filePath;
  private boolean compressOutput;
  
  public BTGRecorderParser()
   {
	filePath="."+File.separator+"BTGRecorderOutput.txt";
	compressOutput=false;
	actTag=-1;
   }//Fine Costruttore
  
  public String getFilePath()
   { return filePath; }
  
  public boolean getCompressOutput()
  { return compressOutput; }
  
  public void startElement(String uri, String name,String qName, Attributes atts)
   {
    if(qName.equals("filePath")) 
	 { actTag=FILE_PATH;}  
    
    if(qName.equals("compressOutput")) 
     { actTag=COMPRESS_OUTPUT;}  
    
  }//Fine startElements
  
  public void characters(char[] ch,int start,int length)
   {
    String str=new String(ch,start,length); 
    if(actTag==FILE_PATH)
	 {
      filePath=str;
      actTag=-1;
     } 
    
    if(actTag==COMPRESS_OUTPUT)
	 {
     compressOutput=Boolean.valueOf(str);
     actTag=-1;
    }  
    
   }//Fine characters
 }//Fine classe ViewerParser
