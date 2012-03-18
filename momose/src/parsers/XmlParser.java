package parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser 
 {
  public static boolean parse(File file,DefaultHandler parserHandler) 
   {
	//System.out.println("file aperto:"+file.getPath()); 
	//Creo il parser SAX
	SAXParserFactory factory=SAXParserFactory.newInstance();
	   
	//Provo a fare il parsing del file aperto
	try 
	  { 
	   SAXParser saxParser = factory.newSAXParser();
	   //Faccio il parsing del file
	   try 
		{saxParser.parse(file,parserHandler);}
		 
	   catch (IOException e) 
		{
		 //System.out.println("ERRORE: Impossibile fare il parsing...");	 
		 return false;
		}
	   }  
	  catch (ParserConfigurationException e) 
	   {
	  	//System.out.println("ERRORE: Configurazione del parser sbagliata...");  
	  	return false;
	   } 
	  catch (SAXException e) 
	   {
	  	//System.out.println("ERRORE: Errore del parser SAX...");  
	  	return false;
	   }
	  //System.out.println("Parsing del file terminato...");  
	  return true;
	 }//Fine parse
  
  
  public static boolean parse(InputStream inputStream,DefaultHandler parserHandler) 
  {
	//Creo il parser SAX
	SAXParserFactory factory=SAXParserFactory.newInstance();
	   
	//Provo a fare il parsing del file aperto
	try 
	  { 
	   SAXParser saxParser = factory.newSAXParser();
	   //Faccio il parsing dell'inputStream
	   try 
		{saxParser.parse(inputStream,parserHandler);}
		 
	   catch (IOException e) 
		{return false;}
	   }  
	  catch (ParserConfigurationException e) 
	   {return false;} 
	  catch (SAXException e) 
	   {return false;}
	    
	  return true;
	 }//Fine parse  
 }//Fine XmlParser
