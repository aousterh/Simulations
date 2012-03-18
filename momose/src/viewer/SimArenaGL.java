package viewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Iterator;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import math.Point2d;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;


public class SimArenaGL extends SimArena implements GLEventListener
 {
  private GLCanvas glCanvas;
  private GLU glu;	
  private GLUT glut;
  
  private int basePosX;
  private int basePosY;
 	
  public SimArenaGL(float scale) 
   {
    super(scale);
    
    //Setto il layout  
    this.setLayout(new BorderLayout()); 	  
	
    //Setto l'openGL
	setOpenGL();	
	
	//Setto la base del sistema di coordiate 
	basePosX=0;
	basePosY=0;
	
	
	//Applico il canvas al pannello
	this.add(glCanvas,BorderLayout.CENTER);
	
	//Aggiungo al canvas il listener per il mouse
	MouseArenaController mouseArenaController=new MouseArenaController(this);
	glCanvas.addMouseListener(mouseArenaController);
	glCanvas.addMouseMotionListener(mouseArenaController);
   }//Fine costruttore
  
  protected void applyChange() 
   {reDraw();}
  
  public void reDraw() 
   {glCanvas.display();}
  
  public void addToBaseCoords(int x,int y)
   {basePosX+=x; basePosY+=y;}
  
  public void setBaseCoords(int x,int y)
  {basePosX=x; basePosY=y;}
  
  public GLU getGlu()
   {return glu;}
   
  public float getScale() 
   {return scale;}
	  
  
  public void setOpenGL()
   {
	//Creo l'oggetto canvas sul quale disegno   
	glCanvas=new GLCanvas(new GLCapabilities());
	glCanvas.setSize(new Dimension(this.getWidth(),this.getHeight()));
	glCanvas.addGLEventListener(this);
	//Creo l'oggetto GLU
	glu=new GLU();
	//Creo l'oggetto glut
	glut=new GLUT();
	//Faccio partire l'animatore
	FPSAnimator animator=new FPSAnimator(glCanvas,60);
	animator.setRunAsFastAsPossible(true);
	animator.start();
   }//Fine setOpenGL 
 
  public void updateNodes(Vector nodes)
   { this.nodes=nodes;  }
  
 //Metodi di GLEventListener   
 public void init(GLAutoDrawable glDrawable) 
  {
   //Creo l'oggetto gl  
   final GL gl=glDrawable.getGL(); 
   
   //Cancello lo sfondo e lo coloro di grigio
   gl.glClearColor(0.8f,0.8f,0.8f,1.0f);
   //gl.glClearColor(0f,0f,0f,0f);
   //Abilito l'alfa blending
   gl.glDisable(GL.GL_BLEND);
   gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );
   //gl.glEnable(GL.GL_LINE_SMOOTH);
   //gl.glHint (GL.GL_LINE_SMOOTH_HINT,GL.GL_NICEST);
   // Creo le displayList che mi servono
   initDisplayList(gl);
  }//Fine init
 
 private void initDisplayList(GL gl) 
  {
   //Creo la display list per i stampare i cerchi "pieni" 	  
   int listId1=gl.glGenLists(1);
   gl.glNewList(listId1,gl.GL_COMPILE);
    GLUquadric quadric=glu.gluNewQuadric();
    glu.gluDisk(quadric,0,1,32,1);
    glu.gluDeleteQuadric(quadric); 
   gl.glEndList();
  
  //Creo la display list per i stampare i cerchi "vuoti" 	  
  int listId2=gl.glGenLists(2);
  gl.glNewList(listId2,gl.GL_COMPILE);
   quadric=glu.gluNewQuadric();
   glu.gluDisk(quadric,0.97f,1,32,1);
   glu.gluDeleteQuadric(quadric); 
  gl.glEndList();
  
 }//Fine createDisplyList

 public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) 
  {
   final GL gl=glDrawable.getGL();
   gl.glViewport(0,0,width,height); 
   
   //Setto la proiezione
   gl.glMatrixMode(GL.GL_PROJECTION);  
   gl.glLoadIdentity(); 
   glu.gluOrtho2D(0.0,width,0.0,height);
   
   //Riapplico la matrice del modello di vista
   gl.glMatrixMode(GL.GL_MODELVIEW); 
  }//Fine reshape
 
 
 
 public void display(GLAutoDrawable glDrawable) 
  {
   //Creo l'oggetto gl  
   final GL gl=glDrawable.getGL();
   //Cancello lo sfondo
   gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);
       
   //Carico la matrice indentita'
   gl.glLoadIdentity();
   
   //Setto la "vista" dell'utente
   gl.glTranslatef(basePosX,basePosY+glCanvas.getHeight(),0);
   gl.glRotatef(180,1,0,0);
   //Setto la scala
   gl.glScalef(scale,scale,0);
   
   //Disegno gli oggetti sulla scena
   
   //Disegno la sfondo
   drawBackground(gl);
   
   //Disegno gli elementi dello scenario
   drawScenElements(gl);
        
   //Disegno i nodi
   drawNodes(gl); 
   
  }//Fine display

  public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {}
  
  private void drawBackground(GL gl) 
   {
    //Disegno lo sfondo
	setActColor(gl,Color.WHITE);
	 gl.glBegin (GL.GL_POLYGON);
	   gl.glVertex2i(0,0);
	   gl.glVertex2i((int)sWidth,0);
	   gl.glVertex2i((int)sWidth,(int)sHeight);
	   gl.glVertex2i(0,(int)sHeight);
	 gl.glEnd();	 
		
	    //Disegno il bordo
	setActColor(gl,Color.BLACK);
	  gl.glBegin (GL.GL_LINE_LOOP);
	    gl.glVertex2i(0,0);
	    gl.glVertex2i((int)sWidth,0);
	    gl.glVertex2i((int)sWidth,(int)sHeight);
	    gl.glVertex2i(0,(int)sHeight);
	  gl.glEnd();	
   }//Fine drawBackground
  
  public void drawScenElements(GL gl)
   {
    for(int i=0;i<scenElemArray.length;i++)
     {scenElemArray[i].draw(gl,this);}  
   }//Fine drawScenElement  
  
 public void drawCircle(GL gl,Point2d coord,float radius,Color color)
  {
   //Converto le coordinate	 
   //Salvo la matrice corrente
   gl.glPushMatrix();
   //Sposto il sistema di coordinate
   gl.glTranslatef(coord.x,coord.y,0);
   //Disegno il cerchio
   setActColor(gl,color);
   gl.glScalef(radius,radius,0);
   
   //Disegno il cerchio memorizzato nella displayLIst
   gl.glCallList(1);
      
   //Riprendo la matrice corrente
   gl.glPopMatrix();	 
  }//Fine drawCircle

 public void drawLine(GL gl,Point2d start,Point2d end,Color color)
  {
   //Disegno la linea
   setActColor(gl,color);  	 
   gl.glBegin(gl.GL_LINE_STRIP);
    gl.glVertex2f(start.x,start.y);
    gl.glVertex2f(end.x,end.y);
   gl.glEnd();
  }//Fine drawLine


 private void drawNodes(GL gl)
  {
   if(nodes.size()>0)
	{	
	 if(drawAntennaRadius==true)
	  {
	   Iterator j=nodes.iterator();	 
	   while(j.hasNext())
	     {
	      GraphNode node=(GraphNode)j.next();
	      //Disegno l'antenna
	      drawAntennaRadius(gl,node);
	     }
	  } 
	    
	 if(drawGraph==true)
	  { drawAntennaGraph(gl,nodes); }	  
	  
	 Iterator i=nodes.iterator();	 
	 while(i.hasNext())
	 {
	  GraphNode node=(GraphNode)i.next();
	  //Disegno il nodo
	  node.draw(gl,this);
	 }	
	}
  }//Fine drawNodes

  private void drawAntennaRadius(GL gl,GraphNode node)  
   {
    Color antennaColor=new Color(0f,1f,0f,0.1f);
    gl.glEnable(GL.GL_BLEND);
    drawCircle(gl,node.getPosition(),node.getAntennaRadius(),antennaColor);	
    gl.glDisable(GL.GL_BLEND);	
   }//Fine drawAntennaRadius

  private void drawAntennaGraph(GL gl,Vector nodes) 
   {
    for(int i=0;i<nodes.size();i++)
     {
	  GraphNode node=(GraphNode)nodes.elementAt(i);
	  for(int j=0;j<nodes.size();j++)
	   {
		GraphNode nodeTarget=(GraphNode)nodes.elementAt(j);
		if(i!=j)
		 {
		  Point2d nodePos=node.getPosition();
		  Point2d targetPos=nodeTarget.getPosition();
	      if(Point2d.distance(nodePos,targetPos)<=node.getAntennaRadius())
	       {
	    	Color color=calcAttenuation(node,nodeTarget);   
	        drawLine(gl,nodePos,targetPos,color);
	       }
		 }	
	   }	
	 }	   
  }//Fine drawAntenna Graph
  
 public void setActColor(GL gl,Color color)
  {
   gl.glColor4f((float)color.getRed()/255,
	            (float)color.getGreen()/255,
			    (float)color.getBlue()/255,
			     0.1f);
  }//Fine setColor
  
 private void drawDebugBox(GL gl,int x,int y)
  {
   //Disegno il rettangolo interno 
   setActColor(gl,Color.RED);
	  gl.glBegin(gl.GL_POLYGON);
	   gl.glVertex2f(x,y);
	   gl.glVertex2f(x,y+10);
	   gl.glVertex2f(x+10,y+10);
	   gl.glVertex2f(x+10,y+0);
	  gl.glEnd(); 
  }//Fine drawDebugBox
 
public  void renderStrokeString(GL gl,int font,String string) 
  {
   for (int i=0;i<string.length();i++)
    {
     char c=string.charAt(i);
     glut.glutStrokeCharacter(font,c);
    }
  }//Fine renderStrokeString

 }//Fine classe SimArenaGL

 class MouseArenaController implements MouseListener,MouseMotionListener
  {
   private int lastPosX;
   private int lastPosY; 	 
	 
   private SimArenaGL owner;	
	
   public MouseArenaController(SimArenaGL owner)
    { 
	 this.owner=owner;
	 lastPosX=-1;
	 lastPosY=-1;
	}//Fine MuseArenaAdapter
	
   public void changeBaseCoords(int posX,int posY)
    {owner.addToBaseCoords(posX-lastPosX,-(posY-lastPosY));}
  
  public void mouseDragged(MouseEvent e) 
   {
    if(e.getModifiers()==MouseEvent.BUTTON1_MASK)
    {   
     if((lastPosX!=-1)&&(lastPosY!=-1))
      {changeBaseCoords(e.getX(),e.getY());}
      //Salvo la posizione del mouse
      lastPosX=e.getX(); lastPosY=e.getY();	
     } 
   }//Fine mouseDragged
  
  public void mouseReleased(MouseEvent e) 
   { lastPosX=-1; lastPosY=-1; }
  
 public void mousePressed(MouseEvent e) 
  {
   if(e.getModifiers()==MouseEvent.BUTTON3_MASK)
    {
	 owner.setBaseCoords(0,0);
	 owner.reDraw();
    }  
  }//Fine mousePressed
 
 public void mouseMoved(MouseEvent e) {}
 public void mouseClicked(MouseEvent e) {}
 public void mouseEntered(MouseEvent e) {}
 public void mouseExited(MouseEvent e) {}
 
}//Fine classe MouseArenaController

 


