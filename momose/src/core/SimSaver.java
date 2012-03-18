package core;

import engine.Scenario;
import engine.SimTime;
import gui.ConfigSimulationWnd;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

public class SimSaver 
 { 
  private static int MODELSBUILDERS=0;	
  private static int DATARECBUILDERS=1;	
 
  private static ConfigSimulationWnd configWnd;
	
  synchronized public static void saveCnfFile(ConfigSimulationWnd cnfWnd,File mainCnfFile) 
   {
	//Predo il riferimento  alla finestra di configurazione 
	configWnd=cnfWnd;  
	
	//Salvo il file di configurazione principale
	boolean opOk=false;
	opOk=saveMainCnfFile(mainCnfFile);
	if(opOk==true)
	{		
	 //Salvo i file di configurazione dei ModelBuider
	 opOk=saveBuildersCnfFiles(configWnd.getModBuildSel(),mainCnfFile);
	 if(opOk==true)
	  {	 
	   //Salvo i file di configurazione dei data-recorder
	   saveBuildersCnfFiles(configWnd.getRecBuildSel(),mainCnfFile);
	  } 
	}
  }//Fine saveCnfFile
  
  private static boolean saveMainCnfFile(File mainCnfFile)
   {
	//Apro lo scenario
	Scenario scenario=SimLoader.loadScenarioFromCnfWnd(configWnd);  
	if(scenario==null)
	 {
      JOptionPane.showMessageDialog(configWnd,"Syntax error in scneario file...\n"+
      		                        "Unable to create main config file...\n"+
                                    "Saving aborted!",
                                    "Error",JOptionPane.ERROR_MESSAGE);		
	  return false;
	 } 
	  
	//Creo il file di configurazione
	FileOutputStream fos=createFile(mainCnfFile.getPath());
	if(fos==null)
	 return false;
		
	//Creo lo stream
	PrintStream ps=new PrintStream(fos);
	ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
	ps.println("<ConfigInfo>");
	    
	//Salvo le info sul tempo
	SimTime simTime=new SimTime(configWnd.getT(),
			                    configWnd.getDT(),
			                    configWnd.getLOOPS());
	ps.println(simTime.staticTimeToXml());
	
	//Salvo le info sulllo scenario
	ps.println(scenario.toXml());
	
	//Salvo i riferimenti ai builders dei modelli
	saveBuildersRef(ps,configWnd.getModBuildSel(),MODELSBUILDERS);
    //Salvo i riferimenti ai builders dei recorder
	saveBuildersRef(ps,configWnd.getRecBuildSel(),DATARECBUILDERS);
	      
	//Chiudo il tag principale
	ps.println("</ConfigInfo>");
	
	//Chiudo il file 
	return closeFile(fos,mainCnfFile.getPath());   
   }//Fine saveMainCnfFile 
  
  private static void saveBuildersRef(PrintStream ps,Vector builders,int buildersType)
   {
	Iterator i=builders.iterator();
	while(i.hasNext())
	 {
	  //Estraggo il prossimo builder	
	  Builder nextBuilder=(Builder)i.next();
	  if(buildersType==MODELSBUILDERS)
		ps.println("<ModBuilder name=\""+nextBuilder.getName()+"\"/>");
	   else
	    ps.println("<RecBuilder name=\""+nextBuilder.getName()+"\"/>");
	 } 
   }//Fine saveBildersRef
  
  
  private static boolean saveBuildersCnfFiles(Vector builders,File mainCnfFile)
   {
	Iterator i=builders.iterator();
	while(i.hasNext())
	 {
	  //Estraggo il prossimo builder	
	  Builder nextBuilder=(Builder)i.next();
	  //Salvo le info di questo builder
	  boolean opOk=saveBuilderInfo(nextBuilder,mainCnfFile);
	  if(opOk==false)
		return false; 
	 }		
	return true; 
   }//Fine saveBuildersCnfFiles
  
  private static boolean saveBuilderInfo(Builder builder,File mainCnfFile)
   {
	//Costruisco il nome del file  
	String cnfFilePath=getCfgFilePath(builder.getName(),mainCnfFile);
	//Creo il file di configurazione
	FileOutputStream fos=createFile(cnfFilePath);
    if(fos==null)
      return false;
	
    //Creo lo stream
    PrintStream ps=new PrintStream(fos);
    ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
    ps.println("<Info>");
    
    //Salvo le info del buidler
    if(builder.toXml()!=null)
     ps.println(builder.toXml());
      
	//Chiudo il tag principale
    ps.println("</Info>");
    //Chiudo il file 
    return closeFile(fos,cnfFilePath);  
   }//Fine saveBuilderInfo
  
  private static String getCfgFilePath(String builderName,File mainCnfFile)
   {
	return new String(mainCnfFile.getParent()
		              +File.separator
		              +builderName+"-"
		              +mainCnfFile.getName());
   }//Fine getCfgFilePath
  
  private static FileOutputStream createFile(String fileName)
   {
	FileOutputStream fos;
	try 
	 {fos=new FileOutputStream(fileName); } 
	catch (FileNotFoundException e) 
	 {
      JOptionPane.showMessageDialog(configWnd,"Unable to create "+fileName+"!\n" +
      		                        "Saving aborted!",
                                    "Error",JOptionPane.ERROR_MESSAGE);
	 return null; 	
	}  
	//Ritorno il riferimento allo stream 
    return fos;  
   }//Fine createFile
  
  private static boolean closeFile(FileOutputStream fos,String fileName)
   {
	try 
	 { fos.close();	} 
	catch (IOException e) 
	 {
	  JOptionPane.showMessageDialog(configWnd,"Unable to close "+fileName+"!\n" +
                                    "Saving aborted!",
                                    "Error",JOptionPane.ERROR_MESSAGE);
      return false; 		
	 }  
	return true;
   }//Fine cloaseFile
  
  public static boolean saveFile(String text,File file)
   {
	//Apro il file  
	FileOutputStream fos=createFile(file.getPath());
    if(fos==null)
	 return false;
    //Creo lo stream
	PrintStream ps=new PrintStream(fos);
	ps.println(text);
	
	//Chiudo il file
	return closeFile(fos,file.getPath());
   }//Fine saveFile
 }//Fine SimSaver
