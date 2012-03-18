package math;


public class Ray2d 
{
 public Point2d o;
 public Vector2d d;
 
 public Ray2d()
  {
   o=new Point2d();
   d=new Vector2d();
  }//Fine costruttore
 
 public Ray2d(Ray2d ray)
  {
   this.o=new Point2d(ray.o);	 
   this.d=new Vector2d(ray.d);
  }//Fine costruttore
 
 public Ray2d(Point2d o,Vector2d d)
  {
   this.o=new Point2d(o);	 
   this.d=new Vector2d(d);
  }//Fine costruttore
 
 public void copy(Ray2d ray)
  {
   this.o.x=ray.o.x; this.o.y=ray.o.y;
   this.d.x=ray.d.x; this.d.y=ray.d.y;
  }//Fine copy
 
 public void copy(Point2d p,Vector2d v)
  {
   o.x=p.x; o.y=p.y;
   d.x=v.x; d.y=v.y;
  }//Fine copy
 
 public String toString()
  {return new String(o.toString()+"+"+d.toString());}
 
 
 static float pointOnRay(Ray2d ray,Point2d p)
 {
  Vector2d v=ray.d;
  Point2d o=ray.o;
  if(v.x==0)
   {
	 if(o.x!=p.x)
	  return -1;
	else
	 if(v.y==0)
		return -1;
	 else
	  return ((p.y-o.y)/v.y);
	}
  
  if(v.y==0)
   {
    if(o.y!=p.y)
	  return -1;
	 else
	  if(v.x==0)
		return -1;
	 else
	   return ((p.x-o.x)/v.x);	    
   }  
  
 //Sia v.x che v.y sono diversi da 0 
 float tx=(p.x-o.x)/v.x;
 float ty=(p.y-o.y)/v.y;
 
 if(tx==ty)
	return tx;
 else
  return -1;	 
 }//Fine pointOnRay
 
}//Fine classe Ray