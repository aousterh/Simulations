package engine;

import math.Point2d;
import math.Ray2d;
import models.Node;



public interface PhysicalObject 
{
 public float intersect(Node node);
 public float rayIntersect(Ray2d ray);
 public boolean nodeOverlap(Node node);
 public boolean pointOverlap(Point2d point);
}//Fine interfacia PhysicalObject
