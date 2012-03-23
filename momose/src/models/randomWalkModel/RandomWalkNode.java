package models.randomWalkModel;

import engine.SimTime;

import math.Point2d;
import math.Vector2d;

import models.simpleModelStructures.SimpleNode;


public class RandomWalkNode extends SimpleNode
{
    protected float localTime;
    
    public RandomWalkNode(Point2d pos, float radius) 
    {
        super(pos,radius);
        
        localTime=0;
        //Velocità iniziale
        //setVelocity(new Vector2d(0f,vMax));
    }//Fine costruttore
    
    public void think(SimTime time) 
    {
        //System.out.println("Sto pensando...");  
        //Movimento di prova
        //testMove(time);
        //Movimento random
        randomMove(time); 
    }//Fine think
    
    private void testMove(SimTime time)
    {
        //setVelocity(new Vector2d(1f,0f));  
        if(collided==true)
        {
            Vector2d tv=getVelocity();
            //setVelocity(new Vector2d((-tv.x),-(vMax)));
        } 
    }//Fine testMove
    
    private void randomMove(SimTime time)
    {
        if((localTime>=pauseTime)||(collided==true)) 
        { 
            //Calcolo il nuovo vettore velocità 
            v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
            v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
            
            //Rapporto la velocità all'intervallo di tempo 
            v.x=v.x*time.getDT();
            v.y=v.y*time.getDT();
            
            //System.out.println("Nuova velocità="+v);
            localTime=0;  
        } 
        
        //Incremento il tempo locale  
        localTime+=time.getDT();
        
    }//Fine randomMove
}//Fine RandomWalkNode
