package models.randomWalkMessageModel;

import java.util.ArrayList;

import engine.SimTime;
import math.Point2d;
import models.messageModel.MessageNode;


public class RandomWalkMessageNode extends MessageNode
{
    protected float localTime;
    protected float vMax;
    protected float vMin;
    protected float pauseTime;
    
    public RandomWalkMessageNode(Point2d pos, float radius, int id, SimTime time) 
    {
        super(pos, radius, id, time);
        
        localTime = 0;
        vMax=5;
        vMin=0;
        pauseTime=1f;
    } // end constructor
    
    public void think(SimTime time) {
        randomMove(time);
    }
    
    private void randomMove(SimTime time)
    {
        if((localTime>=pauseTime)||(collided==true)) 
        { 
            // Calculate the new velocity vector
            v.x=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
            v.y=(float)(Math.random()*vMax+vMin)*(Math.random()<0.5?(-1):(1));
            
            // Calculate the new velocity
            v.x=v.x*time.getDT();
            v.y=v.y*time.getDT();
            
            localTime=0;  
        } 
        
        // increment the local time 
        localTime+=time.getDT();
        
    } // end randomMove
} // end MessageNode