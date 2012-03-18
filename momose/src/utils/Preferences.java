package utils;

public class Preferences 
 {
  private String configDir;
  private String scenariDir;
  private String traceFileDir;
  private String logFileDir;
  
  public Preferences()
   {
	configDir=".";  
	scenariDir=".";
	traceFileDir=".";
	logFileDir=".";
   }//Fine Costruttore
  
  public Preferences(String configDir,String scenariDir,
		             String traceFileDir,String logFileDir)
   {
    this.configDir=configDir;  
    this.scenariDir=scenariDir;
    this.traceFileDir=traceFileDir;
    this.logFileDir=logFileDir;
   }//Fine Costruttore
  
  public void setConfigDir(String dir)
   {configDir=dir;}
  public void setScenariDir(String dir)
   {scenariDir=dir;}
  public void setTraceFileDir(String dir)
   {traceFileDir=dir;}
  public void setLogFileDir(String dir)
   {logFileDir=dir;}
  
  public String getConfigDir()
   {return configDir;}
  public String getScenariDir()
   {return scenariDir;}
  public String getTraceFileDir()
   {return traceFileDir;}
  public String getLogFileDir()
   {return logFileDir;}
 }//Fine Preferences
