package utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TraceFileCompressor 
 {
  public static boolean compress(File inputFile,File outputFile)
   {
	try 
	 {
	  //Creo i buffer di letture e scrittura	
	  BufferedReader in=new BufferedReader(new FileReader(inputFile));
	  BufferedOutputStream out=new BufferedOutputStream(
				                   new GZIPOutputStream(
				                   new FileOutputStream(outputFile)));
			
	  //Scrivo il file
	  int c;
	  while((c=in.read())!=-1)
  	   {out.write(c);}	
				
	  //Chiudo i file
	  in.close();
	  out.close();	
	 } 
	catch (IOException e) 
	 {return false;}  
	return true;  
   }//Fine compress 
  
  public static boolean decompress(File inputFile,File outputFile)
   {
	try
	{
	 //Creo i buffer di lettura e scrittura	
	 BufferedReader in=new BufferedReader(new InputStreamReader(
	                  new GZIPInputStream(
		              new FileInputStream(inputFile))));
	 
	 BufferedOutputStream out=new BufferedOutputStream(
                              new FileOutputStream(outputFile));
	 //Leggo il file
	 int c;
	 while((c=in.read())!=-1)
	  {out.write(c);}
	 
	  //Chiudo i file
	  in.close();
	  out.close();	
	 }		  
    catch(IOException e) 
	  {return false;}   
	return true;
   }//Fine decompress 
}//Fine TraceFileCompressor
