package utils;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.filechooser.FileFilter;



public class MomoseFileFilter extends FileFilter 
 {
  private String descr;	
  private String est;
  private Vector estVect;
 
  public MomoseFileFilter(String type)
  {
   descr="Move file";	  
   est="xml";
   estVect=new Vector();
   
   if(type.equals("config"))
    {
	 estVect.add("xml");  
	 descr="Move config-file";
    } 
   if(type.equals("scenario"))
    {  
	 descr="Move scenario-file";
	 estVect.add("xml");
    }
   if(type.equals("tracefile"))
    {  
   	 descr="Move trace-file";
    	estVect.add("xml");
    }	
   
   if(type.equals("compressed"))
    { 
   	 descr="Compressed Move trace-file";
   	 estVect.add("gz");
    }	
   
   if(type.equals("alltracefile"))
   { 
  	 descr="All Move trace-file";
  	 estVect.add("xml");
  	 estVect.add("gz");
   }	
   
   if(type.equals("svg"))
    {
	 descr="Scalable Vector Graphics";
	 estVect.add("svg");
    }  
  }//Fine costruttore
  
  public boolean accept(File f) 
   {
    if(f.isDirectory()) 
     {return true;}

        String extension=getExtension(f);
        if(extension != null) 
         {
          Iterator i=estVect.iterator();
          while(i.hasNext())
           {
        	String iEst=(String)i.next();  
        	if(iEst.equals(extension))
        	return true;	
           }	  
          
         }
     return false;
    }//File accept

   public String getDescription() 
     {return descr;}
    
   public static String getExtension(File f) 
     {
      String ext=null;
      String s=f.getName();
      int i=s.lastIndexOf('.');

      if((i>0)&&(i<s.length()-1)) 
          {ext=s.substring(i+1).toLowerCase();}
     
      return ext;
    }//Fine getExtension  
}//Fine classe MoveFile FIlter
