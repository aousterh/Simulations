package utils;

import java.io.File;
import java.text.NumberFormat;

import javax.swing.ImageIcon;

import core.SimLoader;

import parsers.PrefParser;
import parsers.XmlParser;

public class Utils 
 {
  static public String getTimeString(float time) 
   {
	//Ore	 
	int o=(int)time/3600;
	//Minuti
	int m=(int)((time-3600*o)/60);
	//Secondi
	int s=(int)(time-(3600*o)-60*m);
	//Centesimi di secondo
	int c=(int)Math.round((time-(int)time)*100)%100;
	   
	//Formatto l'ora
	NumberFormat nfo=NumberFormat.getInstance();
	nfo.setMinimumIntegerDigits(1);
	String so=nfo.format(o);
	   
	//Converto i minuti
	NumberFormat nfm=NumberFormat.getInstance();
	nfm.setMinimumIntegerDigits(2);
	nfm.setMaximumIntegerDigits(2);
	String sm=nfm.format(m);
	  
	//Formatto i secondi
	NumberFormat nfs=NumberFormat.getInstance();
	nfs.setMinimumIntegerDigits(2);
	nfs.setMaximumIntegerDigits(2);
	String ss=nfs.format(s);
	   
	//Formatto i centesimi
	NumberFormat nfc=NumberFormat.getInstance();
	nfc.setMinimumIntegerDigits(2);
	nfc.setMaximumIntegerDigits(2);
	String sc=nfc.format(c);
	   
	//ritorno la stringa del tempo
	return new String(so+":"+sm+":"+ss+","+sc);
  }//Fine calcTime
  
  public static ImageIcon getIcon(String iconName,String text)
   {
	ImageIcon icon=new ImageIcon("."+File.separator+"resources"+File.separator+
			                      "icons"+File.separator+iconName,text);
		
	return icon;
   }//Fine getIcon
  
  public static Preferences getPreferences(String  prefFileName)
   {
	File prefFile=new File(prefFileName); 
	
	if(prefFile.exists()==false)
	{return new Preferences();} 
	else
	 {
	  PrefParser prefParser=new PrefParser(); 
	  XmlParser.parse(prefFile,prefParser);
	  return prefParser.getPreferences(); 
	 }		
   }//Fine gePreftDir 
 }//Fine classeUtils
