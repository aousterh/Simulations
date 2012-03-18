package models;

import core.Builder;

public abstract class ModelBuilder extends Builder 
 {
  public ModelBuilder(String modelName)
   { super(modelName); }
	
  //public abstract Model createModel();
  
  public abstract Model createFromDlg();
  public abstract Model createFromFile();
 }//Fine classe ModelBuilder
