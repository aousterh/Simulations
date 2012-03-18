package datarecorders.scramRecorder;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

import utils.TraceFileCompressor;

import datarecorders.DataRecorder;

import math.Point2d;
import models.Model;
import models.Node;

import engine.Scenario;
import engine.SimTime;

public class ScramRecorder extends DataRecorder
{
    private Vector nodes;
    private SimTime simTime;
    private String outputFilePath;
    
    private PrintStream ps;
    private FileOutputStream fos;
    private boolean compressOutput;
    
    
    public ScramRecorder()
    {
        nodes=new Vector();  
        ps=null;
        fos=null;
        outputFilePath="."+File.separator+"scramRecorderOutput.csv";
        compressOutput=false;
    }//end constructor
    
    public void setOutputFilePath(String outputFileName)
    { this.outputFilePath=outputFileName; }
    
    public void setCompressOutput(boolean compressOutput)
    { this.compressOutput=compressOutput; }
    
    public void setup(Vector models, Scenario scenario, SimTime time) 
    {
        //create the list of nodes
        setupNodes(models);  
        
        //create the file to store data (CSV by default)
        createCsvFile(time);
        
        //write static info to the file
        writeStaticInfo(time, scenario);
        
        //get a reference to time
        this.simTime=time; 
    }//end setup
    
    private void setupNodes(Vector models)
    {
        Iterator i=models.iterator();
        while(i.hasNext())
        {
            //Pull the nodes from the model
            Model nextModel=(Model)i.next();
            nodes.addAll(nextModel.getNodes());
        } 
    }//end setupNodes
    
    private void createCsvFile(SimTime time)
    {
        //create the CSV file to save the simulation
        try{
            fos=new FileOutputStream(outputFilePath);
            ps=new PrintStream(fos);
        } 
        catch (IOException e) 
        { e.printStackTrace(); }
    }//end createCsvFile 
    
    private void writeStaticInfo(SimTime simTime, Scenario scenario) 
    {   
        //Write info on scenario
        ps.println(scenario.toString());
        
        //Write basic node info
        writeBaseNodeInfo(); 
        
    }//end writeStaticInfo 
    
    private void writeBaseNodeInfo() 
    {
        //Write the number of nodes
        ps.println("Nodes: " + nodes.size());
        
        //Write the column headers
        ps.println("time, xpos, ypos\n");
    }//end writeBaseNodeInfo
    
    public void record(SimTime time) 
    {
        //Write the node info
        writeNodeInfo(time);
        
    }//end record
    
    private void writeNodeInfo(SimTime simTime) 
    {
        float time = simTime.getTime();
        
        for(int i=0;i<nodes.size();i++)
        {
            Node nextNode=(Node)nodes.elementAt(i);  
            
            Point2d pos=nextNode.getPosition();
            
            //Write the position in the file
            ps.print(time + ", " + pos.x + ", " + pos.y + "\n");
            
        }   
    }//end writeNodeInfo
    
    public void close()
    {
        //Write the time info
        ps.println(simTime.toString()); 
        
        //Close the file
        try
        {fos.close();} 
        catch (IOException e) 
        {e.printStackTrace();}  
        
        //Compress the file
        if(compressOutput==true)
        { 
            File normalFile=new File(outputFilePath); 
            if(normalFile.exists())
            {  
                File compressedFile=new File(outputFilePath+".gz");
                boolean res=TraceFileCompressor.compress(normalFile,compressedFile);
                if(res==true)
                    normalFile.delete();  
            }
            
        } 
    }//end close 
}//end Class ScramRecorder
