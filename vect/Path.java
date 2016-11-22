import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

class Path {
    public ArrayList<Point> points;
    public Color color;

    public Path(Color c){
        color = c;
        points = new ArrayList<Point>();
    }

}
