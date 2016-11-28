import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class Path {
    public final int id;
    boolean circular = false;
    public ArrayList<Point> points;

    public Path(Path p){
        this.id = p.id;
        points = new ArrayList<Point>(p.points);
    }

    public Path(int id){
        this.id = id;
        points = new ArrayList<Point>();
    }

    public Point getFirst(){
        return points.get(0);
    }

    public Point getLast(){
        return points.get(points.size()-1);
    }
    
    public String toSVG(){
        String result = "";
        for (Point p : points){
            result += "L "+p.x+" "+p.y+" "; 
        }

        return result;
    }

    public void reverse(){
        Collections.reverse(this.points);
    }

    public Path greverse(){
        this.reverse();
        return getc();
    }

    public Path getc(){
        Path pa = new Path(id);
        for (Point p : points){
            pa.points.add(p); 
        }
        return pa;
    }

}
