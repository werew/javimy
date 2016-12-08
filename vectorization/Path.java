package vectorization;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class Path {
    public final int id;
    public ArrayList<Point> points;
    boolean circular = false;

    /**
     * Create Path from the content of another path
     * @param p Path to copy
     */
    public Path(Path p){
        this.id = p.id;
        points = new ArrayList<Point>(p.points);
    }

    /**
     * Create a path having the given id
     * @param id Id to assign to the path
     */
    public Path(int id){
        this.id = id;
        points = new ArrayList<Point>();
    }

    /* Get first point of the path */
    public Point getFirst(){
        return points.get(0);
    }

    /* Get last point of the path */
    public Point getLast(){
        return points.get(points.size()-1);
    }
   
    /**
     * Convert this path to a svg path
     * @return content of an svg path
     */ 
    public String toSVG(){
        String result = "";
        for (Point p : points){
            result += "L "+p.x+" "+p.y+" "; 
        }

        return result;
    }

     /* Theverse the path (the first point will be
      * the last one, etc..) */
    public void reverse(){
        Collections.reverse(this.points);
    }

  
    /**
     * Compress this part using Douglas-Peck 
     */
    public void reduce(double epsilon){
        if (this.points.size() <= 2 ) return;
        this.points = this.rec_red(this.points, epsilon);
    }

    /* Perpendicular distant to a point from a segment */
    private double perpDist(Point p1, Point p2, Point e){
        return Math.abs((p2.y-p1.y)*e.x - (p2.x-p1.x)*e.y 
                         + p2.x*p1.y - p2.y*p1.x ) /
               Math.sqrt((p2.y-p1.y)*(p2.y-p1.y)+
                         (p2.x-p1.x)*(p2.x-p1.x) ) ;
    }

    
    /* Implementation of the Douglas-Peck algorithm */
    private ArrayList<Point> rec_red
        (ArrayList<Point> rpath, double epsilon){

       
        double dmax = 0;
        double d = 0;
        int index = 0;
        int end = rpath.size()-1;

        Point first = rpath.get(0);
        Point last  = rpath.get(end);

        // Search longest distance 
        for (int i=1; i < end -1; i++){
            d = perpDist(first, last, rpath.get(i));
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }

        ArrayList<Point> result;

        if (dmax > epsilon) {
            result = rec_red(new ArrayList<Point>(
                    rpath.subList(0, index+1)), epsilon);

            // Pop last one to avoid repeter index
            result.remove(result.size()-1);

            result.addAll(rec_red(new ArrayList<Point>(
                    rpath.subList(index, end+1)), epsilon));
        } else {
            result = new ArrayList<Point>();
            result.add(first); result.add(last);
        }

        return result;
    }


}
