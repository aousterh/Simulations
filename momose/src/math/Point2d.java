package math;


public class Point2d 
{
    public float x;
    public float y;
    
    public Point2d()
    {
        this.x=0;
        this.y=0;
    }//Fine Costruttore
    
    public Point2d(float x,float y)
    {
        this.x=x;
        this.y=y;
    }//Fine Costruttore
    
    public Point2d(Point2d p)
    {
        this.x=p.x;
        this.y=p.y;
    }//Fine Costruttore
    
    public Point2d(Point2d p,Vector2d v)
    {
        this.x=p.x+v.x;
        this.y=p.y+v.y;
    }//Fine costruttore
    
    public void set(float x,float y)
    {
        this.x=x;
        this.y=y;
    }
    
    public void set(Point2d p,Vector2d v)
    {
        this.x=p.x+v.x;
        this.y=p.y+v.y;
    }//Fine set
    
    public void setX(float x)
    {this.x=x;}
    
    public void setY(float y)
    {this.y=y;}
    
    public float getX()
    {return x;}
    
    public float getY()
    {return y;}
    
    public String toString()
    { return new String("["+this.x+";"+this.y+"]"); }
    
    public String toXml()
    { return new String("<x>"+this.x+"</x><y>"+this.y+"</y>"); }
    
    public void copy(Point2d p)
    {
        this.x=p.x;
        this.y=p.y;
    }//fine copy
    
    public Point2d clonate()
    {return new Point2d(x,y);}
    
    public void addVector2d(Vector2d v)
    {
        this.x+=v.x;
        this.y+=v.y;
    }//Fine addVector2d
    
    
    public void translate(Vector2d vect)
    {addVector2d(vect);}
    
    public void translate(float x,float y)
    {this.x+=x; this.y+=y;}
    
    public void rotate(float angle)
    {
        float oldX=x;     
        x=(float)(x*Math.cos(Math.toRadians(angle))-y*Math.sin(Math.toRadians(angle)));
        y=(float)(oldX*Math.sin(Math.toRadians(angle))+y*Math.cos(Math.toRadians(angle)));   
    }//Fine rotate
    
    public float distance(Point2d p)
    {
        return (float)Math.sqrt((p.x-this.x)*(p.x-this.x)+(p.y-this.y)*(p.y-this.y));  
    }//Fine distance
    
    public static float distance(Point2d p1,Point2d p2)
    {
        return (float)Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y));  
    }//Fine distance
    
    public static Vector2d getVector2d(Point2d tail,Point2d head)
    {
        return (new Vector2d(head.x-tail.x,head.y-tail.y));   
    }//Fine getVector2d
    
    public static float dotPerp(Point2d p1,Point2d p2)
    {return (p1.x*p2.y)-(p2.x*p1.y);}
    
    public static Point2d fromVector(Point2d p,Vector2d v)
    {return (new Point2d(p,v));}//Fine 
    
    
    public static float pointOnRay(Ray2d ray,Point2d p)
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
}//Fine Point2d