import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

class Path {
    public ArrayList<Point> points;
    Point start;
    Point end;


    public Path(Point start){
        points = new ArrayList<Point>();
        this.start = start;
        points.add(start);
    }

}
