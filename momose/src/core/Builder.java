package core;

import org.xml.sax.helpers.DefaultHandler;

import gui.ConfigDlg;


public abstract class Builder 
 {
  private String name;
    
  public Builder(String name)
   { this.name=name;}
  
  public String getName()
   {return name;}
  
  
  //Metodi astratti che vanno riscritti
  public abstract DefaultHandler getConfigParser();
  public abstract ConfigDlg getConfigDlg();
  public abstract String toXml();
  public abstract void setConfigDlgFromFile();
 }//Fine classe Builder
