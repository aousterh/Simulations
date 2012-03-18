package engine;

import java.awt.Color;
import java.awt.Font;


import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


import viewer.SimArenaGL;
import math.Point2d;

public class ScenText extends ScenarioElement
 {
  private String str;
  private float rot;
  private Color strColor;
  private Color pointColor;
  private Font font;
  private float scale;
  
  public ScenText(Point2d pos,String text)
   {
	super(pos);
	this.str=text;
	this.rot=0;
	this.strColor=Color.BLACK;
	this.pointColor=null;
	this.scale=1;
	
	font=new Font("Helvetica",Font.PLAIN, 12);
   }//Fine costruttore
  
  public void setStr(String text)
   {this.str=text;}
  
  public String getStr()
   {return str;}
  
  public void setStrColor(Color color)
   {this.strColor=color;}

 public Color getStrColor()
  {return strColor;}
 
 public void setPointColor(Color color)
  {this.pointColor=color;}

 public Color getPointColor()
  {return pointColor;}
  
  public void setRotation(float angle)
   {this.rot=angle;}
 
  public float getRotation()
   {return rot;}
  
  public void setScale(float scale)
   {
	if(scale>0)  
	 this.scale=scale;
	else
	 this.scale=1;
   }//Fine scale

  public float getScale()
   {return scale;}
 

  public void draw(GL gl, SimArenaGL arena) 
   {
    //Stampo il punto
	if(pointColor!=null)
	 {arena.drawCircle(gl,pos,0.5f,pointColor);}  
		   
	//Stampo il testo
	// Salvo la matrice corrente
	gl.glPushMatrix();
	 //Sposto il sistema di coordinate
	 gl.glTranslatef(pos.x,pos.y,0);
     //Applico la rotazione per mettere il testo dritto 
	 gl.glRotatef(180,1.0f,0,0);
	 //Applico la rotazione 
	 gl.glRotatef(rot,0,0,1.0f);
	 //Stampo il testo
	 arena.setActColor(gl,strColor);
	 gl.glScalef(scale,scale,0);
	 arena.renderStrokeString(gl,GLUT.STROKE_ROMAN,str);
	  
	 //Riprendo la matrice corrente
	 gl.glPopMatrix();
   }//Fine draw  
  
public String toXml() 
 {
  String xmlStr=new String(" <Text x=\""+pos.x+"\" y=\""+pos.y+"\" str=\""+str+"\"");
    
  //Colore del punto (Dato opzionale)
  if(pointColor!=null)
    { 
	 xmlStr+=" pointColor=\""+pointColor.getRed()+","+
	                     pointColor.getGreen()+","+
	                     pointColor.getBlue()+"\""; 
	}
	   
   //Colore testo (Dato opzionale)
   if((strColor.getRed()!=0)||
     (strColor.getGreen()!=0)||
     (strColor.getBlue()!=0))
     { 
	  xmlStr+=" strColor=\""+strColor.getRed()+","+
	                       strColor.getGreen()+","+
                           strColor.getBlue()+"\""; 
	 }  
   
   //Angolo di rotazione (Dato opzionale)
   if(rot!=0)
     { xmlStr+=" rotation=\""+rot+"\""; }  
   
   //Scale della stringa
   if(scale!=1)
    { xmlStr+=" scale=\""+scale+"\""; }
   
   //Stringa di chiusura tag
   xmlStr+=" />\n";
  
   return xmlStr;
 }//Fine toXml
}//Fine ScenText
