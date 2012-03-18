package core;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import engine.Scenario;

import gui.ConfigSimulationWnd;


import javax.swing.JOptionPane;
import org.xml.sax.helpers.DefaultHandler;

import parsers.ConfigSimParser;
import parsers.ScenarioParser;
import parsers.XmlParser;

public class SimLoader 
 {
  private static ConfigSimulationWnd configWnd;	
  
  synchronized public static Scenario loadScenarioFromCnfWnd(ConfigSimulationWnd cnfWnd)
   {
	//Salvo riferimento alla finesta  
	configWnd=cnfWnd;  
	  
    if(configWnd.getScenarioLoadType()==ConfigSimulationWnd.EMPTY_SCENARIO)
	 {
	  //Ritorno lo scenario   
	  return new Scenario(configWnd.getScenarioWidth(),
	                     configWnd.getScenarioHeight(),
				         configWnd.getScenarioBorderType());   
	 }  
	else
	 {
	  if(configWnd.getScenarioLoadType()==ConfigSimulationWnd.LOAD_SCENARIO_FILE)	
	   {
	    //Provo ad aprire il file dello scenario   
	    Scenario scenario=loadScenarioFromScenarioFile(new File(configWnd.getScenarioPath()));
	    //Ritorno lo scenario	
	    return scenario; 
	   }
	  else
	   { return configWnd.getScenarioCfg(); }	  
 	 }
	}//Fine loadScenarioFromWnd
  
  synchronized public static Scenario loadScenarioFromScenarioFile(File file)
   {
	//Faccio il parsing del file dello scenario  
	ScenarioParser scenarioParser=new ScenarioParser();  
	boolean opOk=XmlParser.parse(file,scenarioParser);  
	if(opOk==true)
	 {
	  if(scenarioParser.isScenarioFile())	
	   { return scenarioParser.getScenario(); }
	  else
	   {
		JOptionPane.showMessageDialog(null,"The File you selected is not a scenario-file...",
                                      "Warning",JOptionPane.WARNING_MESSAGE); 
		return null;
	   }  
	 }
	else
	 {return null;}
   }//Fine ScenarioLoader
  
  public static ConfigSimParser loadDataFromCnfFile(File cnfFile)
   {
	//Creo il parser del file di configurazione
	ConfigSimParser cnfSimParser=new ConfigSimParser(cnfFile);  
	boolean opOk=XmlParser.parse(cnfFile,cnfSimParser);  
	if(opOk==true)
	 {return cnfSimParser;}
	else
	 {return null;} 
	}//Fine loadSimFromCnfFile
  
  
  /*public static boolean parseXmlFile(File file,DefaultHandler parserHandler) 
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
  }//Fine parsingXmlFile*/
  
 public static boolean loadBuildersConfigData(Vector builders,File file)
  {
   boolean opOk=true;	 
	 
   Iterator i=builders.iterator();
   while(i.hasNext())
    {
	 //Estraggo il builder   
	 Builder nextBuilder=(Builder)i.next();
	 //Prelevo il parser
	 DefaultHandler nextParser=nextBuilder.getConfigParser();
	 //Se esiste il parser eseguo il parsig del file fi configurazione del builder
	 if(nextParser!=null)
	  {
	   File cfgFile=new File(getCfgFilePath(nextBuilder.getName(),file));	
       opOk=XmlParser.parse(cfgFile,nextParser);
       if(opOk==false)
        {
    	 JOptionPane.showMessageDialog(null,"Error in parsing "+cfgFile.getName()+" file!",
                                       "Error",JOptionPane.ERROR_MESSAGE);   
    	 return false;
        }   
	  } 
     }
   return true;
  }//Fine loadBuildersConfigData
 
 private static String getCfgFilePath(String builderName,File mainCnfFile)
  {
	return new String(mainCnfFile.getParent()
		              +File.separator
		              +builderName+"-"
		              +mainCnfFile.getName());
  }//Fine getCfgFilePath
 }//Fine SimLoader
