package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import engine.Scenario;

import parsers.SvgParser;
import parsers.XmlParser;


public class ScenarioGenerator 
 {
  public static boolean generate(File svgFile,File scenarioFile,int borderType)
   throws FileNotFoundException,IOException     
   {
	//Faccio il parsing del file svg
	SvgParser svgParser=new SvgParser();  
	boolean res=XmlParser.parse(svgFile,svgParser); 
	if(res==false)
	 return false;	
	 	
	Scenario scenario=svgParser.getScenario();
	if(scenario==null)
	  return false;
	
	//Setto i lbordo dello scenario
	scenario.setBorderType(borderType);
	
	//Scrivo il file dello scenario
	writeScenarioFile(scenarioFile,scenario);
	
	return true;
  }//Fine generate
  
  
 private static void writeScenarioFile(File scenarioFile,Scenario scenario) throws FileNotFoundException,IOException          
 
  {
   //Creo il file dello scenario
   FileOutputStream fos;
 	fos=new FileOutputStream(scenarioFile);  
 	
   if(fos==null)
    {throw new FileNotFoundException(); } 
 		
 	//Creo lo stream
 	PrintStream ps=new PrintStream(fos);
 	
 	//Scrivo l'intestazione
 	ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	ps.println("<ScenarioInfo>");
 	
 	//Scrivo le info dello scenario nel file
 	ps.println(scenario.toXml());
 	
 	//Scrivo la chiusura
 	ps.println("</ScenarioInfo>");
 	
 	//Chiudo il file 
 	fos.close(); 
  }//Fine savePrefFile 
  
 }//Fine ScenarioGenerator
