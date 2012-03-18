package datarecorders.ns2MobilityRecorder;

import java.io.File;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class NS2MobilityRecorderParser extends DefaultHandler
 {
  private static int FILE_PATH=0;
  private  int actTag;
	
  private String filePath;
  
  public NS2MobilityRecorderParser()
   {
	filePath="."+File.separator+"ns2MobilityRecorderOutput.xml";
	actTag=-1;
   }//Fine Costruttore
  
  public String getFilePath()
   { return filePath; }
  
  public void startElement(String uri, String name,String qName, Attributes atts)
   {
    if(qName.equals("filePath")) 
	 { actTag=FILE_PATH;}  
    }//Fine startElements
  
  public void characters(char[] ch,int start,int length)
   {
    String str=new String(ch,start,length); 
    if(actTag==FILE_PATH)
	 {
      filePath=str;
      actTag=-1;
     }     
   }//Fine characters
 }//Fine classe ViewerParser
