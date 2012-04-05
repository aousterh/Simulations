#ifndef SIMTIME_H_
#define SIMTIME_H_

#include<iostream>
#include<cmath>

using namespace std;

class SimTime
{
 //overloading dell'I/O	
 friend ostream& operator<<(ostream& output,const SimTime& simTime); 	
	
	
 protected:
   float T; 
   float dt;
   int N;
 
   int loop;
   float time;	
   
 public:
	SimTime(const float &T,const float &dt,const int &N);
	virtual ~SimTime();
	
	void upTime();
	void downTime();
	ostream& staticTimetoXml(ostream& out);
	ostream& dynamicTimetoXml(ostream& out);
	
	//Metodi inline
	inline float getT()
     { return T; }
 
	inline float getDT()
     { return dt; }
 
    inline int getN()
     { return N; }
 
    inline int getLoop()
     { return loop; }
 
    inline float getTime()
     { return time; }
     
    inline void reset()
     { time=0.0f; loop=0; } 
  
  //protected:
    //Arrotonda il valore a due cifre decimali	 
    inline void setTime(float t)
     {time=(float)(floor((int)((t*100.0f)+0.5))/100.0f);}
     
};

#endif /*SIMTIME_H_*/
