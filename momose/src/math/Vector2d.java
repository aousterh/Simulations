package math;

public class Vector2d 
 {
  public float x;
  public float y;
  
  public Vector2d()
  {
   this.x=1;
   this.y=0;
  }//Fine costruttore
  
  public Vector2d(float x,float y)
   {
    this.x=x;
    this.y=y;
   }//Fine costruttore
  
  public Vector2d(Vector2d v)
   {
	this.x=v.x;
	this.y=v.y;
   }//Fine costruttore
  
  public Vector2d(Point2d tail,Point2d head)
  {
	this.x=head.x-tail.x;
	this.y=head.y-tail.y;
  }//Fine costruttore
  
  public float scalarMul(Vector2d v)
  { return this.x*v.x+this.y*v.y; }
  
  public void dotMul(float t)
  {
   this.x*=t;
   this.y*=t;
  }//Fine costruttore
  
  public void sum(Vector2d v)
   {
    this.x+=v.x;
    this.y+=v.y;
   }//Fine sum
  
  public static Vector2d sum(Vector2d v1,Vector2d v2)
   {
    Vector2d vectSum=new Vector2d(); 	  
	vectSum.x=v1.x+v2.x;   
	vectSum.y=v1.y+v2.y;
	return vectSum;
   }//Fine costruttore
  
  public static float scalarMul(Vector2d v1,Vector2d v2)
   {return (v1.x*v2.x+v1.y*v2.y);}
  
  public boolean isZero()
   {
	if((x==0.0)&&(y==0.0))
     return true;
	else
	 return false;	
   }//Fine isZero
  
  public void set(Point2d tail,Point2d head)
   {
	this.x=head.x-tail.x;
	this.y=head.y-tail.y;
   }//Fine set
  
  public String toString()
   { return new String("("+this.x+";"+this.y+")"); }
  
  public float getLength() 
   { return (float)(Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2)));}
  
  public void normalize()
   {
	float len=getLength();
	if(len!=0)
	{x=x/len;y=y/len;} 
   }//Fine normalize
  
  public float norm()
  {return  (float)Math.sqrt(x*x+y*y); }//Fine norm
  
  public Vector2d unitVector()
  {
   Vector2d unitVector=new Vector2d(x,y);
   float norm=this.norm();
   if(norm!=0)
    return (new Vector2d(x/norm,y/norm));
   else
	return new Vector2d(0,0);  
  }//Fine unitvector
  
  public static float dotPerp(Vector2d v1,Vector2d v2)
   {return (v1.x*v2.y)-(v2.x*v1.y);}
  
  public void rotate(float angle)
   {
	float oldX=x;   
    x=(float)(x*Math.cos(Math.toRadians(angle))-y*Math.sin(Math.toRadians(angle)));
	y=(float)(oldX*Math.sin(Math.toRadians(angle))+y*Math.cos(Math.toRadians(angle)));	  
   }//Fine rotate
  
 }//Fine classe Vector2d
