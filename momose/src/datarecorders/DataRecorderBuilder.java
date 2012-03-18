package datarecorders;

import core.Builder;

public abstract class DataRecorderBuilder extends Builder 
 {
  public DataRecorderBuilder(String modelName)
   { super(modelName); }	
	
  public abstract DataRecorder createFromFile();
  public abstract DataRecorder createFromDlg();
 }//Fine classe DataRecorderBuilder
