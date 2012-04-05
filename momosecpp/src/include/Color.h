#ifndef COLOR_H_
#define COLOR_H_

#include<iostream>

class Color
 {
  //overloading dell'I/O	
  inline friend std::ostream& operator<<(ostream& output,const Color& col)
   {output<<"|"<<col.r<<","<<col.g<<","<<col.b<<"|"; return output;}

  private:	
  	int r;
  	int g;
  	int b;	
 	
  public:	
   inline Color()
    {r=0; g=0; b=0;}	
    
   inline Color(int r,int g,int b)
    {this->r=r; this->g=g; this->b=b;} 
    
   inline void setRi(int r)
    {this->r=r;}  
    
   inline void setGi(int g)
    {this->g=g;}   
    
   inline void setBi(int b)
    {this->b=b;}  
    
   inline int getRi()
    {return r;}  
    
   inline int getGi()
    {return g;}   
    
   inline int getBi()
    {return b;}   
    
   inline void setRf(float r)
    {this->r=(int)(r*255);}  
    
   inline void setGf(float g)
    {this->g=(int)(g*255);}   
    
   inline void setBf(float b)
    {this->b=(int)(b*255);}  
    
    inline float getRf()
     {return (r/255);} 
     
    inline float getGf()
     {return (g/255);}   
    
    inline float getBf()
     {return (b/255);}  

};//Fine Color

#endif /*COLOR_H_*/
